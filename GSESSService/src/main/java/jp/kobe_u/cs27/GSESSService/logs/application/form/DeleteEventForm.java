package jp.kobe_u.cs27.GSESSService.logs.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DeleteEventのフォーム
 *
 * @author ing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEventForm {
    /** 誰が */
    private String uid;

    /** どのイベントを */
    private Long eid;

    /** タイプ */
    private String type;
}
