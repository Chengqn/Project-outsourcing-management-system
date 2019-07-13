package cn.edu.zucc.controller;

import cn.edu.zucc.annotation.PassToken;
import cn.edu.zucc.annotation.Role;
import cn.edu.zucc.annotation.UserLoginToken;
import cn.edu.zucc.common.HashKit;
import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @Role("admin")
    @UserLoginToken
    @RequestMapping(value="/",method = RequestMethod.POST)
    public R<String> postUser(@Valid @ModelAttribute User user){
        //处理POST请求 用来创建User
        //除了@ModelAttribute绑定参数，还可以通过@RequestParam从页面传递参数
//        if(StringUtils.isEmpty(user.getName())){
//            throw new NullPointerException("用户名不能为空");
//        }
        user.setSalt(HashKit.generateSalt(8));
        user.setPassword(HashKit.md5(user.getPassword()+user.getSalt()));
        service.create(user);
        return R.success("success");
    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public R<User> getUser(@PathVariable Long id){
//        return R.data(service.findById(id).get());
//    }

    @Role("admin")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public R<String> putUser(@PathVariable Long id, @ModelAttribute User user){
        //处理put请求用来更新User信息
        user.setId(id);
        service.create(user);
        return R.success("success");
    }

    @Role("admin")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public R<String> deleteUser(@PathVariable Long id){
        //处理delete请求， 用来删除User
        service.deleteById(id);
        return R.success("success");
    }
//    @RequestMapping(value = "findByNameLike/{name1}", method = RequestMethod.GET)
//    public R<List<User>> getAllUsers(@PathVariable String name1){
//        return R.data(service.findByNameLike(name1));
//
//    }

    @PassToken
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public R<String> loginUser(@RequestParam String name, @RequestParam String password){
      //  User user = service.getUser(name, password);
        User user =service.getUser(name);
        if(user == null){
            return R.fail("用户名不存在");
        }
       // return user==null?R.fail("用户不存在或者密码错误"):R.Loginsuccess(user);
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

}