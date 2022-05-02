package jp.kobe_u.cs27.GSESSService.common.application.controller.rest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import jp.kobe_u.cs27.GSESSService.common.application.form.UserForm;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.UserService;
import jp.kobe_u.cs27.GSESSService.common.domain.dto.UserDto;
import jp.kobe_u.cs27.GSESSService.common.domain.entity.User;

/**
 * Userを操作するAPIを定義するRESTコントローラ
 */
@RestController // Jsonを返す
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {
    /** ユーザのサービス */
    private final UserService us;

    // Create
    /**
     * ユーザ登録
     *
     * @param form
     * @return UserDto
     */
    @PostMapping("/users")
    public UserDto addUser(@RequestBody @Validated UserForm form) {
        User user = us.createUser(form.toEntity());
        return UserDto.build(user);
    }

    // Read
    /**
     * ユーザ確認(ログイン)
     *
     * @param uid
     * @return UserDto
     */
    @GetMapping("/users/{uid}")
    public UserDto getUser(@PathVariable String uid) {
        return us.getUser(uid);
    }

    // Delete
    /**
     * ユーザ削除
     *
     * @param uid
     * @return true
     */
    @DeleteMapping("/users/{uid}")
    public Boolean deleteUser(@PathVariable String uid) {
        us.deleteUser(uid);

        return true;
    }
}
