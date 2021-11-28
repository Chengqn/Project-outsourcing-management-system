package cn.edu.zucc.service;

import cn.edu.zucc.StudioDemoApplicationTests;
import cn.edu.zucc.domain.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;

public class UserServiceTest extends StudioDemoApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void test001create() {
        User user1 = new User();
        user1.setName("cqn");
        user1.setAge(20);

        User user2 = new User();
        user2.setName("Tom");
        user2.setAge(20);

        userService.create(user1);
        userService.create(user2);

        Assert.assertTrue(user1.getId() == 1L);
        Assert.assertTrue(user2.getId() == 2L);

    }

    @Test
    public void test002update() {

        User user1 = new User();
        user1.setName("TomCat");
        user1.setAge(20);
        user1.setId(1L);

        userService.update(user1);
        User user = userService.getUser(1L);
        Assert.assertThat(user.getName(), is(user1.getName()));

    }

    @Test
    public void test003deleteById() {
        Assert.assertThat(userService.getAllUsers().size(), is(2));
        userService.deleteById(1L);
        Assert.assertThat(userService.getAllUsers().size(), is(1));
    }

    @Test
    public void test004getAllUsers() {
        User user1 = new User();
        user1.setName("TomCat");
        user1.setAge(20);
        user1.setId(1L);

        List<User> getAllUsers = userService.getAllUsers();
        Assert.assertThat(getAllUsers.get(0).getName(), is(user1.getName()));


    }

    @Test
    public void test005() throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh");
        String str = "2011-5-31 23:40:21";
        Date s1 = sim.parse(str);
        String str2 = "2011-5-30 15:40:21";
        Date s2 = sim.parse(str2);
        Long a = s1.getTime();
        Long b = s2.getTime();
        Double days = 1.0 * (a - b) / (24 * 60 * 60 * 1000);
        System.out.println(days);
    }


}
