package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.Date;

// import jp.kobe_u.cs27.GSESSService.logs.application.form.StartStudyForm;
import lombok.Data;

/**
 * StartStudyEventのDTO
 *
 * @author ing
 */
@Data
public class StartStudyDto {
    /** 誰が */
    private String uid;

    /** どの科目を */
    private String sCode;

    /** 何時から始めたか */
    private Date since;

    // /**
    // * StartStudyのDTO
    // *
    // * @param StartStudyForm
    // * @return StartStudyDto
    // */
    // public static StartStudyDto build(StartStudyForm form) {
    // StartStudyDto dto = new StartStudyDto();

    // dto.uid = form.getUid();
    // dto.sCode = form.getSCode();
    // dto.since = new Date(); // 現在時刻を所得

    // return dto;
    // }
}
