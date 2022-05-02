package jp.kobe_u.cs27.GSESSService.logs.application.controller.view;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.kobe_u.cs27.GSESSService.common.application.form.UidForm;
import jp.kobe_u.cs27.GSESSService.common.configration.exception.SubjectValidationException;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.SubjectService;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.UserService;
import jp.kobe_u.cs27.GSESSService.logs.application.form.CommentForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.CreateLogForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.DateQuaryForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.DeleteEventForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.EndStudyForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.StartStudyForm;
import jp.kobe_u.cs27.GSESSService.logs.configration.exception.EventValidationException;
import jp.kobe_u.cs27.GSESSService.logs.configration.exception.LogValidationException;
import jp.kobe_u.cs27.GSESSService.logs.configration.exception.OtherValidationException;
import jp.kobe_u.cs27.GSESSService.logs.domain.survice.StudyLogSurvice;
import lombok.RequiredArgsConstructor;

/**
 * 勉強時間記録のコントローラー
 *
 * @author ing
 */
@Controller
@RequiredArgsConstructor
public class StudyLogController {
    /** 勉強時間記録のサービス */
    private final StudyLogSurvice sls;
    /** ユーザのサービス */
    private final UserService us;
    /** 科目のサービス */
    private final SubjectService ss;

    // /**
    // * 勉強記録ページへ
    // */
    // @GetMapping("/studylog")
    // public String showStdyLogPage(){
    // return "redirect:/logs"
    // }

    /**
     * 勉強記録ページ
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return logs.html
     */
    @GetMapping("/logs")
    public String showStudyEventPage(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated UidForm form, BindingResult bindingResult) {
        // if (bindingResult.getFieldErrors().stream().anyMatch(it ->
        // it.getField().equals("uid"))) { // ユーザIDに使用できない文字が含まれていた場合
        // attributes.addFlashAttribute("isUidValidationError", true); // エラーフラグをオンにする

        // return "redirect:/"; // 初期画面に戻る
        // }

        final String uid = form.getUid(); // ユーザIDを変数に格納する

        model.addAttribute("today", new Date());

        // ユーザIDと名前をModelに追加する
        model.addAttribute("uid", uid);
        model.addAttribute("nickname", us.getUser(uid).getNickname());

        model.addAttribute("subjects", ss.getAllSubject()); // モデルに科目リストを追加

        String sCode = sls.getSubject(uid).getSCode();
        if (!sCode.equals("0")) { // 勉強中なら
            model.addAttribute("name", sls.getSubject(uid).getName()); // 現在行っている勉強の科目をモデルに登録
            model.addAttribute("isStudy", true); // 勉強フラグをオンにする
        } else {
            model.addAttribute("isStudy", false); // 勉強フラグをオフにする
        }

        model.addAttribute(new DateQuaryForm());
        model.addAttribute(new CommentForm()); // CommentFormをModelに追加
        model.addAttribute(new CreateLogForm()); // CreateLogFormをModelに追加

        // 今日のStudyLogを所得
        DateQuaryForm dqf = new DateQuaryForm();

        dqf.setUid(uid);
        dqf.setSince(new Date());
        dqf.setUntil(new Date());

        // model.addAttribute("dqf", dqf);

        model.addAttribute("logs", sls.getStudyEventByDate(sls.createDateQuaryDto(dqf)).getEvents());

        int time, hour, min, sec;

        // 累計勉強時間を計算
        time = sls.caluculateStudyTime(uid);
        hour = time / 3600;
        min = (time % 3600) / 60;
        sec = time % 60;

        // モデルに登録
        model.addAttribute("hour", hour);
        model.addAttribute("min", min);
        model.addAttribute("sec", sec);

        return "logs";
    }

    /**
     * Eventを見返す
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return "lookback"
     */
    @GetMapping("/logs/lookback")
    public String showStudyEventLookBack(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated DateQuaryForm form, BindingResult bindingResult) {
        final String uid = form.getUid(); // ユーザIDを変数に格納する

        System.out.println("since" + sls.createDateQuaryDto(form).getSince());
        System.out.println("until" + sls.createDateQuaryDto(form).getUntil());

        // ユーザIDと名前をModelに追加する
        model.addAttribute("uid", uid);
        model.addAttribute("nickname", us.getUser(uid).getNickname());

        model.addAttribute(new CreateLogForm()); // CreateLogFormをModelに追加

        model.addAttribute("logs", sls.getStudyEventByDate(sls.createDateQuaryDto(form)).getEvents());

        return "lookback";
    }

    // @GetMapping("/log/status")
    // public String showStudyStatus(Model model, RedirectAttributes attributes,
    // @ModelAttribute @Validated UidForm form,
    // BindingResult bindingResult) {
    // final String uid = form.getUid(); // ユーザIDを変数に格納する

    // model.addAttribute("uid", uid); // ユーザIDをModelに追加する

