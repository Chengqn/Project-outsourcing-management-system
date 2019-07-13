package cn.edu.zucc.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

//项目实体类
@Entity
public class Project {
    //id 主键以及主键生成策略
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    //项目名称
    private String pname;
    //项目说明
    private String pexplanation;
    //期望完成时间
    @NotNull(message = "注解声明：项目期望完成时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pfinishtime;
    //项目需求
    //项目的状态  刚发布时--初始化  发布后--已发布  发起验收--待验收  验收通过--已验收
    private  String pstatus;
    //项目需要总的人天
    private Integer totalpersondays;
    //项目发布者
    private String puber;

    //总开发人数
    private  Integer numofdev;

    //项目承包商
    private  String contractor;

    //上传的需求文档名
    private  String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getNumofdev() {
        return numofdev;
    }

    public void setNumofdev(Integer numofdev) {
        this.numofdev = numofdev;
    }

    public String getPuber() {
        return puber;
    }

    public void setPuber(String puber) {
        this.puber = puber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPexplanation() {
        return pexplanation;
    }

    public void setPexplanation(String pexplanation) {
        this.pexplanation = pexplanation;
    }

    public Date getPfinishtime() {
        return pfinishtime;
    }

    public void setPfinishtime(Date pfinishtime) {
        this.pfinishtime = pfinishtime;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public Integer getTotalpersondays() {
        return totalpersondays;
    }

    public void setTotalpersondays(Integer totalpersondays) {
        this.totalpersondays = totalpersondays;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", pname='" + pname + '\'' +
                ", pexplanation='" + pexplanation + '\'' +
                ", pfinishtime=" + pfinishtime +
                ", pstatus='" + pstatus + '\'' +
                ", totalpersondays=" + totalpersondays +
                ", puber='" + puber + '\'' +
                ", numofdev=" + numofdev +
                ", contractor='" + contractor + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
