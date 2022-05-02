package jp.kobe_u.cs27.GSESSService.logs.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EndStudyEventのフォーム
 *
 * @author ing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndStudyForm {
    /** 誰が */
    private String uid;

    /** 何の科目を終えたか */
    private String sCode;
}
