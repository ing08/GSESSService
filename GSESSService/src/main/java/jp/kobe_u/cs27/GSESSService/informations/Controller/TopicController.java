package jp.kobe_u.cs27.GSESSService.informations.Controller;

import java.util.Date;

import javax.swing.text.AbstractDocument.Content;

import com.petebevin.markdown.MarkdownProcessor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.kobe_u.cs27.GSESSService.common.domain.Service.UserService;
import jp.kobe_u.cs27.GSESSService.common.domain.entity.Subject;
import jp.kobe_u.cs27.GSESSService.informations.dto.ContentsQueryResult;
import jp.kobe_u.cs27.GSESSService.informations.entity.Topic;
import jp.kobe_u.cs27.GSESSService.informations.entity.UserInfoStatus;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsForm;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsQueryForm;
import jp.kobe_u.cs27.GSESSService.informations.form.SubjectForm;
import jp.kobe_u.cs27.GSESSService.informations.form.ThemeForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicAndSubjectForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicQueryForm;
import jp.kobe_u.cs27.GSESSService.informations.service.ContentsService;
import jp.kobe_u.cs27.GSESSService.informations.service.ThemeService;
import jp.kobe_u.cs27.GSESSService.informations.service.TopicService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TopicController {

  private final TopicService topicService;
  private final ContentsService contentsService; 
  private final UserService userService;
  private final ThemeService themeService;
    

 

  @PostMapping("/information/{uid}/{subject}/topics/create")
  public String createTopic(@PathVariable String uid,@PathVariable String subject,
    Model model,RedirectAttributes attributes,
    @ModelAttribute @Validated TopicForm form){

      topicService.createTopic(subject,uid,form);
      contentsService.createContents(form.getTname(),new ContentsForm());
      attributes.addFlashAttribute("subject",subject);
      attributes.addFlashAttribute("topics",
              topicService.queryFromSubject(new TopicQueryForm(subject)).getTopics());
      return "redirect:/information/{uid}/{subject}/topics/";
    }

   

 

 

  @PostMapping("/information/{uid}/{subject}/{tname}/contentsEdit/edit")
  public String editContents(@PathVariable String uid,
                             @PathVariable String subject,
                             @PathVariable String tname,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             ContentsForm form) {
    UserInfoStatus userInfoStatus = userService.getUserInfoStatus(uid);

    contentsService.updateContents(tname,form);
    topicService.updateTopicEditer(topicService.getTopicFromTname(tname), uid);
    
    if(userService.upEditRank(userInfoStatus) == true){
      //ランクが上がった場合
      redirectAttributes.addFlashAttribute("isUpEditRank", true);
      
    }
    userInfoStatus.setEditDay(new Date());
    userService.updateUserInfoStatus(userInfoStatus);

    redirectAttributes.addFlashAttribute("editRank",userInfoStatus.getEditRank());
    
    return "redirect:/information/{uid}/{subject}/{tname}/contents";
  }

  @PostMapping("/information/{uid}/{subject}/{tname}/changeTheme")
  public String changeThemeFromContentsPage(@PathVariable String uid,
                             
                             Model model,
                             RedirectAttributes redirectAttributes,
                             ThemeForm form) {
    UserInfoStatus userInfoStatus = userService.getUserInfoStatus(uid);
    userInfoStatus.setTheme(form.getThemeName());

    userService.updateUserInfoStatus(userInfoStatus);
    
    return "redirect:/information/{uid}/{subject}/{tname}/contents";
  }

  @PostMapping("/information/{uid}/changeTheme")
  public String changeThemeFromSubjectsPage(@PathVariable String uid,
            
                             Model model,
                             RedirectAttributes redirectAttributes,
                             ThemeForm form) {
    UserInfoStatus userInfoStatus = userService.getUserInfoStatus(uid);
    userInfoStatus.setTheme(form.getThemeName());

    userService.updateUserInfoStatus(userInfoStatus);
    
    return "redirect:/information/{uid}";
  }

  @GetMapping("/information/{uid}/{subject}/topics/delete")
  public String deleteTopic(@PathVariable String uid,
            
                             Model model,
                             RedirectAttributes redirectAttributes,
                             TopicForm form) {
    
    topicService.deleteTopic(form);
    
    return "redirect:/information/{uid}/{subject}/topics/";
  }

  


}
