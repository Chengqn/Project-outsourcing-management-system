package cn.edu.zucc.controller;


import cn.edu.zucc.annotation.Role;
import cn.edu.zucc.annotation.UserLoginToken;
import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.Mission;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.MissionService;
import cn.edu.zucc.service.ProjectService;
import cn.edu.zucc.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/mission")   //与开发任务有关的映射
public class MissionController {
        @Autowired
        MissionService service;

        @Autowired
        UserService userService;

        @Autowired
        ProjectService projectService;

    //创建开发任务
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public R<String> postMission(@RequestBody Mission mission,HttpServletRequest httpServletRequest){
        // System.out.println(mission);
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);

        String puber = projectService.selectPuberByProject(mission.getProject());
        mission.setContractor(user.getUsername());
        mission.setMstatus("初始化");
        mission.setPuber(puber);
        service.create(mission);
        return R.success("创建开发任务成功");
    }

    //查看所有开发任务信息
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public R<List<Mission>> getMissions(){
        return R.data(service.getAllMissions());

    }

    //通过id获取某个开发任务信息
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getMission/{id}",method = RequestMethod.GET)
    public R<Mission> getMissionById(@PathVariable  Long id){
        return R.data(service.getMissionById(id));
    }


    //通过id删除某个开发任务
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/deleteMission/{id}",method = RequestMethod.DELETE)
    public R<String> deleteMissionById(@PathVariable Long id){
        service.deleteMission(id);
        return R.success("删除开发任务成功");
    }

    //承包商查看自己的开发任务详情
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getMission",method = RequestMethod.GET)
    public R<List<Mission>> getMission(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);

        return R.data(service.getMissionByName(user.getUsername()));


    }

    //模糊查询开发任务
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getMissionByProject/{ProjectName}",method = RequestMethod.GET)
    public R<List<Mission>> getMissionByMissionName(@PathVariable String ProjectName){
        return R.data(service.getMissionByNameLike(ProjectName));
    }

    //发布方查看关于自己项目的任务开发详情
    @UserLoginToken
    @Role("puber")
    @RequestMapping(value = "/getMissionByPuber",method = RequestMethod.GET)
    public R<List<Mission>> getMissionByPuber(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);
        return R.data(service.getMissionByPuber(user.getUsername()));
    }

    //分配开发任务
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/assignMission",method = RequestMethod.POST)
    public R<String> AssignMission(@RequestBody  Mission mission){

        mission.setMstatus("已分配");
        service.create(mission);
        return R.success("分配开发任务成功");
    }

    //开发获取自己的任务
    @UserLoginToken
    @Role("user")
    @RequestMapping(value = "/getMyMission",method = RequestMethod.GET)
    public R<List<Mission>> getMyMission(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);

        return R.data(service.getMyMission(userId+""));
    }

    //开发人员完成任务  记录完成的时间以及修改任务状态
    @UserLoginToken
    @Role("user")
    @RequestMapping(value = "competeMisision/{missionId}",method = RequestMethod.POST)
    public R<String> competeMission(@PathVariable Long missionId){
        Mission m = service.getMissionById(missionId);

        if(!m.getMstatus().equals("已完成")){
            m.setMstatus("已完成");

            //获取当前的系统时间
            Date finishtime = new Date();
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            m.setFinishtime(finishtime);
            System.out.println(finishtime);
            Date starttime = m.getStarttime();
            //计算实际的人天数
            int actualpersondays = 0;
            Calendar cal=Calendar.getInstance();
            while(starttime.compareTo(finishtime)!=1)
            {
                cal.setTime(starttime);
                int week=cal.get(Calendar.DAY_OF_WEEK)-1; //Calendar.DAY_OF_MONTH表示这天是几号,Calendar.DAY_OF_WEEK表示是星期几
                if(week==0||week==6)
                {
                    cal.add(Calendar.DAY_OF_MONTH,+1);
                    starttime=cal.getTime();
                    continue;
                }
                actualpersondays+=1;
                cal.add(Calendar.DAY_OF_MONTH,+1);
                starttime=cal.getTime();
            }
            m.setActualpersondays(actualpersondays);
            service.create(m);
            return R.success("恭喜您，完成任务成功");
        }else{
            return  R.fail("此任务已完成");
        }

    }
}
