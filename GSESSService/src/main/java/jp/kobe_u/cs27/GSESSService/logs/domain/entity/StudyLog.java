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
 * 勉強ログのエンティティ
 *
 * @author ing
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StudyLog {
    /** 通し番号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lid;

    /** 誰のログか */
    private String uid;

    /** どの科目を */
    private String sCode;

    /** 何時から */
    @Temporal(TemporalType.TIMESTAMP)
    private Date since;

    /** 何時まで */
    @Temporal(TemporalType.TIMESTAMP)
    private Date until;

    /** 何秒勉強したか */
    private int second;

    /** 開始イベントID */
    private Long startEvent;

    /** 終了イベントID */
    private Long endEvent;
}
