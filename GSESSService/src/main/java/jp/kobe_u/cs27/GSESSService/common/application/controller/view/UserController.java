package jp.kobe_u.cs27.GSESSService.common.application.controller.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import jp.kobe_u.cs27.GSESSService.common.application.form.UidForm;
import jp.kobe_u.cs27.GSESSService.common.application.form.UserForm;
import jp.kobe_u.cs27.GSESSService.common.configration.exception.UserValidationException;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.UserService;
import jp.kobe_u.cs27.GSESSService.common.domain.dto.UserDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.survice.StudyLogSurvice;

/**
 * ユーザのコントローラー
 *
 * @author ing
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    /** ユーザのサービス */
    private final UserService us;
    /** 勉強時間記録のサービス */
    private final StudyLogSurvice sls;

    /**
     * ログインした後、GSESSEerviceにホームのページを返す
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return home.html
     */
    @GetMapping("/user/enter")
    public String confirmUserExistence(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated UidForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) { // ユーザIDに使用できない文字が含まれていた場合
            attributes.addFlashAttribute("isUidValidationError", true); // エラーフラグをオンにする

            return "redirect:/"; // 初期画面に戻る
        }

        // ユーザIDを変数に格納する
        final String uid = form.getUid();

        String nickname;

        // ユーザIDから名前を取得する
        // ユーザが登録済みかどうかの確認も兼ねている
        try {
            nickname = us.getUser(uid).getNickname();
        } catch (UserValidationException e) { // ユーザIDが未登録であった場合
            attributes.addFlashAttribute("isUserDoesNotExistError", true); // エラーフラグをオンにする

            System.out.println(e.getMessage());

            return "redirect:/"; // 初期ページに戻る
        }

        // ユーザIDとニックネームをModelに追加する
        attributes.addFlashAttribute("uid", uid);
        attributes.addFlashAttribute("nickname", nickname);

        return "redirect:/home"; // GSESServiceホーム
    }

    /**
     * 新規登録用ページを渡す
     *
     * @param model
     * @return userRegister.html
     */
    @GetMapping("/user/signup")
    public String showUserRegistrationPage(Model model) {
        // UserFormをModelに追加する(Thymeleaf上ではuserForm)
        model.addAttribute(new UserForm());

        return "userRegister";
    }

    /**
     * 新規登録を確認する
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return userConfirmRegistration.html
     */
    @GetMapping("/user/register/confirm")
    public String confirmUserRegistration(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // formのバリゼーション違反
            attributes.addFlashAttribute("isUserFormError", true); // エラーフラグオン

            return "redirect:/user/signup"; // viewを転換
        }

        final String uid = form.getUid(); // ユーザIDを変数に格納する

        if (us.existsUser(uid)) { // user登録済みの場合
            attributes.addFlashAttribute("isUserAlreadyExistsError", true); // エラーフラグオン

            return "redirect:/user/signup"; // viewを転換
        }

        // ユーザ情報をModelに追加する
        model.addAttribute("uid", uid);
        model.addAttribute("nickname", form.getNickname());
        model.addAttribute("email", form.getEmail());

        return "userConfirmRegistration"; // ユーザ登録確認ページ
    }

    /**
     * 新規登録する
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return userRegistered.html
     */
    @PostMapping("/user/register")
    public String registerUser(Model model, RedirectAttributes attributes, @ModelAttribute @Validated UserForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // formのバリゼーション違反
            attributes.addFlashAttribute("isUserFormError", true); // エラーフラグオン

            return "redirect:/user/signup"; // viewを転換
        }

        try {
            us.createUser(form.toEntity()); // user登録
        } catch (UserValidationException e) { // user登録済みの場合
            attributes.addFlashAttribute("isUserAlreadyExistError", true); // エラーフラグオン

            System.out.println(e.getMessage());

            return "redirect:/user/signup"; // viewを転換
        }

        sls.createSubjectState(form.toEntity()); // 勉強状況を登録

        // ユーザ情報をModelに追加する
        model.addAttribute(new UidForm());
        model.addAttribute("uid", form.getUid());
        model.addAttribute("nickname", form.getNickname());

        return "userRegistered"; // 新規登録完了ページを返す
    }

    /**
     * ユーザ退会用ページを渡す
     *
     * @param model
     * @return userDelete.html
     */
    @GetMapping("/user/withdrawal")
    public String showUserDeletePage(Model model) {
        // UidFormをModelに追加する(Thymeleaf上ではuidform)
        model.addAttribute(new UidForm());
        return "userDelete";
    }

    /**
     * ユーザ削除が可能か確認する
     *
     * @param model
     * @param attributes
     * @param form       ユーザID
     * @return userConfirmDelete.html
     */
    @GetMapping("/user/delete/confirm")
    public String confirmUserDelete(Model model, RedirectAttributes attributes, @ModelAttribute @Validated UidForm form,
            BindingResult bindingResult) {

        // ユーザIDに使用できない文字が含まれていた場合
        if (bindingResult.hasErrors()) {
            // エラーフラグをオンにする
            attributes.addFlashAttribute("isUidValidationError", true);

            // 初期画面に戻る
            return "redirect:/user/withdrawal";
        }

        // ユーザIDを変数に格納する
        final String uid = form.getUid();

        // ユーザ情報をDBから取得する
        // ユーザが登録済みかどうかの確認も兼ねている
        UserDto ud;
        try {
            ud = us.getUser(uid);
        } catch (UserValidationException e) {
            // エラーフラグをオンにする
            attributes.addFlashAttribute("isUserDoesNotExistError", true);

            System.out.println(e.getMessage());

            // 初期ページに戻る
            return "redirect:/user/withdrawal";
        }

        // ユーザ情報をModelに追加する
        model.addAttribute("uid", uid);
        model.addAttribute("nickname", ud.getNickname());
        model.addAttribute("email", ud.getEmail());

        // ユーザ削除確認ページ
        return "userConfirmDelete";
    }

    /**
     * ユーザを削除する
     *
     * @param model
     * @param attributes
     * @param form       ユーザID
     * @return userDeleted.html
     */
    @PostMapping("/user/delete")
    public String deleteUser(Model model, RedirectAttributes attributes, @ModelAttribute @Validated UidForm form,
            BindingResult bindingResult) {

        // ユーザIDに使用できない文字が含まれていた場合
        if (bindingResult.hasErrors()) {
            // エラーフラグをオンにする
            attributes.addFlashAttribute("isUidValidationError", true);

            // 初期画面に戻る
            return "userDelete";
        }

        // ユーザIDを変数に格納する
        final String uid = form.getUid();

        // ユーザ情報をModelに追加する
        model.addAttribute("uid", uid);
        model.addAttribute("nickname", us.getUser(uid).getNickname());

        // ユーザを削除する
        us.deleteUser(uid);

        return "userDeleted"; // 退会完了ページを返す
    }
}
