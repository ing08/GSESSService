package jp.kobe_u.cs27.GSESSService.informations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.informations.entity.Topic;
import jp.kobe_u.cs27.GSESSService.informations.entity.UserInfoStatus;

@Repository
public interface UserInfoStatusRepository extends CrudRepository<UserInfoStatus, String> {

    

}
