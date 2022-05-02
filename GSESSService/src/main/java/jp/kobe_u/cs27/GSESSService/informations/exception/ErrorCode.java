package jp.kobe_u.cs27.GSESSService.informations.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * エラーコード
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
  TOPIC_DOES_NOT_EXIST(11),
  TOPIC_ALREADY_EXISTS(12),
  CONTENTS_DOES_NOT_EXIST(13),
  CONTENTS_ALREADY_EXISTS(14),
  MORE_THAN_ONE_CONTENTS_EXISTS(15),
  CONTROLLER_VALIDATION_ERROR(97),
  CONTROLLER_REJECTED(98),
  OTHER_ERROR(99);

  private final int code;
}
