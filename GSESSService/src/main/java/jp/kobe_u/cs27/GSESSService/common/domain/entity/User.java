package jp.kobe_u.cs27.GSESSService.common.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.*;

/**
 * ユーザのエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // ユーザID
    @Id
    private String uid;

    // ニックネーム
    private String nickname;

    // メールアドレス
    private String email;
}
