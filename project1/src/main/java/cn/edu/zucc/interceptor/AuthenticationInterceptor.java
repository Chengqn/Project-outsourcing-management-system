package cn.edu.zucc.interceptor;

import cn.edu.zucc.annotation.PassToken;
import cn.edu.zucc.annotation.Role;
import cn.edu.zucc.annotation.UserLoginToken;
import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("Token");
        //如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        //object 强制转化为HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) object;
        //方法处理器中调用要采用的方法
        Method method = handlerMethod.getMethod();

        //检查是否有passToken注释，有则跳过注释
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            //执行认证
            if (token == null) {
                throw new InvalidClientException("无Token，请重新登陆");
            }
            //获取token中的user id
            Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
            long userId = jwt.getBody().get("id", Long.class);

            User user = userService.getUser(userId);
            if (user == null) {
                throw new Exception("用户不存在 请重新登陆");
            }
            if (jwt.getBody().get("exp", Date.class).before(new Date())) {
                throw new Exception(("登陆超时 请重新登陆"));
            }
            if (method.isAnnotationPresent(Role.class)) {
                Role role = method.getAnnotation(Role.class);
                if (!(user.getRole().equals(role.value()))) {
                    throw new Exception("登陆权限不足 请获取管理员权限");
                }
                return true;
            }
            return true;
        }
        return true;

    }


}
