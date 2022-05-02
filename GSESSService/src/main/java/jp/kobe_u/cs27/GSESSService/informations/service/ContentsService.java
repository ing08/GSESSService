package jp.kobe_u.cs27.GSESSService.informations.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import jp.kobe_u.cs27.GSESSService.informations.dto.ContentsQueryResult;
import jp.kobe_u.cs27.GSESSService.informations.entity.Contents;
import jp.kobe_u.cs27.GSESSService.informations.exception.ErrorCode;
import jp.kobe_u.cs27.GSESSService.informations.exception.TopicException;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsForm;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsQueryForm;
import jp.kobe_u.cs27.GSESSService.informations.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentsService {
    private final ContentsRepository contentsRepository;

    /**
     * トピックの内容を追加する
     * 
     * @param form TopicNameForm
     * @return 追加したトピック
     */
    public Contents createContents(String tname,ContentsForm form) {
        // 同じ名前のトピックが既に存在している場合、例外を投げる
        if (contentsRepository.existsById(tname)) {
            throw new TopicException(ErrorCode.CONTENTS_ALREADY_EXISTS, "create the topic",
                    String.format("topic %s already exists", tname));
        }
        return contentsRepository.save(new Contents(tname, form.getContents()));
    }

    /**
     * コンテンツを取得する
     * 
     * @param tname トピック名
     * @return コンテンツ
     */
    public Contents getContents(String tname) {

        return contentsRepository.findById(tname).orElseThrow(() -> new TopicException(ErrorCode.CONTENTS_DOES_NOT_EXIST,
                "get the contents", String.format("topicName %s does not exist", tname)));
    }

    /**
     * トピックの内容を更新する
     * 
     * @param form ContentsForm
     * @return 編集したトピック内容
     */
    public Contents updateContents(String tname,ContentsForm form) {
        if (!contentsRepository.existsById(tname)) {
            throw new TopicException(ErrorCode.CONTENTS_DOES_NOT_EXIST, "create the contents",
                    String.format("topicName %s does not exist", tname));
        }

        // DB上のユーザ情報を更新し、新しいユーザ情報を戻り値として返す
        return contentsRepository.save(new Contents(tname, form.getContents()));
    }

    /**
     * コンテンツを削除する 処理に失敗した場合、このメソッド中のDB操作はすべてロールバックされる
     *
     * @param tname コンテンツ名
     */
    public void deleteContents(String tname) {

        // トピックが存在しない場合、例外を投げる
        if (!contentsRepository.existsById(tname)) {
            throw new TopicException(ErrorCode.CONTENTS_DOES_NOT_EXIST, "delete the user",
                    String.format("topicName %s does not exist", tname));
        }

        // トピックを削除する
        contentsRepository.deleteById(tname);
    }

    /**
     * トピック内容を検索する
     * 
     * @param form ContentsQueryForm
     * @return 検索結果
     */
    public ContentsQueryResult query(ContentsQueryForm form) {

        // フォームの中身を変数に格納する
        final String keyword = form.getKeyword();
        List<Contents> contentsList = new ArrayList<Contents>();

        //トピック名で紐づいたトピック内容を取得する
        //リスト形式だが要素は一つしか入らない
        contentsList.add(contentsRepository.findById(keyword).get());
        
        
 
        return new ContentsQueryResult(keyword, contentsList);
    }

    /**
     * トピック内容を部分文字列検索する
     * 
     * @param form ContentsQueryForm
     * @return 検索結果
     */
    public ContentsQueryResult queryContaining(ContentsQueryForm form) {

        // フォームの中身を変数に格納する
        final String keyword = form.getKeyword();
        List<Contents> contentsList;

        //
        contentsList = contentsRepository.findByCommentContaining(keyword);

        return new ContentsQueryResult(keyword, contentsList);
    }
}
