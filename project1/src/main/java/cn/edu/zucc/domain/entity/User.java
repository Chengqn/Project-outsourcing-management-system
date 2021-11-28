package cn.edu.zucc.domain.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

//表明实体类的注释
@Entity
public class User {
    //JPA主键约束
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //主键生成策略 自增长
    private long id;

    @NotEmpty(message = "注解声明：用户名不能为空")
    private String name;
    private Integer age;
    // private Integer  yearIncome;
    //private Integer monthIncome;
    private String role;

    private String password;
    private String email;

    private String salt;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

//    public Integer getYearIncome() {
//        return yearIncome;
//    }
//
//    public void setYearIncome(Integer yearIncome) {
//        this.yearIncome = yearIncome;
//    }
//
//    public Integer getMonthIncome() {
//        return monthIncome;
//    }
//
//    public void setMonthIncome(Integer monthIncome) {
//        this.monthIncome = monthIncome;
//    }
}
