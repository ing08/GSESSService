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
import jp.kobe_u.cs27.GSESSService.common.application.form.SCodeForm;
import jp.kobe_u.cs27.GSESSService.common.application.form.SubjectForm;
import jp.kobe_u.cs27.GSESSService.common.configration.exception.SubjectValidationException;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.SubjectService;
import jp.kobe_u.cs27.GSESSService.common.domain.dto.SubjectDto;

/**
 * 科目のコントローラー
 *
 * @author ing
 */
@Controller
@RequiredArgsConstructor
public class SubjectController {
    /**
     * 科目のサービス
     */
    private final SubjectService ss;

    /**
     * 科目追加用ページを渡す
     *
     * @param model
     * @return SubjectRegister.html
     */
    @GetMapping("/subject/add")
    public String showSubjectRegistrationPage(Model model) {
        // SubjectFormをModelに追加する(Thymeleaf上ではsubjectForm)
        model.addAttribute(new SubjectForm());

        model.addAttribute("subjects", ss.getAllSubject());

        return "subjectRegister";
    }

    /**
     * 科目追加を確認する
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return subjectConfirmRegistration.html
     */
    @GetMapping("/subject/register/confirm")
    public String confirmSubjectRegistration(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated SubjectForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // formのバリゼーション違反
            attributes.addFlashAttribute("isSubjectFormError", true); // エラーフラグオン

            return "redirect:/subject/add"; // viewを転換
        }

        final String sCode = form.getSCode(); // 科目コードを変数に格納する

        if (ss.existsSubject(sCode)) { // 科目登録済みの場合
            attributes.addFlashAttribute("isSubjectAlreadyExistsError", true); // エラーフラグオン

            return "redirect:/subject/add"; // viewを転換
        }

        // 科目情報をModelに追加する
        model.addAttribute("sCode", sCode);
        model.addAttribute("name", form.getName());
        model.addAttribute("description", form.getDescription());

        return "subjectConfirmRegistration"; // 科目登録確認ページ
    }

    /**
     * 科目追加する
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return subjectregistered.html
     */
    @PostMapping("/subject/register")
    public String registerSubject(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated SubjectForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // formのバリゼーション違反
            attributes.addFlashAttribute("isSubjectFormError", true); // エラーフラグオン

            return "redirect:/subject/add"; // viewを転換
        }

        try {
            ss.createSubject(form.toEntity()); // user登録
        } catch (SubjectValidationException e) { // user登録済みの場合
            attributes.addFlashAttribute("isSubjectAlreadyExistError", true); // エラーフラグオン

            return "redirect:/subject/add"; // viewを転換
        }

        // ユーザ情報をModelに追加する
        model.addAttribute("sCode", form.getSCode());
        model.addAttribute("name", form.getName());

        return "subjectRegistered"; // 科目追加完了ページを返す
    }

    /**
     * 科目削除用ページを渡す
     *
     * @param model
     * @return subjectDelete.html
     */
    @GetMapping("/subject/remove")
    public String showSubjectDeletePage(Model model) {
        // SCodeFormをModelに追加する(Thymeleaf上ではsCodeform)
        model.addAttribute(new SCodeForm());

        model.addAttribute("subjects", ss.getAllSubject());

        return "subjectDelete";
    }

    /**
     * 科目削除が可能か確認する
     *
     * @param model
     * @param attributes
     * @param form       科目コード
     * @return subjectConfirmDelete.html
     */
    @GetMapping("/subject/delete/confirm")
    public String confirmSubjectDelete(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated SCodeForm form, BindingResult bindingResult) {

        // 科目コードに使用できない文字が含まれていた場合
        if (bindingResult.hasErrors()) {
            // エラーフラグをオンにする
            attributes.addFlashAttribute("isSCodeValidationError", true);

            // 初期画面に戻る
            return "redirect:/user/remove";
        }

        // 科目コードを変数に格納する
        final String sCode = form.getSCode();

        // 科目情報をDBから取得する
        // 科目が登録済みかどうかの確認も兼ねている
        SubjectDto sd;
        try {
            sd = ss.getSubject(sCode);
        } catch (SubjectValidationException e) {
            // エラーフラグをオンにする
            attributes.addFlashAttribute("isSubjectDoesNotExistError", true);
            // 初期ページに戻る
            return "redirect:/user/remove";
        }

        // 科目情報をModelに追加する
        model.addAttribute("sCode", sCode);
        model.addAttribute("name", sd.getName());
        model.addAttribute("description", sd.getDescription());

        // 科目削除確認ページ
        return "subjectConfirmDelete";
    }

    /**
     * 科目を削除する
     *
     * @param model
     * @param attributes
     * @param form       科目コード
     * @return 初期ページ
     */
    @PostMapping("/subject/delete")
    public String deleteSubject(Model model, RedirectAttributes attributes, @ModelAttribute @Validated SCodeForm form,
            BindingResult bindingResult) {

        // 科目コードに使用できない文字が含まれていた場合
        if (bindingResult.hasErrors()) {
            // エラーフラグをオンにする
            attributes.addFlashAttribute("issCodeValidationError", true);

            // 初期画面に戻る
            return "subjectDelete";
        }

        // 科目コードを変数に格納する
        final String sCode = form.getSCode();

        // 科目情報をModelに追加する
        model.addAttribute("sCode", sCode);
        model.addAttribute("name", ss.getSubject(sCode).getName());

        // 科目を削除する
        ss.deleteSubject(sCode);

        return "subjectDeleted"; // 削除完了ページを返す
    }
}
