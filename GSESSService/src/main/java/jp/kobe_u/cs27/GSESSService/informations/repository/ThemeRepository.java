package jp.kobe_u.cs27.GSESSService.informations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs27.GSESSService.informations.entity.Theme;
import jp.kobe_u.cs27.GSESSService.informations.entity.Topic;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long> {

    List<Theme> findByRequiredRankLessThanEqual(int editRank);
}
