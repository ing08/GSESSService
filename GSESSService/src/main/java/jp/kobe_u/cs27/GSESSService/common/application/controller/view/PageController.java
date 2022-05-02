package jp.kobe_u.cs27.GSESSService.common.application.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 一番上位の層、index.htmlへ渡す
 *
 * @author ing & tomoro
 */
@Controller
public class PageController {
    @GetMapping("/")
    public String showLandingPage() {
        return "index";
    }

    /**
     * 設計者限定 管理画面
     *
     * @return controll.html
     */
    @GetMapping("/controll")
    public String showControllPage() {
        return "controll";
    }

    /**
     * ログイン後のホーム
     *
     * @return home.html
     */
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
