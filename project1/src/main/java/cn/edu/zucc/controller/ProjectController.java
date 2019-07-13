package cn.edu.zucc.controller;


import cn.edu.zucc.annotation.Role;
import cn.edu.zucc.annotation.UserLoginToken;
import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.Mission;
import cn.edu.zucc.domain.entity.Project;
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
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/projects")   //与项目有关的映射
public class ProjectController {
    @Autowired
    ProjectService service;

    @Autowired
    UserService userService;

    @Autowired
    MissionService missionService;

    //发布项目
    @UserLoginToken
    @Role("puber")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public R<String> postProject(@RequestBody  Project project,HttpServletRequest httpServletRequest){
        //获取Token

        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);
        project.setPuber(user.getUsername());
        project.setPstatus("初始化");
        project.setContractor("");
        service.create(project);
        return R.success("发布项目成功");

    }

    //查看所有项目的信息
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public R<List<Project>> getProjects(){
        return R.data(service.getAllProjects());
    }

    //根据id获取项目的信息
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getProjects/{id}",method = RequestMethod.GET)
    public R<Project> getProjectById(@PathVariable Long id){
        return R.data(service.getProjectById(id));
    }

    //根据id删除项目信息
    @UserLoginToken
    @RequestMapping(value = "/deleteProjects/{id}",method = RequestMethod.DELETE)
    public R<String> deleteProjects(@PathVariable Long id){
        service.deleteProject(id);
        return R.success("删除项目信息成功");
    }

    //承包商承包项目
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public R<String> updateProjects(@PathVariable Long id,HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);

        Project project = service.getProjectById(id);
       // System.out.println("-------------");
        //System.out.println(project.getContractor());
        if(project.getContractor().equals("")||project.getContractor()==null){
           // System.out.println(project);
            project.setContractor(user.getName());
            project.setPstatus("已承包");
            service.create(project);
            return R.success("恭喜你，承包项目成功");
        }else{
            return R.fail("名花有主，请另觅良项");
        }

    }

    //验收项目  通过id获取并且修改状态为已验收
    @UserLoginToken
    @Role("puber")
    @RequestMapping(value = "/acceptance/{id}",method = RequestMethod.PUT)
    public R<String> acceptProject(@PathVariable Long id){
        Project project = service.getProjectById(id);
        //总的开发人数
       int  numofdev = service.getNumOfDev(project.getPname());
       //总的开发人天数
        int totalpersondays = service.getTotalPersonDays(project.getPname());
        project.setTotalpersondays(totalpersondays);
        //System.out.println(project);
        project.setPstatus("已验收");
        project.setNumofdev(numofdev);
        service.create(project);
        return R.success("恭喜你，验收项目成功");

    }

    //承包方查看已经承包的项目  模糊查询
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getProjectByContractor/{ContractorName}",method = RequestMethod.GET)
    public R<List<Project>> getProjectByName(@PathVariable String ContractorName){

        return R.data(service.getProjectByContractorName(ContractorName));
    }

    //承包商获取我的项目
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getProject",method = RequestMethod.GET)
    public R<List<Project>> getProject(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);

        return R.data(service.getProjectByContractorName(user.getUsername()));

    }

    //在我的项目模糊查询项目  在承包商的角度
    @UserLoginToken
    @RequestMapping(value = "/getProject/{ProjectName}",method = RequestMethod.GET)
    public R<List<Project>> getProjectByNameLike(@PathVariable String ProjectName,HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);

        return R.data(service.getMyProjectByNameLike(user.getUsername(),ProjectName));

    }

    //在我的项目下实现模糊查询 在发包方的角度
    @UserLoginToken
    @RequestMapping(value = "/getProjectByPuber/{PuberName}",method = RequestMethod.GET)
    public R<List<Project>> getProjectByPuberNameLike(@PathVariable String PuberName,HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);
        return R.data(service.getProjectByPuberName(user.getUsername(),PuberName));
    }

   //获取项目下的不同开发任务
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getProjectMissions/{projectName}",method = RequestMethod.GET)
    public R<List<Mission>> getProjectMission(@PathVariable String projectName){
        return R.data(service.getMissionByProjectName(projectName));
    }

    //实现对项目的模糊查询
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getProjectByNameLike/{pname}",method = RequestMethod.GET)
    public R<List<Project>> getProjectByNameLike(@PathVariable String pname ){
        return R.data(service.getProjectByNameLike(pname));
    }

    //发包方获取我发布的项目
    @UserLoginToken
    @Role("puber")
    @RequestMapping(value = "/getProjecyByPuber",method = RequestMethod.GET)
    public R<List<Project>> getProjectByPuber(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);

        return R.data(service.getProjectByPuber(user.getUsername()));
    }

    //发包方修改项目信息
    @UserLoginToken
    @Role("puber")
    @RequestMapping(value = "/updateProject/{id}",method = RequestMethod.PUT)
    public R<String> updateProjectBypuber(@RequestBody Project project,@PathVariable Long id){
        Project p = service.getProjectById(id);
        if(p.getPstatus().equals("初始化")){
            p.setPexplanation(project.getPexplanation());
            p.setPfinishtime(project.getPfinishtime());
            p.setPname(project.getPname());
            service.create(p);
            return R.success("修改项目信息成功");
        }else{
            return R.fail("项目已经承包，请联系承包商修改项目信息");
        }
    }

    //获取项目状态为待验收的项目
    @UserLoginToken
    @Role("puber")
    @RequestMapping(value = "/getProjectsByPstatus",method = RequestMethod.GET)
    public R<List<Project>> getProjectByPstatus(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);
        String Pstatus = "待验收";
        return R.data(service.getProjectByPstatus(user.getUsername(),Pstatus));
    }

    //在我的待验收项目下实现模糊查询 在发包方的角度
    @UserLoginToken
    @RequestMapping(value = "/getProjectByPuberAndPstatus/{PuberName}",method = RequestMethod.GET)
    public R<List<Project>> getProjectByPuberNameAndPstatus(@PathVariable String PuberName,HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);
        String Pstatus = "待验收";
        return R.data(service.getProjectByPuberNameAndPstatus(user.getUsername(),PuberName,Pstatus));
    }

    //获取已验收项目
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getAcceptedProject",method = RequestMethod.GET)
    public R<List<Project>> getAcceptedProject(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = userService.getUser(userId);
        String Pstatus = "已验收";
        return R.data(service.getAcceptProject(user.getUsername(),Pstatus));
    }

    //通过项目id查询其下面的任务
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getMissionByProjectId/{ProjectId}",method = RequestMethod.GET)
    public R<List<Mission>> getMissionByProjectId(@PathVariable Long ProjectId){
       // System.out.println(ProjectId);
        Project project = service.getProjectById(ProjectId);
       // System.out.println(project);
        String pname = project.getPname();
        //System.out.println(pname);
        return R.data(missionService.getMissionByProject(pname));
    }

    //通过id获取文件名
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getFileName/{ProjectId}",method = RequestMethod.GET)
    public R<String> getFileName(@PathVariable Long ProjectId){
        System.out.println(ProjectId);
        Project project  = service.getProjectById(ProjectId);
        System.out.println(project.getFilename());
        return R.data(project.getFilename());
    }


}
