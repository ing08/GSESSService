package jp.kobe_u.cs27.GSESSService.logs.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * StartStudyEventのフォーム
 *
 * @author ing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StartStudyForm {
    /** 誰が */
    private String uid;

    /** 何の科目を始めたか */
    private String sCode;
}
