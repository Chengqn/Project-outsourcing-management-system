package cn.edu.zucc.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Leavee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //申请人
    @NotEmpty(message = "注解声名：请假名字不能为空")
    private String applicant;
    //请假开始时间
    @NotNull(message = "注解声明：请假开始时间不能为空")
    private Date starttime;
    //请假结束时间
    @NotNull(message = "注解声明：请假结束时间不能为空")
    private Date finishtime;
    //合计天数
    @NotNull(message = "注解声明：请假天数不能为空")
    private Double totaldays;
    //审批人
    private String approver;
    //请假类型
    @NotEmpty(message = "注解声名:请假类型不能为空")
    private String offtype;
    //请假原因
    @NotEmpty(message = "注解声名:请假原因不能为空")
    private String offreason;
    //请假状态
    private String offstatus;
    //请假失败批语
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
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

    public Double getTotaldays() {
        return totaldays;
    }

    public void setTotaldays(Double totaldays) {
        this.totaldays = totaldays;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getOfftype() {
        return offtype;
    }

    public void setOfftype(String offtype) {
        this.offtype = offtype;
    }

    public String getOffreason() {
        return offreason;
    }

    public void setOffreason(String offreason) {
        this.offreason = offreason;
    }

    public String getOffstatus() {
        return offstatus;
    }

    public void setOffstatus(String offstatus) {
        this.offstatus = offstatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Leavee{" +
                "id=" + id +
                ", applicant='" + applicant + '\'' +
                ", starttime=" + starttime +
                ", finishtime=" + finishtime +
                ", totaldays=" + totaldays +
                ", approver='" + approver + '\'' +
                ", offtype='" + offtype + '\'' +
                ", offreason='" + offreason + '\'' +
                ", offstatus='" + offstatus + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
