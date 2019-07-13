package cn.edu.zucc.controller;


import cn.edu.zucc.annotation.Role;
import cn.edu.zucc.annotation.UserLoginToken;
import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.Leavee;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import cn.edu.zucc.service.impl.LeaveServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "leaves")    //请假API都在leaves下
public class LeaveController {

    @Autowired
    LeaveServiceImpl service;

    @Autowired
    UserService userService;

    /**
     * 发起请假
     * @param leave
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public R<String> postleave(@Valid @ModelAttribute Leavee leave){
        //简单sql实现 似乎mysql的字段不能有大写？
        leave.setOffstatus("发起");
        service.create(leave);
        return R.success("发起请假成功");
    }

    /**
     * 通过id获取请假单并且审批 只有admin具有审批权限
      * @param id
     * @param comments
     * @return
     */
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public R<String> approveLeave(@PathVariable Long id, @RequestParam String comments, @RequestParam String offstatus, HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");
        //通过id查询请假单
        Leavee leave = service.getLeaves(id);
         leave.setOffstatus(offstatus);
        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        //改变审批字段
        User user = userService.getUser(userId);
        leave.setApprover(user.getName());

         if(offstatus.equals("通过")) {
             service.create(leave);
             return R.success("审批请假单成功");
         }else if(offstatus.equals("不通过")){
             leave.setComments(comments);
             service.create(leave);
             return R.success("审批请假单成功");
         }
         return R.fail("请审批请假单");

    }

    /**
     * 获取当前用户的所有请假列表
     * @param applicant
     * @return
     */
    @RequestMapping(value = "/{applicant}",method = RequestMethod.GET)
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
