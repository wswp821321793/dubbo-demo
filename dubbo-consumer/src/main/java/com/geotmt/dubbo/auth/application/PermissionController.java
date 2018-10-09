package com.geotmt.dubbo.auth.application;

import com.geotmt.dubbo.auth.code.ErrorCode;
import com.geotmt.dubbo.auth.request.PermissionRequest;
import com.geotmt.dubbo.auth.response.PageGetPermissionResponse;
import com.geotmt.dubbo.auth.service.UserService;
import com.geotmt.dubbo.auth.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Response add(@RequestBody PermissionRequest request){
        if(request.getPermissionName()==null||request.getPermissionName().isEmpty()){
            return new Response(ErrorCode.PERMISSION_NAME_IS_EMPTY.getCode(),ErrorCode.PERMISSION_NAME_IS_EMPTY.getMsg());
        }
        if(request.getData()==null||request.getData().isEmpty()){
            return new Response(ErrorCode.PERMISSION_DATA_IS_EMPTY.getCode(),ErrorCode.PERMISSION_DATA_IS_EMPTY.getMsg());
        }
        return userService.insertPermission(request);
    }

    @RequestMapping(value = "/{permissionId}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public Response del(@PathVariable(name = "permissionId") int permissionId){
        return userService.deletePermission(permissionId);
    }

    @RequestMapping(value = "/",method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Response put(@RequestBody PermissionRequest request){
        if(request.getPermissionName()==null||request.getPermissionName().isEmpty()){
            return new Response(ErrorCode.PERMISSION_NAME_IS_EMPTY.getCode(),ErrorCode.PERMISSION_NAME_IS_EMPTY.getMsg());
        }
        if(request.getData()==null||request.getData().isEmpty()){
            return new Response(ErrorCode.PERMISSION_DATA_IS_EMPTY.getCode(),ErrorCode.PERMISSION_DATA_IS_EMPTY.getMsg());
        }
        return userService.updatePermission(request);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public PageGetPermissionResponse all(@RequestParam int page, @RequestParam int limit) {
        return userService.getAllPermission(page -1,limit);
    }
}
