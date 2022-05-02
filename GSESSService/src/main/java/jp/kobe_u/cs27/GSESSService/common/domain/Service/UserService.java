package jp.kobe_u.cs27.GSESSService.common.domain.Service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import jp.kobe_u.cs27.GSESSService.common.configration.exception.UserValidationException;
import jp.kobe_u.cs27.GSESSService.common.domain.dto.UserDto;
import jp.kobe_u.cs27.GSESSService.common.domain.entity.User;
import jp.kobe_u.cs27.GSESSService.informations.entity.UserInfoStatus;
import jp.kobe_u.cs27.GSESSService.informations.repository.UserInfoStatusRepository;
import jp.kobe_u.cs27.GSESSService.common.domain.repositry.UserRepositry;
import jp.kobe_u.cs27.GSESSService.logs.domain.repositry.StudyEventRepositry;
import jp.kobe_u.cs27.GSESSService.logs.domain.repositry.StudyLogRepositry;
import jp.kobe_u.cs27.GSESSService.logs.domain.repositry.SubjectStateRepositry;

import static jp.kobe_u.cs27.GSESSService.common.configration.exception.ErrorCode.*;

import java.util.Date;
import java.util.Calendar;

import javax.transaction.Transactional;

/**
 * ユーザを扱うサービス
 */
@Service
@RequiredArgsConstructor
public class UserService {
    /** ユーザのリポジトリ */
    private final UserRepositry users;
    /** 勉強イベントログのリポジトリ */
    private final StudyEventRepositry ser;
    /** 勉強時間ログのリポジトリ */
    private final StudyLogRepositry slr;
    /** 勉強状況のリポジトリ */
    private final SubjectStateRepositry ssr;
    /** 情報サービスに使うユーザ設定等のリポジトリ by tomoro */
    private final UserInfoStatusRepository usr;

    /**
     * ユーザ新規登録
     *
     * @param user
     * @return User
     */
    public User createUser(User user) {
        if (users.existsById(user.getUid())) {// uidがかぶっているかどうかチェック
            throw new UserValidationException(USER_ALREADY_EXISTS, "create the user",
                    String.format("user %s already exists", user.getUid()));
        }

        // 情報サービスに使うユーザ固有の設定も登録 by tomoro

        // ユーザ登録日と被らない日を最初の編集日にする
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();

        usr.save(new UserInfoStatus(user.getUid(), 1, "orange1", date));

        return users.save(new User(user.getUid(), user.getNickname(), user.getEmail())); // ユーザをDBに登録し、登録したユーザの情報を戻り値として返す
    }

    /**
     * 指定したユーザが存在するか確認する
     *
     * @param uid
     * @return bool値
     */
    public boolean existsUser(String uid) {
        return users.existsById(uid);
    }

    /**
     * 既存ユーザを退会させる
     */
    @Transactional // 指定部にtranzaction処理(巻き戻し)ができる
    public void deleteUser(String uid) {
        if (!users.existsById(uid)) { // 退会させるUserが存在するかのチェック
            throw new UserValidationException(USER_DOES_NOT_EXIST, "delete the user",
                    String.format("user %s does not exist", uid));
        }

        ser.deleteByUid(uid); // 勉強イベントを削除する

        slr.deleteByUid(uid); // 勉強ログを削除する

        ssr.deleteById(uid); // 勉強状況を削除する

        users.deleteById(uid); // ユーザを削除する

        usr.deleteById(uid); // ユーザの設定を消去する

    }

    /**
     * ユーザを取得する
     *
     * @param uid
     * @return User
     */
    public UserDto getUser(String uid) {
        User user = users.findById(uid).orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST,
                "get the user", String.format("user %s does not exist", uid)));

        return UserDto.build(user);
    }

    /* ---------------以下 情報サービス用のメソッド by tomoro ------------------------ */

    /**
     * ユーザ設定を取得する
     *
     * @param uid
     * @return UserInfoStatus
     */
    public UserInfoStatus getUserInfoStatus(String uid) {
        UserInfoStatus userInfoStatus = usr.findById(uid)
                .orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST, "get the user",
                        String.format("user %s does not exist", uid)));

        return userInfoStatus;
    }

    /**
     * 編集者ランクをあげる 編集日が同じ日ならランクはそのまま その日初めての編集ならランクを1あげる
     * 
     * @param uid
     * @return boolean
     */
    public boolean upEditRank(UserInfoStatus userInfoStatus) {

        // Dateクラスのオブジェクトを作成
        Date today = new Date();
        Date editDay = userInfoStatus.getEditDay();
        // カレンダークラスのインスタンスを作り
        Calendar cal = Calendar.getInstance();
        // そこにDateのオブジェクトから日時をセット
        cal.setTime(today);
        // 時、分、秒、ミリ秒にそれぞれ0を設定し
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Dateのオブジェクトに戻す
        today = cal.getTime();

        // そこにDateのオブジェクトから日時をセット
        cal.setTime(editDay);
        // 時、分、秒、ミリ秒にそれぞれ0を設定し
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Dateのオブジェクトに戻す
        editDay = cal.getTime();

        if (editDay.equals(today)) {// その日既にランクが上がっている場合
            return false;
        } else {// その日初めての編集の場合
            userInfoStatus.setEditRank(userInfoStatus.getEditRank() + 1);
            return true;
        }
    }

    /**
     * ユーザ設定を更新する
     *
     * @param uid
     * @return UserInfoStatus
     */
    public UserInfoStatus updateUserInfoStatus(UserInfoStatus userInfoStatus) {

        // ユーザIDを変数に格納する
        final String uid = userInfoStatus.getUid();

        // ユーザが存在しない場合、例外を投げる
        if (!users.existsById(uid)) {
            throw new UserValidationException(USER_DOES_NOT_EXIST, "update the user",
                    String.format("user %s does not exist", uid));
        }

        // DB上のユーザ情報を更新し、新しいユーザ情報を戻り値として返す
        return usr.save(new UserInfoStatus(uid, userInfoStatus.getEditRank(), userInfoStatus.getTheme(),
                userInfoStatus.getEditDay()));
    }

}
