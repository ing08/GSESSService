package jp.kobe_u.cs27.GSESSService.common.configration.exception;

import lombok.AllArgsConstructor; // すべてのフィールドを引数に持つコンストラクタを生成
import lombok.Getter; // フィールドのgetterを生成

/**
 * 共通のエラーコード
 *
 * @author ing feat. Mr.Nakata
 */
@AllArgsConstructor // すべてのフィールドを引数に持つコンストラクタを生成 lombok.AllArgsConstructor
@Getter // 全フィールドのgetterを生成 lombok.Getter
public enum ErrorCode { // 複数の定数をひとつにまとめておくことができる型
    USER_DOES_NOT_EXIST(11), USER_ALREADY_EXISTS(12), SUBJECT_DOES_NOT_EXIST(13), SUBJECT_ALREADY_EXISTS(14),
    CONTROLLER_VALIDATION_ERROR(97), CONTROLLER_REJECTED(98), OTHER_ERROR(99);

    private final int code;
}
