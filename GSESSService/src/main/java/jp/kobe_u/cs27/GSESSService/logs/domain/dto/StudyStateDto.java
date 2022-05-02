package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.Date;

import lombok.Data;

/**
 * LastStudyLogのDTO
 *
 * @author ing
 */
@Data
public class StudyStateDto {
    /** 誰の状況か */
    private String uid;

    /** 勉強中かどうか */
    private Boolean flag;

    /** どの科目を */
    private String name;

    /** 何時まで */
    private Date until;

    /** 何秒勉強したか */
    private int second;
}
