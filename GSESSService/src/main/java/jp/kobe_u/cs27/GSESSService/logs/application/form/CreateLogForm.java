package jp.kobe_u.cs27.GSESSService.logs.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AddEventのフォーム
 *
 * @author ing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLogForm {
    /** 誰が */
    private String uid;

    /** どの科目を */
    private String name;

    /** 何時から */
    private String since;

    /** 何時まで */
    private String until;
}
