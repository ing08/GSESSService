package jp.kobe_u.cs27.GSESSService.informations.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * トピックの内容を持つエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contents {
    //トピック名
    @Id
    private String tname;

    //トピック内容
    private String comment;
}
