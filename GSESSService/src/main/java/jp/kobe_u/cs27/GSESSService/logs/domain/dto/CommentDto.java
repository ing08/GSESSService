package jp.kobe_u.cs27.GSESSService.logs.domain.dto;

import java.util.Date;

// import jp.kobe_u.cs27.GSESSService.logs.application.form.CommentForm;
import lombok.Data;

/**
 * CommentのDTO
 *
 * @author ing
 */
@Data
public class CommentDto {
    /** 誰が */
    private String uid;

    /** 何をコメントしたか */
    private String comment;

    /** いつ */
    private Date date;

    // /**
    // * CommentのDTO
    // *
    // * @param CommentForm
    // * @return CommentDto
    // */
    // public static CommentDto build(CommentForm form) {
    // CommentDto dto = new CommentDto();

    // dto.uid = form.getUid();
    // dto.comment = form.getComment();
    // dto.date = new Date(); // 現在時刻を所得

    // return dto;
    // }
}
