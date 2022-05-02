package jp.kobe_u.cs27.GSESSService.logs.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 勉強イベントログのエンティティ
 *
 * @author ing
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StudyEvent {
    /** 通し番号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eid;

    /** 誰のイベントか */
    private String uid;

    /** どの科目を */
    private String sCode;

    /** いつのイベントか */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /** どのタイプのイベントか */
    private String type;

    /** コメント */
    private String comment;
}
