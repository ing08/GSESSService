package jp.kobe_u.cs27.GSESSService.informations.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    
    //トピックid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    //トピック名
    private String tname;

    //教科名
    private String subject;

    //作成者
    private String uid;

    //作成日
    private Date createdDay;

}
