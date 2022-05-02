package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.Date;

import lombok.Data;

/**
 * DateQuaryのDTO
 *
 * @author ing
 */
@Data
public class DateQuaryDto {
    /** 誰の */
    private String uid;

    /** いつから */
    private Date since;

    /** いつまで */
    private Date until;
}
