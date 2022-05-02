package jp.kobe_u.cs27.GSESSService.logs.domain.repositry;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.logs.domain.entity.StudyLog;

/**
 * 勉強ログのリポジトリ
 *
 * @author ing
 */
@Repository
public interface StudyLogRepositry extends CrudRepository<StudyLog, Long> {
    /**
     * ユーザの勉強記録を日時検索
     *
     * @param uid
     * @param since
     * @param until
     * @return ログのリスト
     */
    public List<StudyLog> findByUidAndSinceGreaterThanEqualAndUntilLessThanEqualOrderBySinceDesc(String uid, Date since,
            Date until);

    // /**
    // * startEventIDでログを検索
    // *
    // * @param startEvent
    // * @return ログのリスト
    // */
    // public StudyLog findByStartEvent(Long startEvent);

    // /**
    // * endEventIDでログを検索
    // *
    // * @param endEvent
    // * @return ログのリスト
    // */
    // public Boolean existsByEndEvent(Long endEvent);

    /**
     * endEventIDでログを検索
     *
     * @param endEvent
     * @return
     */
    public StudyLog findByEndEvent(Long endEvent);

    // /**
    // * uidに紐づく最新のログを検索
    // *
    // * @param uid
    // * @return StudyLog
    // */
    // public StudyLog findFirstByUid(String uid);

    /**
     * uidに紐づくすべてのStudyLogを所得
     *
     * @param uid
     * @return List<StudyLog>
     */
    public List<StudyLog> findAllByUid(String uid);

    /**
     * ユーザID指定でStudyLogをすべて消去する
     *
     * @param uid
     */
    public void deleteByUid(String uid);

    /**
     * endEvent指定でLogを削除
     *
     * @param EndStudyEventID
     * @return StudyLog
     */
    public void deleteByEndEvent(Long endEvent);
}
