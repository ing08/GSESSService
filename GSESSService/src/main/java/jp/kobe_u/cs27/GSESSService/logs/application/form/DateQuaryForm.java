package jp.kobe_u.cs27.GSESSService.logs.application.form;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DateQuaryのフォーム
 *
 * @author ing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateQuaryForm {
    /** 誰の */
    private String uid;

    /** いつから */
    private Date since;

    /** いつまで */
    private Date until;
}
