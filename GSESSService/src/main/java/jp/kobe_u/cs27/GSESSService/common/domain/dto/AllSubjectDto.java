package jp.kobe_u.cs27.GSESSService.common.domain.dto;

import java.util.List;

import jp.kobe_u.cs27.GSESSService.common.domain.entity.Subject;
import lombok.Data;

/**
 * すべてのSubjectのDTO
 *
 * @author ing
 */
@Data
public class AllSubjectDto {
    /** すべての科目 */
    private List<Subject> subjects;

    /**
     * List<Subject>からDTOを生成
     *
     * @param subjects
     * @return SubjectDto
     */
    public static AllSubjectDto build(List<Subject> subjects) {
        AllSubjectDto dto = new AllSubjectDto();

        dto.subjects = subjects;

        return dto;
    }
}
