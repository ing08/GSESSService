package jp.kobe_u.cs27.GSESSService.common.application.form;

import lombok.*; // アノテーションによるコード自動生成を行うライブラリ・ツール

import javax.validation.constraints.NotBlank; // null であってはならず、空白文字以外の文字が少なくとも 1 つ含まれている必要がある
import javax.validation.constraints.NotNull; //  null ではない必要がある
import javax.validation.constraints.Pattern; // 指定された正規表現と一致する必要がある

import jp.kobe_u.cs27.GSESSService.common.domain.entity.Subject;

/**
 * ユーザフォーム
 *
 * @author ing
 */
@Getter // 全フィールドのgetterを生成 lombok.Getter
@Setter // 全フィールドのsetterを生成 lombok.Setter
@NoArgsConstructor // 引数なしコンストラクタ(デフォルトコンストラクタ)を生成 lombok.NoArgsConstructor
@AllArgsConstructor // すべてのフィールドを引数に持つコンストラクタを生成 lombok.AllArgsConstructor
public class SubjectForm {
    /** 科目コード */
    @Pattern(regexp = "[0-9A-Z]+") // 指定された正規表現と一致する必要がある javax.validation.constraints.Pattern
    @NotNull // null ではない必要がある javax.validation.constraints.NotNul
    private String sCode;

    /** 科目名 */
    // @Pattern(regexp = "[0-9a-zA-Z_\\-]+") // 指定された正規表現と一致する必要がある
    // javax.validation.constraints.Pattern
    @NotNull // null ではない必要がある javax.validation.constraints.NotNul
    private String name;

    /** 説明 */
    @NotBlank // null であってはならず、空白文字以外の文字が少なくとも 1 つ含まれている必要がある
              // javax.validation.constraints.NotBlank
    private String description;

    /**
     * フォームをエンティティに変換
     *
     * @return Subject
     */
    public Subject toEntity() {
        return new Subject(this.getSCode(), this.getName(), this.getDescription());
    }
}