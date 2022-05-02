package jp.kobe_u.cs27.GSESSService.informations.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.kobe_u.cs27.GSESSService.informations.entity.Theme;
import jp.kobe_u.cs27.GSESSService.informations.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    //テーマ追加用に使うメソッド 
    public void updateTheme(){
        themeRepository.deleteAll();
        
        themeRepository.save(new Theme(null,1,"orange1"));
        themeRepository.save(new Theme(null,2,"blue1"));
        themeRepository.save(new Theme(null,3,"grean1"));
        themeRepository.save(new Theme(null,4,"black1"));
        themeRepository.save(new Theme(null,5,"blue2"));
        themeRepository.save(new Theme(null,6,"red2"));
        themeRepository.save(new Theme(null,7,"paple2"));
        themeRepository.save(new Theme(null,8,"grean2"));
        themeRepository.save(new Theme(null,9,"blue3"));
        themeRepository.save(new Theme(null,10,"Royal"));
    }

    public List<Theme> queryThemes(int editRank){
        return themeRepository.findByRequiredRankLessThanEqual(editRank);
    }
}
