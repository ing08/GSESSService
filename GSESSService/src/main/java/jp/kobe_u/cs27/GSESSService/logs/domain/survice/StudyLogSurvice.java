package jp.kobe_u.cs27.GSESSService.logs.domain.survice;

import org.springframework.stereotype.Service;

import jp.kobe_u.cs27.GSESSService.common.domain.Service.SubjectService;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.UserService;
import jp.kobe_u.cs27.GSESSService.common.domain.dto.SubjectDto;
import jp.kobe_u.cs27.GSESSService.common.domain.entity.User;
import jp.kobe_u.cs27.GSESSService.logs.application.form.CommentForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.CreateLogForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.DateQuaryForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.DeleteEventForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.EndStudyForm;
import jp.kobe_u.cs27.GSESSService.logs.application.form.StartStudyForm;
import jp.kobe_u.cs27.GSESSService.logs.configration.exception.EventValidationException;
import jp.kobe_u.cs27.GSESSService.logs.configration.exception.LogValidationException;
import jp.kobe_u.cs27.GSESSService.logs.configration.exception.OtherValidationException;
import jp.kobe_u.cs27.GSESSService.logs.configration.exception.SubjectStateValidationException;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.CommentDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.CreateLogDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.DateQuaryDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.DateQuaryResult;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.DeleteEventDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.EndStudyDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.StartStudyDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.dto.StudyEventDto;
import jp.kobe_u.cs27.GSESSService.logs.domain.entity.StudyEvent;
import jp.kobe_u.cs27.GSESSService.logs.domain.entity.StudyLog;
import jp.kobe_u.cs27.GSESSService.logs.domain.entity.SubjectState;
import jp.kobe_u.cs27.GSESSService.logs.domain.repositry.StudyEventRepositry;
import jp.kobe_u.cs27.GSESSService.logs.domain.repositry.StudyLogRepositry;
import jp.kobe_u.cs27.GSESSService.logs.domain.repositry.SubjectStateRepositry;
import lombok.RequiredArgsConstructor;

import static jp.kobe_u.cs27.GSESSService.logs.configration.exception.ErrorCode.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

/**
 * StudyLogのサービス
 *
 * @author ing
 */
@Service
@RequiredArgsConstructor
public class StudyLogSurvice {
    /** 勉強イベントログのリポジトリ */
    private final StudyEventRepositry ser;
    /** 勉強時間ログのリポジトリ */
    private final StudyLogRepositry slr;
    /** 勉強状況のリポジトリ */
    private final SubjectStateRepositry ssr;
    private final UserService us;
    /** 科目のサービス */
    private final SubjectService ss;

    /**
     * 現在ユーザが行っている科目の情報を所得
     *
     * @param uid
     * @return SubjectDto
     */
    public SubjectDto getSubject(String uid) {
        SubjectDto dto = new SubjectDto();
        SubjectState sst = ssr.findById(uid)
                .orElseThrow(() -> new SubjectStateValidationException(SUBJECT_STATE_DOES_NOT_EXIST,
                        "get the subjectState", String.format("subjecstate %s does not exist", uid)));

        dto.setSCode(sst.getSCode());
        if (!dto.getSCode().equals("0")) {
            dto.setName(ss.getSubject(sst.getSCode()).getName());
        }

        return dto;
    }

