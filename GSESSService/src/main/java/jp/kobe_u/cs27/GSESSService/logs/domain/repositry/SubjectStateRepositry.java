package jp.kobe_u.cs27.GSESSService.logs.domain.repositry;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.logs.domain.entity.SubjectState;

/**
 * 勉強状況のリポジトリ
 *
 * @author ing
 */
@Repository
public interface SubjectStateRepositry extends CrudRepository<SubjectState, String> {
    /** 状況全所得 */
    public List<SubjectState> findAll();
}
