package jp.kobe_u.cs27.GSESSService.informations.Controller;

import com.petebevin.markdown.MarkdownProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.kobe_u.cs27.GSESSService.common.application.form.UidForm;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.UserService;
import jp.kobe_u.cs27.GSESSService.informations.dto.ContentsQueryResult;
import jp.kobe_u.cs27.GSESSService.informations.entity.Theme;
import jp.kobe_u.cs27.GSESSService.informations.entity.Topic;
import jp.kobe_u.cs27.GSESSService.informations.entity.UserInfoStatus;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsForm;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsQueryForm;
import jp.kobe_u.cs27.GSESSService.informations.form.SubjectForm;
import jp.kobe_u.cs27.GSESSService.informations.form.ThemeForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicQueryForm;
import jp.kobe_u.cs27.GSESSService.informations.service.ContentsService;
import jp.kobe_u.cs27.GSESSService.informations.service.ThemeService;
import jp.kobe_u.cs27.GSESSService.informations.service.TopicService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/information")
public class InformationPageController {

  @Autowired
  TopicService topicService;
  @Autowired
  ContentsService contentsService;
  @Autowired
  UserService userService;
  @Autowired
  ThemeService themeService;

  //教科選択画面
  @GetMapping("/{uid}")
  public String showSubjectsPage(@PathVariable String uid,Model model) {
    
  
    model.addAttribute(new UidForm());
    model.addAttribute("theme",userService.getUserInfoStatus(uid).getTheme());

    
    return "subjects";
  }

  //トピック一覧画面
  @GetMapping("/{uid}/{subject}/topics")
  public String showTopicsPage(@PathVariable String uid,@PathVariable String subject,Model model) {

    model.addAttribute(new TopicForm());

    // テンプレートにオブジェクトをセットする
    model.addAttribute("uid",uid);
    model.addAttribute("subject",subject);
    model.addAttribute("topics",
              topicService.queryFromSubject(new TopicQueryForm(subject)).getTopics());
    model.addAttribute("theme",userService.getUserInfoStatus(uid).getTheme());
    model.addAttribute("isQueried",false);
    return "topics";
  }

  //トピック検索結果を表示
  @GetMapping("/{uid}/{subject}/topics/serch")
  public String serchTopic(@PathVariable String uid,
                           @PathVariable String subject,
                           Model model,
                           @ModelAttribute @Validated  TopicForm form){
    // テンプレートにオブジェクトをセットする
    model.addAttribute("uid",uid);
    model.addAttribute("subject",subject);
    model.addAttribute("topicsFromTname",
      topicService.queryFromSubjectAndTnameContaining(subject,form.getTname()).getTopics());
     
    model.addAttribute("topicsFromTcomment",
    topicService.queryFromTcomment(subject,form.getTname()).getTopics());
    model.addAttribute("theme",userService.getUserInfoStatus(uid).getTheme());
    model.addAttribute("queryWord", form.getTname());
    model.addAttribute("isQueried",true);
    return "topics";
  }

  //トピック内容画面表示
  @GetMapping("/{uid}/{subject}/{tname}/contents")
  public String showContentsPage(@PathVariable String uid,
                                 @PathVariable String subject,
                                 @PathVariable String tname,
                                 Model model) {
  
    ContentsQueryResult cq = contentsService.query(new ContentsQueryForm(tname));

    //トピック名に紐づくトピック内容をモデルに入れる
    // マークダウン文章をHTMLに変換
    MarkdownProcessor processor = new MarkdownProcessor();
    String html = processor.markdown(cq.getContentsList().get(0).getComment());
    
    // テンプレートにオブジェクトをセットする
    model.addAttribute("uid",uid);
    model.addAttribute("subject",subject);
    model.addAttribute("tname",tname);
    //contentsにはトピック内容が入る
    model.addAttribute("contents",html);
    //使用しているテーマ(cssファイル名)をthemeに入れる
    model.addAttribute("theme",userService.getUserInfoStatus(uid).getTheme());
    
    return "contents";
  }

  //トピック内容編集ページを表示
  @GetMapping("/{uid}/{subject}/{tname}/contentsEdit")
  public String showContentsEditPage(@PathVariable String uid,
                                     @PathVariable String subject,
                                     @PathVariable String tname,
                                     Model model) {
    model.addAttribute(new ContentsForm());                                 
    // テンプレートにオブジェクトをセットする
    model.addAttribute("uid",uid);
    model.addAttribute("subject",subject);
    model.addAttribute("tname",tname);
    model.addAttribute("contents", contentsService.query(new ContentsQueryForm(tname)).getContentsList().get(0).getComment());
    model.addAttribute("theme",userService.getUserInfoStatus(uid).getTheme());
    return "contentsEdit";
  }

  //クレジットページを表示(教科一覧から)
  @GetMapping("/{uid}/credit")
  public String showCreditPageFromSubjectsPage(@PathVariable String uid,
                                     
                                     Model model) {
    UserInfoStatus userInfoStatus = userService.getUserInfoStatus(uid);
    model.addAttribute("themeList", themeService.queryThemes(userInfoStatus.getEditRank()));
                     
    model.addAttribute(new ThemeForm());
    // テンプレートにオブジェクトをセットする
    model.addAttribute("fromSubjects",true);
    model.addAttribute("uid",uid);
    model.addAttribute("theme",userService.getUserInfoStatus(uid).getTheme());
    model.addAttribute("editRank", userInfoStatus.getEditRank());
    return "credit";
  }

  //クレジットページを表示(コンテンツページから)
  @GetMapping("/{uid}/{subject}/{tname}/credit")
  public String showCreditPageFromContentsPage(@PathVariable String uid,
                                     @PathVariable String subject,
                                     @PathVariable String tname,
                                     Model model) {
    UserInfoStatus userInfoStatus = userService.getUserInfoStatus(uid);
    model.addAttribute("themeList", themeService.queryThemes(userInfoStatus.getEditRank()));
    System.out.println("aaaaaaaaaaaaaaaaaaa");
                     
    model.addAttribute(new ThemeForm());
    // テンプレートにオブジェクトをセットする
    model.addAttribute("fromContents",true);
    model.addAttribute("uid",uid);
    model.addAttribute("subject",subject);
    model.addAttribute("tname",tname);
    model.addAttribute("theme",userService.getUserInfoStatus(uid).getTheme());
    model.addAttribute("editRank", userInfoStatus.getEditRank());
    return "credit";
  }

  

}
