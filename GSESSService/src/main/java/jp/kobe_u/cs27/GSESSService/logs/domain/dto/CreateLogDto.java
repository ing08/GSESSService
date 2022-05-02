package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.Date;

import lombok.Data;

/**
 * CreateStudyEventのDTO
 *
 * @author ing
 */
@Data
public class CreateLogDto {
    /** 誰が */
    private String uid;

    /** どの科目を */
    private String sCode;

    /** 何時から */
    private Date since;

    /** 何時まで */
    private Date until;
}
