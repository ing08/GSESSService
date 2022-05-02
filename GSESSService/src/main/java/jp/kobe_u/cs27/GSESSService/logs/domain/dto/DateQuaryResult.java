package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.List;

import lombok.Data;

/**
 * DateQuaryResurtのDTO
 *
 * @author ing
 */
@Data
public class DateQuaryResult {
    /** 誰の */
    private String uid;

    /** ある日のStudyEventのリスト */
    List<StudyEventDto> events;
}
