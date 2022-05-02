package jp.kobe_u.cs27.GSESSService.logs.domain.repositry;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.logs.domain.entity.StudyEvent;

/**
 * 勉強イベントのリポジトリ
 *
 * @author ing
 */
@Repository
public interface StudyEventRepositry extends CrudRepository<StudyEvent, Long> {
    /**
     * ユーザ指定で現在の最新comment以外のStudyEventを所得
     *
     * @return StudyEvent
     */
    public StudyEvent findFirstByUidAndTypeNotOrderByCreatedAtDesc(String uid, String type);

    /**
     * ユーザの勉強イベントを日時検索
     *
     * @param uid
     * @param since
     * @param until
     * @return ログのリスト
     */
    public List<StudyEvent> findByUidAndCreatedAtBetweenOrderByCreatedAtDesc(String uid, Date since, Date until);

    /**
     * ユーザの勉強イベントで指定日時以前最初にあるものを所得
     *
     * @param uid
     * @param type
     * @param date
     * @return StudyEvent
     */
    public StudyEvent findFirstByUidAndTypeNotAndCreatedAtLessThanEqualOrderByCreatedAtDesc(String uid, String type,
            Date date);

    /**
     * ユーザの勉強イベントで指定日時以降最初にあるものを所得
     *
     * @param uid
     * @param type
     * @param date
     * @return StudyEvent
     */
    public StudyEvent findFirstByUidAndTypeNotAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(String uid, String type,
            Date date);

    /**
     * ユーザID指定でStudyEventをすべて消去する
     *
     * @param uid
     */
    public void deleteByUid(String uid);
}