    // model.addAttribute("statuss", sls.createStudyStatus());

    // return "status";
    // }

    /**
     * StartStudyEventを記録
     *
     * @param model
     * @param attributes
     * @param StartStudyForm
     * @return redirect:/logs
     */
    @PostMapping("/logs/startstudy")
    public String pushStartStudyEvent(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated StartStudyForm form, BindingResult bindingResult) {
        final String uid = form.getUid(); // ユーザIDを変数に格納する

        // ユーザIDと名前をModelに追加する
        // attributes.addFlashAttribute("uid", uid);
        attributes.addAttribute("uid", uid);
        attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

        try {
            sls.startStudy(sls.createStartStudyDto(form)); // StartStudyEventを記録
        } catch (EventValidationException e) { // 直前のcomment以外StudyEventのtypeがendでなかった場合
            attributes.addFlashAttribute("isStudyDidNotEndError", true); // エラーフラグをオンにする

            System.out.println(e.getMessage());

            return "redirect:/logs"; // そのまま戻す
        }

        String name = sls.getSubject(uid).getName();
        if (!name.equals("0")) { // 正常に勉強状況が更新されていたら
            attributes.addFlashAttribute("name", sls.getSubject(uid).getName()); // 現在行っている勉強の科目をモデルに登録
            attributes.addFlashAttribute("isStudy", true); // 勉強フラグをオンにする
        } else {
            attributes.addFlashAttribute("isStudyEventDoseNotRecordError", true); // エラーフラグをオンにする
        }

        return "redirect:/logs";
    }

    /**
     * EndStudyEventを記録
     *
     * @param model
     * @param attributes
     * @param StartStudyForm
     * @return redirect:/logs
     */
    @PostMapping("/logs/endstudy")
    public String pushEndStudyEvent(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated EndStudyForm form, BindingResult bindingResult) {
        final String uid = form.getUid(); // ユーザIDを変数に格納する

        // ユーザIDと名前をModelに追加する
        // attributes.addFlashAttribute("uid", uid);
        attributes.addAttribute("uid", uid);
        attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

        try {
            sls.endStudy(sls.createEndStudyDto(form)); // EndStudyEventを記録
        } catch (EventValidationException e) { // 直前のcomment以外のStudyEventのtypeがstartでなかった場合
            attributes.addFlashAttribute("isStudyEventDoseNotRecordError", true); // エラーフラグをオンにする

            System.out.println(e.getMessage());

            return "redirect:/logs"; // そのまま戻す
        }

        attributes.addFlashAttribute("isStudy", false); // 勉強フラグをオフにする

        return "redirect:/logs";
    }

    /**
     * Commentを記録
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return redirect:/logs
     */
    @PostMapping("/logs/comment")
    public String pushComment(Model model, RedirectAttributes attributes, @ModelAttribute @Validated CommentForm form,
            BindingResult bindingResult) {
        final String uid = form.getUid();

        // ユーザIDと名前をModelに追加する
        attributes.addAttribute("uid", uid);
        attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

        if (bindingResult.hasErrors()) { // コメントにエラーが含まれていた場合
            attributes.addFlashAttribute("isCommentFormError", true); // エラーフラグをオンにする

            return "redirect:/logs"; // 体調入力ページ
        }

        sls.remarkComment(sls.createCommentDto(form));

        return "redirect:/logs";
    }

    // /**
    // * ログを手動で追加する
    // *
    // * @param model
    // * @param attributes
    // * @param form
    // * @param bindingResult
    // * @return redirect:/logs
    // */
    // @PostMapping("/logs/lookback/create")
    // public String createLog(Model model, RedirectAttributes attributes,
    // @ModelAttribute @Validated CreateLogForm form,
    // BindingResult bindingResult) {
    // final String uid = form.getUid();

    // // ユーザIDと名前をModelに追加する
    // attributes.addAttribute("uid", uid);
    // attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

    // String sCode = sls.getSubject(uid).getSCode(); // 現在の勉強状況を所得

    // if (!sCode.equals("0")) { // 勉強中なら
    // attributes.addFlashAttribute("isCreateStudying", true); // エラーフラグをオンにする

    // return "redirect:/logs/lookback"; // 勉強中なら追加させない
    // }

    // try {
    // sls.createStudyLog(sls.createCreateLogDto(form));
    // } catch (OtherValidationException e) {
    // attributes.addFlashAttribute("isParseExceptionError", true); // エラーフラグをオンにする

    // return "redirect:/logs/lookback";
    // } catch (EventValidationException e) {
    // attributes.addFlashAttribute("isCreateEventExceptionError", true); //
    // エラーフラグをオンにする

    // return "redirect:/logs/lookback";
    // }

    // return "redirect:/logs/lookback";
    // }

