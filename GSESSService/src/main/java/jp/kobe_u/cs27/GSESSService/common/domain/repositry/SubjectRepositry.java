package jp.kobe_u.cs27.GSESSService.common.domain.repositry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.common.domain.entity.Subject;

/**
 * 科目のリポジトリ
 *
 * @author ing
 */
@Repository
public interface SubjectRepositry extends CrudRepository<Subject, String> {
    /**
     * 科目名で科目を所得
     *
     * @param name
     * @return Subject
     */
    public Subject findByName(String name);

    // /**
    // * 任意の科目コード以外の科目をすべて所得
    // *
    // * @param sCode
    // * @return List<Subject>
    // */
    // public Iterable<Subject> findAllByIdNot(String sCode);
}
