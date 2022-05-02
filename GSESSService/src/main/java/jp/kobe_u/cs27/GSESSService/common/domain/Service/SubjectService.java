package jp.kobe_u.cs27.GSESSService.common.domain.Service;

import jp.kobe_u.cs27.GSESSService.common.configration.exception.SubjectValidationException;
import jp.kobe_u.cs27.GSESSService.common.domain.dto.SubjectDto;
import jp.kobe_u.cs27.GSESSService.common.domain.entity.Subject;
import jp.kobe_u.cs27.GSESSService.common.domain.repositry.*;
import lombok.RequiredArgsConstructor;

import static jp.kobe_u.cs27.GSESSService.common.configration.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

/**
 * 科目のサービス
 *
 * @author ing
 */
@Service
@RequiredArgsConstructor
public class SubjectService {
    /** 科目のリポジトリ */
    private final SubjectRepositry sr;

    /**
     * 科目を所得する
     *
     * @param sCode
     * @return Subject
     */
    public SubjectDto getSubject(String sCode) {
        Subject subject = sr.findById(sCode).orElseThrow(() -> new SubjectValidationException(SUBJECT_DOES_NOT_EXIST,
                "get the subject", String.format("subject %s does not exist", sCode)));

        return SubjectDto.build(subject);
    }

    public SubjectDto getSubjectByName(String name) {
        Subject subject;

        if ((subject = sr.findByName(name)) == null) { // 存在しない科目の科目名だった場合
            throw new SubjectValidationException(SUBJECT_DOES_NOT_EXIST, "get the subject",
                    String.format("subject name %s does not exist", name));
        }

        return SubjectDto.build(subject);
    }

    /**
     * 全ての科目を所得する
     *
     * @return AllSubjectDto
     */
    public List<SubjectDto> getAllSubject() {
        ArrayList<SubjectDto> list = new ArrayList<SubjectDto>();

        for (Subject subject : sr.findAll()) {
            list.add(SubjectDto.build(subject));
        }

        return list;
    }

    /**
     * 指定した科目が存在するか確認する
     *
     * @param sCode
     * @return bool値
     */
    public boolean existsSubject(String sCode) {
        return sr.existsById(sCode);
    }

    /**
     * 科目新規登録
     *
     * @param subject
     * @return Subject
     */
    public Subject createSubject(Subject subject) {
        if (sr.existsById(subject.getSCode())) {// sCodeがかぶっているかどうかチェック
            throw new SubjectValidationException(SUBJECT_ALREADY_EXISTS, "create the subject",
                    String.format("subject %s already exists", subject.getSCode()));
        }

        return sr.save(new Subject(subject.getSCode(), subject.getName(), subject.getDescription())); // 科目をDBに登録し、登録した科目の情報を戻り値として返す
    }

    /**
     * 既存科目を削除する
     */
    @Transactional // 指定部にtranzaction処理(巻き戻し)ができる
    public void deleteSubject(String sCode) {
        if (!sr.existsById(sCode)) { // 退会させる科目が存在するかのチェック
            throw new SubjectValidationException(SUBJECT_DOES_NOT_EXIST, "delete the subject",
                    String.format("subject %s does not exist", sCode));
        }

        sr.deleteById(sCode); // 科目を削除する
    }
}
