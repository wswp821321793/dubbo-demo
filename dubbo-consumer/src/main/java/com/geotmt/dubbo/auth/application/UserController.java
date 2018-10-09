package com.geotmt.dubbo.auth.application;

import com.geotmt.dubbo.auth.request.UserRequest;
import com.geotmt.dubbo.auth.response.UserResponse;
import com.geotmt.dubbo.auth.service.UserService;
import com.geotmt.dubbo.auth.code.ErrorCode;
import com.geotmt.dubbo.auth.common.Response;
import com.geotmt.dubbo.auth.request.LoginRequest;
import com.geotmt.dubbo.auth.response.PageGetUserResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Response login(@RequestBody LoginRequest request){
        //获取用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                request.getUsername(), DigestUtils.md5Hex(request.getPassword()));
        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e){
            log.error(String.format("登陆账号：%s 出现异常",request.getUsername()),e);
            return new Response(ErrorCode.USER_NAME_NOT_EXIST.getCode(),ErrorCode.USER_NAME_NOT_EXIST.getMsg());
        } catch (IncorrectCredentialsException e){
            log.error(String.format("登陆账号：%s 出现异常",request.getUsername()),e);
            return new Response(ErrorCode.USER_PASSWORD_ERROR.getCode(),ErrorCode.USER_PASSWORD_ERROR.getMsg());
        } catch (Exception e){
            log.error(String.format("登陆账号：%s 出现异常",request.getUsername()),e);
            return new Response(ErrorCode.USER_UNKNOWN_ERROR.getCode(),ErrorCode.USER_UNKNOWN_ERROR.getMsg());
        }
        return new Response(200);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Response logout(){
        //获取用户认证信息
        Subject subject = SecurityUtils.getSubject();
        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            subject.logout();
        } catch (Exception e){
            log.error(String.format("登陆账号：%s 出现异常",subject.getPrincipal()),e);
            return new Response(ErrorCode.USER_UNKNOWN_ERROR.getCode(),ErrorCode.USER_UNKNOWN_ERROR.getMsg());
        }
        return new Response(200);
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Response get(){
        UserResponse userResponse = null;
        try {
            userResponse = (UserResponse) SecurityUtils.getSubject().getPrincipal();
        } catch (UnknownAccountException e){
            log.error("登陆账号 出现异常",e);
            return new Response(ErrorCode.USER_NAME_NOT_EXIST.getCode(),ErrorCode.USER_NAME_NOT_EXIST.getMsg());
        } catch (IncorrectCredentialsException e){
            log.error("登陆账号 出现异常",e);
            return new Response(ErrorCode.USER_PASSWORD_ERROR.getCode(),ErrorCode.USER_PASSWORD_ERROR.getMsg());
        } catch (Exception e){
            log.error("登陆账号 出现异常",e);
            return new Response(ErrorCode.USER_UNKNOWN_ERROR.getCode(),ErrorCode.USER_UNKNOWN_ERROR.getMsg());
        }
        return new Response(200,userResponse);
    }

    @RequestMapping(value = "/",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Response add(@RequestBody UserRequest request){
        if(request.getUsername()==null||request.getUsername().isEmpty()){
            return new Response(ErrorCode.USER_NAME_IS_EMPTY.getCode(),ErrorCode.USER_NAME_IS_EMPTY.getMsg());
        }
        if(request.getPassword()==null||request.getPassword().isEmpty()){
            return new Response(ErrorCode.USER_PASSWORD_IS_EMPTY.getCode(),ErrorCode.USER_PASSWORD_IS_EMPTY.getMsg());
        }
        return userService.insertUser(request);
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public Response del(@PathVariable(name = "userId") int userId){
        return userService.deleteUser(userId);
    }

    @RequestMapping(value = "/",method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Response put(@RequestBody UserRequest request){
        if(request.getUsername()==null||request.getUsername().isEmpty()){
            return new Response(ErrorCode.USER_NAME_IS_EMPTY.getCode(),ErrorCode.USER_NAME_IS_EMPTY.getMsg());
        }
        if(request.getPassword()==null||request.getPassword().isEmpty()){
            return new Response(ErrorCode.USER_PASSWORD_IS_EMPTY.getCode(),ErrorCode.USER_PASSWORD_IS_EMPTY.getMsg());
        }
        return userService.updateUser(request);
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Response get(@PathVariable(name = "userId")int userId){
        return userService.getUserInfo(userId);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public PageGetUserResponse all(@RequestParam int page, @RequestParam int limit){
        return userService.getAllUser(page-1,limit);
    }
}
