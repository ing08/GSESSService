package jp.kobe_u.cs27.GSESSService.common.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * ユーザIDフォーム
 *
 * @author ing feat.Mr.Nakata
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UidForm {
    /** ユーザID */
    @Pattern(regexp = "[0-9a-zA-Z_\\-]+")
    @NotNull
    private String uid;
}
