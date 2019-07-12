package cn.edu.zucc.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

//开发任务实体类
@Entity
public class Mission {
    //主键生成策略
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    //任务名称
    private String mname;
    //开发内容
    private String mcontent;
    //设计文档
    //预计人天  1人天=8小时
    private Integer expectpersondays;
    //实际人天
    private Integer actualpersondays;
    //开发任务开始时间
    @NotNull(message = "注解声明：开发任务开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;
    //开发任务结束时间
    @NotNull(message = "注解声明：开发任务结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishtime;
    //开发任务状态   创建开发任务--初始化  分配给开发人员--已分配  开发完成--已完成
    private String mstatus;
    private String contractor;
    private  String project;
    private String puber;
    private  String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPuber() {
        return puber;
    }

    public void setPuber(String puber) {
        this.puber = puber;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public Integer getExpectpersondays() {
        return expectpersondays;
    }

    public void setExpectpersondays(Integer expectpersondays) {
        this.expectpersondays = expectpersondays;
    }

    public Integer getActualpersondays() {
        return actualpersondays;
    }

    public void setActualpersondays(Integer actualpersondays) {
        this.actualpersondays = actualpersondays;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", mname='" + mname + '\'' +
                ", mcontent='" + mcontent + '\'' +
                ", expectpersondays=" + expectpersondays +
                ", actualpersondays=" + actualpersondays +
                ", starttime=" + starttime +
                ", finishtime=" + finishtime +
                ", mstatus='" + mstatus + '\'' +
                ", contractor='" + contractor + '\'' +
                ", project='" + project + '\'' +
                ", puber='" + puber + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
