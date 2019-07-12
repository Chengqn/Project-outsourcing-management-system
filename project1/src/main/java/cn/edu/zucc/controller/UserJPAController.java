package cn.edu.zucc.controller;

import cn.edu.zucc.annotation.PassToken;
import cn.edu.zucc.annotation.Role;
import cn.edu.zucc.annotation.UserLoginToken;
import cn.edu.zucc.common.HashKit;
import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value="/users")    //通过这里的配置使下面的映射都在/users下
public class UserJPAController {

    @Autowired
    UserService service;

    @Role("admin")
   @UserLoginToken
    @RequestMapping(value="/",method = RequestMethod.GET)
    public R<List<User>> getUserList(){
        //处理GET请求获得用户列表
        return R.data(service.getAllUsers());
    }

    @UserLoginToken
    @Role("admin")
    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    public R<List<User>> getDeveloperList(){
        String role = "user";
        return R.data(service.getDeveloperList(role));
    }

    @Role("admin")
    @RequestMapping(value = "/{id}/role",method = RequestMethod.PUT)
    public R<String> modifyUserRole(@PathVariable Long id,@RequestBody Long roleid){
        User user = service.getUser(id);
        if(roleid == 2){
            user.setRole("user");
            service.create(user);
        }else{
            user.setRole("admin");
            service.create(user);
        }
        return R.success("修改角色权限成功");
    }


    //根据id获取用户信息
    @Role("admin")
    @RequestMapping(value = "/getUserById/{id}",method = RequestMethod.GET)
    public R<User> getUserById(@PathVariable Long id){
        //User user = service.getUser(id);
        return R.data(service.getUser(id));

    }
    @RequestMapping(value="/",method = RequestMethod.POST)
    public R<String> postUser(@RequestBody Map<String,String> map){
        System.out.println(map);
        if((service.getUser(map.get("username")))!=null){
            return R.fail("用户名已存在");

        }else{
            String age = map.get("age");
            String username =map.get("username");
            String email = map.get("email");
            String password = map.get("password");
            User user = new User();
            user.setAge(age);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            System.out.println(user);
            //处理POST请求 用来创建User
            //除了@ModelAttribute绑定参数，还可以通过@RequestParam从页面传递参数
//        if(StringUtils.isEmpty(user.getName())){
//            throw new NullPointerException("用户名不能为空");
//        }
            user.setSalt(HashKit.generateSalt(8));
            user.setPassword(HashKit.md5(user.getPassword()+user.getSalt()));
            service.create(user);
            return R.success("添加用户成功");

        }

    }
    //登陆界面的注册逻辑
    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    public R<String> RegisUser(@RequestBody User user){
        System.out.println(user);
        if((service.getUser(user.getUsername()))!=null){
            return R.fail("用户名已存在");
        }else{
            user.setSalt(HashKit.generateSalt(8));
            user.setPassword(HashKit.md5(user.getPassword()+user.getSalt()));
            service.create(user);
            return R.success("注册用户成功");
        }
    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public R<User> getUser(@PathVariable Long id){
//        return R.data(service.findById(id).get());
//    }
    //更新用户信息
    @Role("admin")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public R<String> putUser(@PathVariable Long id, @RequestBody Map<String,String> map){
        //处理put请求用来更新User信息
        String username =map.get("username");
        String age = map.get("age");
        String email = map.get("email");
        System.out.println(age+"------"+email);
        //User user = new User();
        User user = service.getUser(id);
        user.setUsername(username);
        user.setAge(age);
        user.setEmail(email);
        service.create(user);
        return R.success("更新用户信息成功");
    }
    //删除用户信息
    @Role("admin")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public R<String> deleteUser(@PathVariable Long id){
        //处理delete请求， 用来删除User
        service.deleteById(id);
        return R.success("删除用户信息成功");
    }
    //更新用户状态
    @Role("admin")
    @RequestMapping(value = "/{id}/state/{type}",method = RequestMethod.PUT)
    public R<String> changeState(@PathVariable Long id ,@PathVariable String type){
        User user = service.getUser(id);
        user.setUser_state(type);
        service.create(user);
        return R.success("更新状态成功");


    }


    @RequestMapping(value = "/{name1}", method = RequestMethod.GET)
    public R<List<User>> getAllUsers(@PathVariable String name1){
        System.out.println(name1);
        return R.data(service.findByNameLike(name1));

    }

//    @PassToken
////    @RequestMapping(value="/login",method = RequestMethod.POST)
////    public R<String> loginUser(@RequestParam String password, @RequestParam String username){
////        System.out.println(username);
////        System.out.println(password);
////      //  User user = service.getUser(name, password);
////        User user =service.getUser(username);
////        if(user == null){
////            return R.fail("用户名不存在");
////        }
////       // return user==null?R.fail("用户不存在或者密码错误"):R.Loginsuccess(user);
////
////        return user.getPassword().equals(HashKit.md5(password+user.getSalt()))?R.Loginsuccess(user):R.fail("密码错误");
////    }
    @PassToken
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public R<String> loginUser(@RequestBody Map<String,String> map){
            System.out.println(map);


//        String[] namelist = content.split("%22");
//        String username =namelist[3];
//        String password = namelist[7];
          String  username = map.get("username");
          String password =map.get("password");
          System.out.println(username);
          System.out.println(password);
          User user = service.getUser(username);
        if(user == null){
            return R.fail("用户名不存在");
        }
        return user.getPassword().equals(HashKit.md5(password+user.getSalt()))?R.Loginsuccess(user):R.fail("密码错误");


    }

    @PassToken
    @RequestMapping(value = "/change",method = RequestMethod.PUT)
    public R<String> changePwd(@RequestParam String newpassword1,@RequestParam String newpassword2 ,@RequestParam String name){
        User user = service.getUser(name);
        if(user == null){
            return R.fail("用户名不存在");
        }
        if(!(newpassword1.equals(newpassword2))){
            return R.fail("前后密码不一致");
        }
        user.setPassword(HashKit.md5(newpassword1+user.getSalt()));
        service.update(user);
        return R.success("密码修改成功");

    }
    //获取用户权限函数
    @UserLoginToken
    @RequestMapping(value = "/getRoles",method = RequestMethod.GET)
    public R<String> getRoles(HttpServletRequest httpServletRequest){
        //获取Token
        String token = httpServletRequest.getHeader("Token");

        //通过token获取当前用户
        Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        long userId = jwt.getBody().get("id",Long.class);
        User user = service.getUser(userId);
        return  R.data(user.getRole());
    }

}