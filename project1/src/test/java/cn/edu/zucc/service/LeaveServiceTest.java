package cn.edu.zucc.service;

import cn.edu.zucc.StudioDemoApplicationTests;
import cn.edu.zucc.domain.entity.Leavee;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * service层的单元测试
 */
public class LeaveServiceTest extends StudioDemoApplicationTests {
    @Autowired
    LeaveService service;

    @Test
    public void test001create() {
        Date d1 = new Date();
        Date d2 = new Date();
        Leavee leave1 = new Leavee();
        leave1.setApplicant("李四");
        leave1.setOfftype("病假");
        leave1.setOffreason("生病");
        leave1.setTotaldays(1.0);
        leave1.setStarttime(d1);
        leave1.setFinishtime(d2);
        System.out.println(leave1);
        service.create(leave1);
        Assert.assertTrue(leave1.getId() == 1L);
    }

    @Test
    public void test002getLeaves() {
        Date d1 = new Date();
        Date d2 = new Date();
        Leavee leave1 = new Leavee();
        leave1.setApplicant("张三");
        leave1.setOfftype("病假");
        leave1.setOffreason("生病");
        leave1.setTotaldays(1.0);
        leave1.setStarttime(d1);
        leave1.setFinishtime(d2);
        service.create(leave1);
        List<Leavee> leave2 = service.getLeaves("张三");
        Assert.assertEquals(leave1.getApplicant(), leave2.get(0).getApplicant());

    }

    @Test
    public void test003getLeaves() {
        Date d1 = new Date();
        Date d2 = new Date();
        Leavee leave1 = new Leavee();
        leave1.setApplicant("张三");
        leave1.setOfftype("病假");
        leave1.setOffreason("生病");
        leave1.setTotaldays(1.0);
        leave1.setStarttime(d1);
        leave1.setFinishtime(d2);
        service.create(leave1);
        Leavee leave2 = service.getLeaves(1L);
        Assert.assertEquals(leave1.getApplicant(), leave2.getApplicant());
    }

    @Test
    public void test004getAllLeaves() {
        Date d1 = new Date();
        Date d2 = new Date();
        Leavee leave1 = new Leavee();
        leave1.setApplicant("张三");
        leave1.setOfftype("病假");
        leave1.setOffreason("生病");
        leave1.setTotaldays(1.0);
        leave1.setStarttime(d1);
        leave1.setFinishtime(d2);
        service.create(leave1);
        List<Leavee> leave2 = service.getAllLeavs();
        Assert.assertEquals(leave1.getApplicant(), leave2.get(0).getApplicant());
    }

    @Test
    public void test006getAllLeave() {
        Date d1 = new Date();
        Date d2 = new Date();
        Leavee leave1 = new Leavee();
        leave1.setApplicant("张三");
        leave1.setOfftype("病假");
        leave1.setOffstatus("通过");
        leave1.setOffreason("生病");
        leave1.setTotaldays(1.0);
        leave1.setStarttime(d1);
        leave1.setFinishtime(d2);
        service.create(leave1);
        List<Leavee> leaves = service.getAllLeaves("通过");
        Assert.assertEquals(leave1.getApplicant(), leaves.get(0).getApplicant());
    }


}
