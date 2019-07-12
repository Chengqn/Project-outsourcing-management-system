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
    private String username;
    private String age;
   // private Integer  yearIncome;
    //private Integer monthIncome;
    private String role;

    private String password;
    private String email;

    private String salt;
    private String user_state;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

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
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", user_state='" + user_state + '\'' +
                '}';
    }
}
