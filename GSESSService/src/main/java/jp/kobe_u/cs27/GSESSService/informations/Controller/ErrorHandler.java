package jp.kobe_u.cs27.GSESSService.informations.Controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public String NullPointerExceptionHandler() {
        System.out.println("Exeption occured");
        return "error";
    }
}
