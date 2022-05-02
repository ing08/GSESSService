package jp.kobe_u.cs27.GSESSService;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring BootアプリケーションのWarデプロイを可能にするクラス 必要なBeanをサーバーに渡す
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /*
     * ソースとなる設定クラスを指定し、アプリケーションの設定を行う
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GsessServiceApplication.class);
    }
}
