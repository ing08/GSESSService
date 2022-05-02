package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import lombok.Data;

/**
 * DeleteEventのDTO
 *
 * @author ing
 */
@Data
public class DeleteEventDto {
    /** 誰が */
    private String uid;

    /** どのイベントを */
    private Long eid;

    /** タイプ */
    private String type;
}
