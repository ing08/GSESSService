package jp.kobe_u.cs27.GSESSService.informations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.informations.entity.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    // トピック名が既に存在しているか判定
    Boolean existsByTname(String tname);

    // 部分文字列検索(トピック名から)
    List<Topic> findByTnameContaining(String tname);

    //トピック名から検索
    Optional<Topic> findByTname(String tname);

    // 教科から検索(教科選択画面からトピック一覧画面に遷移するとき用)
    List<Topic> findBySubject(String subject);

    //教科とトピック名から検索
    List<Topic> findBySubjectAndTnameContaining(String subject,String tname);

    //トピック名から削除
    void deleteByTname(String tname);

    // // 部分文字列検索(内容から)
    // List<Topic> findByCommentContaining(String keyword);
}
