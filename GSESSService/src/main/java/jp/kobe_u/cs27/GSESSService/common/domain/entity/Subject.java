package jp.kobe_u.cs27.GSESSService.common.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

/**
 * 科目のエンティティ
 *
 * @author ing
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    /** 科目コード */
    @Id
    private String sCode;

    /** 科目名 */
    private String name;

    /** 科目の説明 */
    private String description;
}
