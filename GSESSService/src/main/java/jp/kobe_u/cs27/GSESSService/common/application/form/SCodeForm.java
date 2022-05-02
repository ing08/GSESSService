package jp.kobe_u.cs27.GSESSService.common.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 科目コードフォーム
 *
 * @author ing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SCodeForm {
    /** 科目コード */
    @Pattern(regexp = "[0-9a-zA-Z_\\-]+")
    @NotNull
    private String sCode;
}
