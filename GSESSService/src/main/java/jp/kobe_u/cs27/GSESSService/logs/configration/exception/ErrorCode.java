package jp.kobe_u.cs27.GSESSService.logs.configration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * logsのエラーコード
 *
 * @author ing
 */
@AllArgsConstructor // すべてのフィールドを引数に持つコンストラクタを生成 lombok.AllArgsConstructor
@Getter // 全フィールドのgetterを生成 lombok.Getter
public enum ErrorCode {
    EVENT_START_END_NOT_MATCH(15), EVENT_START_END_NOT_MATCH_SUBJECT(16), EVENT_DOES_NOT_EXIST(17), EVENT_NOT_FAST(18),
    LOG_DOES_NOT_EXIST(19), SUBJECT_STATE_DOES_NOT_EXIST(20), USER_ALREADY_EXISTS(21), PERSE_EXCEPTION(22);

    private final int code;
}
