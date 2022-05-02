package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.Date;

// import jp.kobe_u.cs27.GSESSService.logs.application.form.EndStudyForm;
import lombok.Data;

/**
 * EndStudyEventのDTO
 *
 * @author ing
 */
@Data
public class EndStudyDto {
    /** 誰が */
    private String uid;

    /** どの科目を */
    private String sCode;

    /** 何時に終えたか */
    private Date until;

    // /**
    // * EndStudyのDTO
    // *
    // * @param EndStudyForm
    // * @return EndStudyDto
    // */
    // public static EndStudyDto build(EndStudyForm form) {
    // EndStudyDto dto = new EndStudyDto();

    // dto.uid = form.getUid();
    // dto.sCode = form.getSCode();
    // dto.until = new Date(); // 現在時刻を所得

    // return dto;
    // }
}
