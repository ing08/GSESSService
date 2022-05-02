package jp.kobe_u.cs27.GSESSService.informations.form;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * ユーザ登録・更新フォーム
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicForm {


  // トピック名
  @NotBlank
  private String tname;

}
