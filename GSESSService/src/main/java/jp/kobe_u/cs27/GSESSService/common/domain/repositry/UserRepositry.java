package jp.kobe_u.cs27.GSESSService.common.domain.repositry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.common.domain.entity.User;

/**
 * ユーザのリポジトリ
 *
 * @author ing
 */
@Repository
public interface UserRepositry extends CrudRepository<User, String> {
}