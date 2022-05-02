package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.Date;

import lombok.Data;

/**
 * StudyEventのDTO
 *
 * @author ing
 */
@Data
public class StudyEventDto {
    /** 通し番号 */
    private Long eid;

    /** 誰のイベントか */
    private String uid;

    /** どの科目を */
    private String name;

    /** いつのイベントか */
    private Date date;

    /** どのタイプのイベントか */
    private String type;

    /** コメント */
    private String comment;

    // /**
    // * StudyEventからDTOを生成
    // *
    // * @param StudyEvent
    // * @return StudyEventDto
    // */
    // public static StudyEventDto build(StudyEvent event) {
    // StudyEventDto dto = new StudyEventDto();

    // dto.eid = event.getEid();
    // dto.uid = event.getUid();
    // // dto.name = event.getSCode();
    // dto.date = event.getDate();
    // dto.type = event.getType();
    // dto.comment = event.getComment();

    // return dto;
    // }
}
