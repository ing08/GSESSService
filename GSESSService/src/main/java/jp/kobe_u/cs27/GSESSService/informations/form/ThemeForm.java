package jp.kobe_u.cs27.GSESSService.informations.form;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeForm {
    
    //  γγΌγε
    @NotBlank
    private String themeName;

}
