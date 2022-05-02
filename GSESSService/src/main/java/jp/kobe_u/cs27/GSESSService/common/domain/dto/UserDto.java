package jp.kobe_u.cs27.GSESSService.common.domain.dto;

import jp.kobe_u.cs27.GSESSService.common.domain.entity.User;
import lombok.Data;

/**
 * UserのDTO
 *
 * @author ing
 */
@Data
public class UserDto {
    /** ユーザID */
    private String uid;

    /** 名前 */
    private String nickname;

    /** E-mail */
    private String email;

    /**
     * UserからDTOを生成
     *
     * @param user
     * @return UserDto
     */
    public static UserDto build(User user) {
        UserDto dto = new UserDto();

        dto.uid = user.getUid();
        dto.nickname = user.getNickname();
        dto.email = user.getEmail();

        return dto;
    }
}
