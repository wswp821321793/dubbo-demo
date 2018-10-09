package com.geotmt.dubbo.auth.application;

import com.geotmt.dubbo.auth.code.ErrorCode;
import com.geotmt.dubbo.auth.request.RoleRequest;
import com.geotmt.dubbo.auth.response.PageGetRoleResponse;
import com.geotmt.dubbo.auth.service.UserService;
import com.geotmt.dubbo.auth.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Response add(@RequestBody RoleRequest request){
        if(request.getRoleName()==null||request.getRoleName().isEmpty()){
            return new Response(ErrorCode.ROLE_NAME_IS_EMPTY.getCode(),ErrorCode.ROLE_NAME_IS_EMPTY.getMsg());
        }
        return userService.insertRole(request);
    }

    @RequestMapping(value = "/{roleId}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public Response del(@PathVariable(name = "roleId") int roleId){
        return userService.deleteRole(roleId);
    }

    @RequestMapping(value = "/",method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Response put(@RequestBody RoleRequest request){
        if(request.getRoleName()==null||request.getRoleName().isEmpty()){
            return new Response(ErrorCode.ROLE_NAME_IS_EMPTY.getCode(),ErrorCode.ROLE_NAME_IS_EMPTY.getMsg());
        }
        return userService.updateRole(request);
    }

    @RequestMapping(value = "/{roleId}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Response get(@PathVariable(name = "roleId")int roleId){
        return userService.getRoleInfo(roleId);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public PageGetRoleResponse all(@RequestParam int page, @RequestParam int limit){
        return userService.getAllRole(page -1,limit);
    }
}
