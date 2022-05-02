package jp.kobe_u.cs27.GSESSService.logs.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 勉強イベントログのエンティティ
 *
 * @author ing
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectState {
    /** あるユーザが */
    @Id
    private String uid;

    /** 勉強中かどうか 0なら何もしていない */
    private String sCode;
}
