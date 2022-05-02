package jp.kobe_u.cs27.GSESSService.informations.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.informations.entity.Contents;

@Repository
public interface ContentsRepository extends CrudRepository<Contents, String> {

    // トピック名が既に存在しているか判定
    Boolean existsByTname(String tname);
    
    // 内容から部分文字列検索
    List<Contents> findByCommentContaining(String keyword);

}
