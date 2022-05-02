package jp.kobe_u.cs27.GSESSService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import jp.kobe_u.cs27.GSESSService.common.application.form.SubjectForm;
import jp.kobe_u.cs27.GSESSService.common.application.form.UserForm;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.SubjectService;
import jp.kobe_u.cs27.GSESSService.common.domain.Service.UserService;
import jp.kobe_u.cs27.GSESSService.informations.entity.Topic;
import jp.kobe_u.cs27.GSESSService.informations.form.ContentsForm;
import jp.kobe_u.cs27.GSESSService.informations.form.TopicForm;
import jp.kobe_u.cs27.GSESSService.informations.service.ContentsService;
import jp.kobe_u.cs27.GSESSService.informations.service.ThemeService;
import jp.kobe_u.cs27.GSESSService.informations.service.TopicService;

@SpringBootApplication
public class GsessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsessServiceApplication.class, args);
		// ConfigurableApplicationContext context =
		// SpringApplication.run(GsessServiceApplication.class, args);
		// UserService us = context.getBean(UserService.class);
		// UserForm uf = new UserForm("ing", "長谷", "ing.cs24@gmail.com");

		// us.createUser(uf.toEntity());
		/*
		 * ConfigurableApplicationContext context =
		 * SpringApplication.run(GsessServiceApplication.class, args); TopicService ts =
		 * context.getBean(TopicService.class); TopicForm tf = new TopicForm("線形代数",
		 * "一次独立");
		 * 
		 * if (ts.createTopic(tf) != null) System.out.println("create topic sucsess!");
		 */
		/*
		 * ConfigurableApplicationContext context =
		 * SpringApplication.run(GsessServiceApplication.class, args); ContentsService
		 * cs = context.getBean(ContentsService.class); ContentsForm cf = new
		 * ContentsForm("一次独立", "線形代数","aaaa\nbbbb\ncccc");
		 * 
		 * if (cs.createContents(cf) != null)
		 * System.out.println("create contents sucsess!");
		 */

		// テーマ追加用
		// ConfigurableApplicationContext context =
		// SpringApplication.run(GsessServiceApplication.class, args);
		// ThemeService ts = context.getBean(ThemeService.class);
		// ts.updateTheme();

		// ConfigurableApplicationContext context =
		// SpringApplication.run(GsessServiceApplication.class, args);
		// SubjectService ss = context.getBean(SubjectService.class);
		// SubjectForm sf = new SubjectForm("TEST", "テスト", "テストです");

		// ss.createSubject(sf.toEntity());

	}

}
