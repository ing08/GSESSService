package jp.kobe_u.cs27.GSESSService.informations.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * テーマの情報のエンティティ
 */
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tid;

    int requiredRank;

    String themeName;
    
}