    /**
     * 範囲指定でStudyEventを所得
     *
     * @param DateQuaryDto
     * @return DateQuaryResult
     */
    public DateQuaryResult getStudyEventByDate(DateQuaryDto dto) {
        DateQuaryResult result = new DateQuaryResult();
        ArrayList<StudyEventDto> list = new ArrayList<StudyEventDto>();

        result.setUid(dto.getUid());

        // Date -> LocalDate -> Date
        Date since = Date.from(dto.getSince().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date until = Date.from(dto.getUntil().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (StudyEvent event : ser.findByUidAndCreatedAtBetweenOrderByCreatedAtDesc(dto.getUid(), since, until)) { // 検索範囲のStudyEventを所得
            // StudyEventDtoを作成
            StudyEventDto sed = new StudyEventDto();

            sed.setEid(event.getEid());
            sed.setUid(event.getUid());
            if (!event.getType().equals("comment")) { // commentなら
                sed.setName(ss.getSubject(event.getSCode()).getName());
            }
            sed.setDate(event.getCreatedAt());
            sed.setType(event.getType());
            sed.setComment(event.getComment());

            list.add(sed);
        }

        result.setEvents(list);

        return result;
    }

    /**
     * DateQuaryFormからDateQuaryDtoを作成
     *
     * @param DateQuaryForm
     * @return DateQuaryDto
     */
    public DateQuaryDto createDateQuaryDto(DateQuaryForm form) {
        DateQuaryDto dto = new DateQuaryDto();

        dto.setUid(form.getUid());
        dto.setSince(form.getSince());
        dto.setUntil(form.getUntil());

        return dto;
    }

    // /**
    // * 勉強状況の表示リストを作成
    // *
    // * @return List<StudyStateDto>
    // */
    // public List<StudyStateDto> createStudyStatus() {
    // List<StudyStateDto> statusList = new ArrayList<StudyStateDto>();
    // List<SubjectState> statuss = ssr.findAll();

    // for (SubjectState status : statuss) {
    // if (status.getSCode() != "0") {
    // StudyStateDto dto = new StudyStateDto();

    // dto.setUid(status.getUid());
    // dto.setName(ss.getSubject(status.getSCode()).getName());

    // statusList.add(dto);
    // } else {
    // statusList.add(createLastStudyStateDto(status.getUid()));
    // }
    // }

    // return statusList;
    // }

    // /**
    // * 最新の勉強ログを所得
    // *
    // * @param uid
    // * @return LastStudyLogDto
    // */
    // public StudyStateDto createLastStudyStateDto(String uid) {
    // StudyStateDto dto = new StudyStateDto();

    // dto.setUid(uid);
    // dto.setFlag(false);

    // StudyLog log = slr.findFirstByUid(uid); // 最新のLogを所得

    // if (log.getLid() != null) { // nullじゃないなら
    // dto.setName(ss.getSubject(log.getSCode()).getName());
    // dto.setUntil(log.getUntil());
    // dto.setSecond(log.getSecond());
    // }

    // return dto;
    // }

    /**
     * 累計勉強時間を計算
     *
     * @param uid
     * @return int
     */
    public int caluculateStudyTime(String uid) {
        List<StudyLog> logs = slr.findAllByUid(uid);

        int time = 0;

        for (StudyLog log : logs) {
            time += log.getSecond();
        }

        return time;
    }

    /**
     * 勉強状況を作成
     *
     * @param user
     * @return SubjectState
     */
    public SubjectState createSubjectState(User user) {
        // uidがかぶっているかどうかチェック
        if (ssr.existsById(user.getUid())) { // ??
            throw new SubjectStateValidationException(USER_ALREADY_EXISTS, "create the subjectState",
                    String.format("subjectState %s already exists", user.getUid()));
        }

        // ユーザをDBに登録し、登録したユーザの情報を戻り値として返す
        return ssr.save(new SubjectState(user.getUid(), "0"));
    }

    /**
     * 勉強をスタート
     *
     * @param dto
     * @return StudyEvent
     */
    public StudyEvent startStudy(StartStudyDto dto) {
        StudyEvent se = new StudyEvent();
        SubjectState sst = ssr.findById(dto.getUid())
                .orElseThrow(() -> new SubjectStateValidationException(SUBJECT_STATE_DOES_NOT_EXIST,
                        "get the subjectState", String.format("subjecstate %s does not exist", dto.getUid())));

        if (!sst.getSCode().equals("0") && !sst.getSCode().equals(dto.getSCode())) { // 現在ほかの科目を勉強中なら
            // その科目のendStudyEventを作成
            EndStudyForm form = new EndStudyForm(dto.getUid(), sst.getSCode());
            endStudy(createEndStudyDto(form));

            // ssr.save(new SubjectState(dto.getUid(), "0")); // 勉強状況を更新
        }

        se = ser.findFirstByUidAndTypeNotAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(dto.getUid(), "comment",
                new Date()); // 直前のcomment以外のstudyEventを所得

        if (se != null && se.getType().equals("start")) { // 直前のcomment以外StudyEventのtypeがendでなかった場合
            throw new EventValidationException(EVENT_START_END_NOT_MATCH, "create the studyEvent",
                    String.format("startStudyEvent does not match endStudyEvent %s just before", se.getEid()));
        }

        ssr.save(new SubjectState(dto.getUid(), dto.getSCode())); // 勉強状況を更新

        return ser.save(new StudyEvent(null, dto.getUid(), dto.getSCode(), new Date(), "start", null)); // StartStudyEventを記録
    }

    /**
     * 勉強をエンド
     *
     * @param dto
     * @return StudyEvent
     */
    public StudyEvent endStudy(EndStudyDto dto) {
        StudyEvent endse = new StudyEvent();
        StudyEvent startse = new StudyEvent();

        startse = ser.findFirstByUidAndTypeNotAndCreatedAtLessThanEqualOrderByCreatedAtDesc(dto.getUid(), "comment",
                new Date()); // 直前のcomment以外のstudyEventを所得

        if (startse.getType().equals("end")) { // 直前のcomment以外StudyEventのtypeがstartでなかった場合
            throw new EventValidationException(EVENT_START_END_NOT_MATCH, "create the studyEvent",
                    String.format("EndStudyEvent does not match StartStudyEvent %s just before", startse.getEid()));
        }

        if (!dto.getSCode().equals(startse.getSCode())) { // 直前のstartStudyEventの科目がendStudyEventとマッチしなかった場合
            throw new EventValidationException(EVENT_START_END_NOT_MATCH_SUBJECT, "create the studyEvent",
                    String.format("EndStudyEvent %s does not match StartStudyEvent %s just before", dto.getSCode(),
                            startse.getSCode()));
        }

        ssr.save(new SubjectState(dto.getUid(), "0")); // 勉強状況を更新

        if (dto.getUntil() == null) {
            endse = ser.save(new StudyEvent(null, dto.getUid(), dto.getSCode(), new Date(), "end", null)); // EndStudyEventを記録
        } else {
            endse = ser.save(new StudyEvent(null, dto.getUid(), dto.getSCode(), dto.getUntil(), "end", null)); // EndStudyEventを記録
        }

        int second = (int) (endse.getCreatedAt().getTime() - startse.getCreatedAt().getTime()) / 1000; // 勉強時間sを計算

        slr.save(new StudyLog(null, endse.getUid(), endse.getSCode(), startse.getCreatedAt(), endse.getCreatedAt(),
                second, startse.getEid(), endse.getEid())); // StudyLogを記録

        return endse;
    }

    /**
     * コメントをリマーク
     *
     * @param Commentdto
     * @return StudyEvent
     */
    public StudyEvent remarkComment(CommentDto dto) {
        return ser.save(new StudyEvent(null, dto.getUid(), null, new Date(), "comment", dto.getComment()));
    }

    /**
     * StartStudyFormからStartStudyDtoを作成
     *
     * @param StartStudyForm
     * @return StartStudyDto
     */
    public StartStudyDto createStartStudyDto(StartStudyForm form) {
        StartStudyDto dto = new StartStudyDto();

        dto.setUid(form.getUid());
        dto.setSCode(form.getSCode());
        // dto.setSince(new Date());

        return dto;
    }

    /**
     * EndStudyFormからEndStudyDtoを作成
     *
     * @param EndStudyForm
     * @return EndStudyDto
     */
    public EndStudyDto createEndStudyDto(EndStudyForm form) {
        EndStudyDto dto = new EndStudyDto();

        dto.setUid(form.getUid());
        dto.setSCode(form.getSCode());
        // dto.setUntil(new Date());

        return dto;
    }

    /**
     * CommentFormからCommentDtoを作成
     *
     * @param CommentForm
     * @return CommentDto
     */
    public CommentDto createCommentDto(CommentForm form) {
        CommentDto dto = new CommentDto();

        dto.setUid(form.getUid());
        dto.setComment(form.getComment());
        // dto.setDate(new Date());

        return dto;
    }

    /**
     * 日時を指定してログを追加
     *
     * @param CreateLogDto
     * @return StudyLog
     */
    @Transactional
    public StudyLog createStudyLog(CreateLogDto dto) {
        String uid = dto.getUid();
        String type = "comment";

        Date now = new Date();

        if (dto.getSince().after(now) || dto.getUntil().after(now)) { // 過去への記録でなければ
            throw new EventValidationException(EVENT_NOT_FAST, "create the StudyEvent",
                    String.format("StudyEvent can not record in the future"));
        }

        // startについて前後を所得
        StudyEvent sbefore = ser.findFirstByUidAndTypeNotAndCreatedAtLessThanEqualOrderByCreatedAtDesc(uid, type,
                dto.getSince());
        // StudyEvent safter =
        // ser.findFirstByUidAndTypeNotAndDateGreaterThanEqualOrderByDateAsc(uid, type,
        // dto.getSince());

        if (((sbefore != null) && sbefore.getType().equals("start"))) { // 時間関係がおかしかったら
            throw new EventValidationException(EVENT_START_END_NOT_MATCH, "create the startStudyEvent", String.format(
                    "StartStudyEvent can not record following StartStudyEvent %s just before", sbefore.getEid()));
        }

        StudyEvent startse = ser
                .save(new StudyEvent(null, dto.getUid(), dto.getSCode(), dto.getSince(), "start", null)); // startEventを記録

        // endについて前後を所得
        StudyEvent ebefore = ser.findFirstByUidAndTypeNotAndCreatedAtLessThanEqualOrderByCreatedAtDesc(uid, type,
                dto.getUntil());
        // StudyEvent eafter =
        // ser.findFirstByUidAndTypeNotAndDateGreaterThanEqualOrderByDateAsc(uid, type,
        // dto.getUntil());

        // if (((ebefore != null) && ebefore.getType().equals("end"))
        // || ((eafter != null) && eafter.getType().equals("end"))) { // 時間関係がおかしかったら
        // throw new EventValidationException(EVENT_START_END_NOT_MATCH, "create the
        // endStudyEvent", String
        // .format("EndStudyEvent can not record following EndStudyEvent %s just
        // before", eafter.getEid()));
        // }

        if (!(ebefore.getEid() == startse.getEid())) {
            throw new EventValidationException(EVENT_START_END_NOT_MATCH, "create the endStudyEvent", String
                    .format("EndStudyEvent can not record following StartStudyEvent %s just before", startse.getEid()));
        }

        StudyEvent endse = ser.save(new StudyEvent(null, dto.getUid(), dto.getSCode(), dto.getUntil(), "end", null)); // endEventを記録

        int second = (int) (endse.getCreatedAt().getTime() - startse.getCreatedAt().getTime()) / 1000; // 勉強時間sを計算

        return slr.save(new StudyLog(null, dto.getUid(), dto.getSCode(), startse.getCreatedAt(), endse.getCreatedAt(),
                second, startse.getEid(), endse.getEid())); // StudyLogを記録
    }

    /**
     * CreateLogFormからCreateLogDto
     *
     * @param reateLogForm
     * @return CreateLogDto
     */
    public CreateLogDto createCreateLogDto(CreateLogForm form) {
        CreateLogDto dto = new CreateLogDto();

        dto.setUid(form.getUid());
        dto.setSCode(ss.getSubjectByName(form.getName()).getSCode());

        try { // String型をDate型に変換してdtoに
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            Date since = sdFormat.parse(form.getSince());
            dto.setSince(since);

            Date until = sdFormat.parse(form.getUntil());
            dto.setUntil(until);
        } catch (ParseException e) {
            throw new OtherValidationException(PERSE_EXCEPTION, "create the StudyLog",
                    String.format("ParseException occured"));
        }

        return dto;
    }

    /**
     * EndEventに紐づいたログを削除し、対応するStartEventoを削除
     *
     * @param DeleteEventDto
     */
    @Transactional
    public void deleteStudyEvent(DeleteEventDto dto) {
        Long eid = dto.getEid(); // eidを保存

        if (!ser.existsById(eid)) { // 削除するEventが存在しないなら
            throw new EventValidationException(EVENT_DOES_NOT_EXIST, "delete the event",
                    String.format("evenr %lf does not exist", eid));
        }

        if (dto.getType().equals("end")) { // EventがEndだった場合
            StudyLog log;

            if ((log = slr.findByEndEvent(eid)) == null) { // 削除対象に紐づくログが存在しないなら
                throw new LogValidationException(LOG_DOES_NOT_EXIST, "delete the event",
                        String.format("evenr %lf does not exist", eid));
            }

            slr.deleteByEndEvent(eid); // ログを削除&所得

            ser.deleteById(log.getStartEvent()); // StartEventを削除
        }

        ser.deleteById(eid); // Eventを削除
    }

    /**
     * DeleteEventFormからDeleteEventDtoを作成
     *
     * @param DeleteEventForm
     * @return DeleteEventDto
     */
    public DeleteEventDto createDeleteEventDto(DeleteEventForm form) {
        DeleteEventDto dto = new DeleteEventDto();

        dto.setUid(form.getUid());
        dto.setEid(form.getEid());
        dto.setType(form.getType());

        return dto;
    }
}
