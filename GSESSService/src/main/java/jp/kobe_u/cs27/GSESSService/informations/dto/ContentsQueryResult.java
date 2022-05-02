package jp.kobe_u.cs27.GSESSService.informations.dto;

import lombok.Data;

import java.util.List;

import jp.kobe_u.cs27.GSESSService.informations.entity.Contents;

/**
 * 体調記録の検索結果
 */
@Data
public class ContentsQueryResult {

  
  // 検索キーワード
  private final String keyword;
  // 検索結果のリスト
  private final List<Contents> contentsList;

}
