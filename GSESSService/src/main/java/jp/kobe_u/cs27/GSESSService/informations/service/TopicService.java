package jp.kobe_u.cs27.GSESSService.informations.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import jp.kobe_u.cs27.GSESSService.informations.dto.TopicQueryResult;
import jp.kobe_u.cs27.GSESSService.informations.entity.Contents;
import jp.kobe_u.cs27.GSESSService.informations.entity.Topic;
import jp.kobe_u.cs27.GSESSService.informations.exception.ErrorCode;
import jp.kobe_u.cs27.GSESSService.informations.exception.TopicException;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicForm;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsForm;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsQueryForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicAndSubjectForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicQueryForm;
import jp.kobe_u.cs27.GSESSService.informations.repository.TopicRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final ContentsService contentsService;

    /**
     * トピックを追加する
     * 
     * @param form TopicNameForm
     * @return 追加したトピック
     */
    public Topic createTopic(String subject,String uid,TopicForm form) {
        final String tname = form.getTname();

        // 同じ名前のトピックが既に存在している場合、例外を投げる
        if (topicRepository.existsByTname(tname)) {
            throw new TopicException(ErrorCode.TOPIC_ALREADY_EXISTS, "create the topic",
                    String.format("topic %s already exists", tname));
        }
        
        return topicRepository.save(new Topic(null, tname,subject,uid,new Date()));
    }

    /**
     * トピックを取得する
     * 
     * @param tid トピックID
     * @return トピック
     */
    public Topic getTopic(Long tid) {

        return topicRepository.findById(tid).orElseThrow(() -> new TopicException(ErrorCode.TOPIC_DOES_NOT_EXIST,
                "get the topic", String.format("topicid %s does not exist", tid)));
    }
    /**
     * トピックをトピック名から取得する
     * 
     * @param tid トピックID
     * @return トピック
     */
    public Topic getTopicFromTname(String tname) {

        return topicRepository.findByTname(tname).orElseThrow(() -> new TopicException(ErrorCode.TOPIC_DOES_NOT_EXIST,
                "get the topic", String.format("topicid %s does not exist", tname)));
    }

    /**
     * 最後にトピックを編集した人を更新する
     * 
     * @param form TopicForm
     * @return 編集したトピック
     */
    public Topic updateTopicEditer(Topic topic,String uid) {
        String tname = topic.getTname();
        if (!topicRepository.existsByTname(tname)) {
            throw new TopicException(ErrorCode.TOPIC_DOES_NOT_EXIST, "create the topic",
                    String.format("topic %s does not exist", tname));
        }

        // DB上のユーザ情報を更新し、新しいユーザ情報を戻り値として返す
        return topicRepository.save(new Topic(topic.getTid(), topic.getTname(),topic.getSubject(),uid,new Date()));
    }
    /**
     * 最後にトピックを編集した人を更新する
     * 
     * @param form TopicForm
     * @return 編集したトピック
     */
    /*public Topic updateTopic(Topic topic,String newTname) {
        String tname = topic.getTname();
        if (!topicRepository.existsByTname(tname)) {
            throw new TopicException(ErrorCode.TOPIC_DOES_NOT_EXIST, "create the topic",
                    String.format("topic %s does not exist", tname));
        }

        // DB上のユーザ情報を更新し、新しいユーザ情報を戻り値として返す
        return topicRepository.save(new Topic(topic.getTid(), newTname,topic.getSubject(),topic.getUid(),topic.getCreatedDay()));
    }*/

    /**
     * トピックを削除する 処理に失敗した場合、このメソッド中のDB操作はすべてロールバックされる
     *
     * @param id ユーザID
     */
    @Transactional
    public void deleteTopic(TopicForm form) {

        String tname = form.getTname();
        // トピックが存在しない場合、例外を投げる
        if (!topicRepository.existsByTname(tname)) {
            throw new TopicException(ErrorCode.TOPIC_DOES_NOT_EXIST, "delete the user",
                    String.format("topic %s does not exist", tname));
        }

        // トピックを削除する
        topicRepository.deleteByTname(tname);
        //トピック名で紐づいたトピック内容を削除する
        contentsService.deleteContents(tname);
    }

    /**
     * 教科名からトピックを検索する
     * 
     * @param form TopicQueryForm
     * @return 検索結果
     */
    public TopicQueryResult queryFromSubject(TopicQueryForm form) {

        // フォームの中身を変数に格納する
        final String subject = form.getKeyword();
        List<Topic> topicList;

        topicList = topicRepository.findBySubject(subject);
        return new TopicQueryResult(subject, topicList);
    }
    /**
     * トピック名からトピックを検索する(部分文字列検索)
     * 
     * @param form TopicQueryForm
     * @return 検索結果
     */
    public TopicQueryResult queryFromTnameContaining(TopicQueryForm form) {

        // フォームの中身を変数に格納する
        final String keyword = form.getKeyword();
        List<Topic> topicList;

        topicList = topicRepository.findByTnameContaining(keyword);
        return new TopicQueryResult(keyword, topicList);
    }

    /**
     * トピック名と教科からトピックを検索する(部分文字列検索)
     * 
     * @param form TopicQueryForm
     * @return 検索結果
     */
    public TopicQueryResult queryFromSubjectAndTnameContaining(String subject,String tname) {

        
        List<Topic> topicList;

        topicList = topicRepository.findBySubjectAndTnameContaining(subject,tname);
        return new TopicQueryResult(tname, topicList);
    }
    /**
     * トピック名からトピックを検索する
     * 
     * @param form TopicQueryForm
     * @return 検索結果
     */
    public TopicQueryResult queryFromTname(TopicQueryForm form) {

        // フォームの中身を変数に格納する
        final String keyword = form.getKeyword();
        List<Topic> topicList = new ArrayList<Topic>();

        topicList.add(topicRepository.findByTname(keyword).get());
        return new TopicQueryResult(keyword, topicList);
    }

    /**
     * トピック内容からトピックを検索する
     * 
     * @param form TopicQueryForm
     * @return 検索結果
     */
    public TopicQueryResult queryFromTcomment(String subject,String tname) {

        // フォームの中身を変数に格納する
        List<Topic> topicList;

        
        // ContentsListをTopicListに変換する処理も含んでいる
        topicList = toTopicList(contentsService.queryContaining(new ContentsQueryForm(tname)).getContentsList());


        return new TopicQueryResult(tname, topicList);
    }

    /**
     * ContentsからTopicを返す
     * 
     * @param content Contents
     * @return トピック
     */
    public Topic toTopic(Contents content) {
        String tname = content.getTname();
        TopicQueryResult result = queryFromTnameContaining(new TopicQueryForm(tname));

        // 同じトピック名が2つ存在していた場合、例外を投げる
        if (result.getTopics().size() != 1) {
            throw new TopicException(ErrorCode.MORE_THAN_ONE_CONTENTS_EXISTS, "query Topic from content",
                    String.format("more than one topicName %s exist", tname));
        }

        return result.getTopics().get(0);

    }

    /**
     * ContentsのListからTopicのListを返す
     * 
     * @param contentsList List<Contents>
     * @return トピックリスと
     */
    public List<Topic> toTopicList(List<Contents> contentsList) {
        List<Topic> topicList = new ArrayList<Topic>();
        for (Contents content : contentsList) {
            topicList.add(toTopic(content));
        }
        return topicList;
    }

}