    // /**
    // * endEventに紐づくlogとstartEventを削除
    // *
    // * @param model
    // * @param attributes
    // * @param form
    // * @param bindingResult
    // * @return redirect:/logs
    // */
    // @PostMapping("/logs/delete")
    // public String deleteEvent(Model model, RedirectAttributes attributes,
    // @ModelAttribute @Validated DeleteEventForm form, BindingResult bindingResult)
    // {
    // final String uid = form.getUid();

    // // ユーザIDと名前をModelに追加する
    // attributes.addAttribute("uid", uid);
    // attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

    // String sCode = sls.getSubject(uid).getSCode(); // 現在の勉強状況を所得

    // if (!sCode.equals("0")) { // 勉強中なら
    // attributes.addFlashAttribute("isDeleteStudying", true); // エラーフラグをオンにする

    // return "redirect:/logs"; // 勉強中なら削除させない
    // }

    // try {
    // sls.deleteStudyEvent(sls.createDeleteEventDto(form)); // EventとLogを削除
    // } catch (EventValidationException e) {
    // attributes.addFlashAttribute("isEventDoesNotExist", true); // エラーフラグをオンにする
    // } catch (LogValidationException e) {
    // attributes.addFlashAttribute("isLogDoesNotExist", true); // エラーフラグをオンにする
    // }

    // return "redirect:/logs";
    // }

    /**
     * ログを手動で追加する
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return redirect:/logs
     */
    @PostMapping("/logs/create")
    public String createLog(Model model, RedirectAttributes attributes, @ModelAttribute @Validated CreateLogForm form,
            BindingResult bindingResult) {
        final String uid = form.getUid();

        // ユーザIDと名前をModelに追加する
        attributes.addAttribute("uid", uid);
        attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

        String sCode = sls.getSubject(uid).getSCode(); // 現在の勉強状況を所得

        if (!sCode.equals("0")) { // 勉強中なら
            attributes.addFlashAttribute("isCreateStudying", true); // エラーフラグをオンにする

            return "redirect:/logs"; // 勉強中なら追加させない
        }

        try {
            sls.createStudyLog(sls.createCreateLogDto(form));
        } catch (SubjectValidationException e) {
            attributes.addFlashAttribute("isSubjectDoseNotExist", true); // エラーフラグをオンにする
        } catch (

        OtherValidationException e) {
            attributes.addFlashAttribute("isParseExceptionError", true); // エラーフラグをオンにする

            System.out.println(e.getMessage());

            return "redirect:/logs";
        } catch (EventValidationException e) {
            attributes.addFlashAttribute("isCreateEventExceptionError", true); // エラーフラグをオンにする

            System.out.println(e.getMessage());

            return "redirect:/logs";
        }

        return "redirect:/logs";
    }

    /**
     * endEventに紐づくlogとstartEventを削除
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return redirect:/logs
     */
    @PostMapping("/logs/delete")
    public String deleteEvent(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated DeleteEventForm form, BindingResult bindingResult) {
        final String uid = form.getUid();

        // ユーザIDと名前をModelに追加する
        attributes.addAttribute("uid", uid);
        attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

        String sCode = sls.getSubject(uid).getSCode(); // 現在の勉強状況を所得

        if (!sCode.equals("0")) { // 勉強中なら
            attributes.addFlashAttribute("isDeleteStudying", true); // エラーフラグをオンにする

            return "redirect:/logs"; // 勉強中なら削除させない
        }

        try {
            sls.deleteStudyEvent(sls.createDeleteEventDto(form)); // EventとLogを削除
        } catch (EventValidationException e) {
            attributes.addFlashAttribute("isEventDoesNotExist", true); // エラーフラグをオンにする
        } catch (LogValidationException e) {
            attributes.addFlashAttribute("isLogDoesNotExist", true); // エラーフラグをオンにする
        }

        return "redirect:/logs";
    }

    /**
     * endEventに紐づくlogとstartEventを削除
     *
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return redirect:/logs/lookback
     */
    @PostMapping("/logs/lookback/delete")
    public String deleteEventInLookBack(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated DeleteEventForm form, BindingResult bindingResult) {
        final String uid = form.getUid();

        // ユーザIDと名前をModelに追加する
        attributes.addAttribute("uid", uid);
        attributes.addFlashAttribute("nickname", us.getUser(uid).getNickname());

        String sCode = sls.getSubject(uid).getSCode(); // 現在の勉強状況を所得

        if (!sCode.equals("0")) { // 勉強中なら
            attributes.addFlashAttribute("isDeleteStudying", true); // エラーフラグをオンにする

            return "redirect:/logs/lookback"; // 勉強中なら削除させない
        }

        try {
            sls.deleteStudyEvent(sls.createDeleteEventDto(form)); // EventとLogを削除
        } catch (EventValidationException e) {
            attributes.addFlashAttribute("isEventDoesNotExist", true); // エラーフラグをオンにする
        } catch (LogValidationException e) {
            attributes.addFlashAttribute("isLogDoesNotExist", true); // エラーフラグをオンにする
        }

        return "redirect:/logs/lookback";
    }
}