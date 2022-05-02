package jp.kobe_u.cs27.GSESSService.informations.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.*;

/**
 * ユーザの編集者ランクや使用テーマを保持するエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoStatus {
    @Id
    private String uid;

    //編集者ランク
    private int editRank;

    //使用テーマ
    private String theme;

    //編集した日にち
    Date editDay;

}
