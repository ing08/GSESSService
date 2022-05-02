package jp.kobe_u.cs27.GSESSService.logs.application.form;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Commentのフォーム
 *
 * @author ing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {
    /** 誰が */
    private String uid;

    /** 何をコメントしたか */
    @NotNull
    private String comment;
}
