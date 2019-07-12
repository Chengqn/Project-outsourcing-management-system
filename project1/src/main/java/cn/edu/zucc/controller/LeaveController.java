package cn.edu.zucc.controller;


import cn.edu.zucc.annotation.Role;
import cn.edu.zucc.annotation.UserLoginToken;
import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.Leavee;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import cn.edu.zucc.service.impl.LeaveServiceImpl;
import cn.edu.zucc.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "leaves")    //请假API都在leaves下
public class LeaveController {

    @Autowired
    LeaveServiceImpl service;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/",method = RequestMethod.POST)
    public R<String> postleave(@RequestBody  Leavee leavee){
        Leavee l = new Leavee();
        l.setApplicant(leavee.getApplicant());
        l.setStarttime(leavee.getStarttime());
        l.setFinishtime(leavee.getFinishtime());
        l.setOffreason(leavee.getOffreason());
        l.setOfftype(leavee.getOfftype());
        l.setTotaldays(leavee.getTotaldays());
        l.setOffstatus("发起");
       service.create(l);
        return R.success("发起请假成功");
    }

    /**
     * 通过id获取请假单并且审批 只有admin具有审批权限
      * @param id
     * @param
     * @return
     */
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public R<String> approveLeave(@PathVariable Long id, @RequestBody Leavee leavee,HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");
        //通过id查询请假单
        Leavee l = service.getLeaves(id);
        l.setApplicant(leavee.getApplicant());
        l.setOffstatus(leavee.getOffstatus());
        l.setComments(leavee.getComments());
        l.setStarttime(leavee.getStarttime());
        l.setFinishtime(leavee.getFinishtime());
        l.setOfftype(leavee.getOfftype());
        l.setTotaldays(leavee.getTotaldays());
        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        //改变审批字段
        User user = userService.getUser(userId);
        l.setApprover(user.getName());
        //保存请假单
        service.create(l);
        // System.out.println(leavee.getApplicant());
        return  R.success("审核请假单成功");

    }


    /**
     * 获取当前用户的所有请假列表
     * @param applicant
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/findbyname/{applicant}",method = RequestMethod.GET)
    public R<List<Leavee>> getLeavesList(@PathVariable String applicant){
            //Get方法只能在url传参
            return R.data(service.getLeaves(applicant));

    }

    /**
     * 管理员获取所有用户的所有请假单列表
     * @return
     */
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public R<List<Leavee>> getLeaveList(){
        return R.data( service.getAllLeavs());
    }

    /**
     * 管理员获取所有状态为请假的请假单列表
     * @return
     */
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/find/",method = RequestMethod.GET)
    public R<List<Leavee>> getLeaveByStatus(){
        List<Leavee> allLeaves = service.getAllLeaves("发起");
        return R.data(allLeaves);
    }


}
