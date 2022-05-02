package jp.kobe_u.cs27.GSESSService.common.domain.dto;

import jp.kobe_u.cs27.GSESSService.common.domain.entity.Subject;
import lombok.Data;

/**
 * SubjectのDTO
 *
 * @author ing
 */
@Data
public class SubjectDto {
    /** 科目コード */
    private String sCode;

    /** 科目名 */
    private String name;

    /** 説明 */
    private String description;

    /**
     * SubjectからDTOを生成
     *
     * @param subject
     * @return SubjectDto
     */
    public static SubjectDto build(Subject subject) {
        SubjectDto dto = new SubjectDto();

        dto.sCode = subject.getSCode();
        dto.name = subject.getName();
        dto.description = subject.getDescription();

        return dto;
    }
}
