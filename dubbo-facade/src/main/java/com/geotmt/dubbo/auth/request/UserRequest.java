package com.geotmt.dubbo.auth.request;

import com.geotmt.dubbo.auth.common.Request;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRequest extends Request implements Serializable {
    private int userId;

    private String name;

    private String username;

    private String password;

    private List<RoleRequest> roles;
}
