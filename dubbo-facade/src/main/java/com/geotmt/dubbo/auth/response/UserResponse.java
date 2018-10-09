package com.geotmt.dubbo.auth.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserResponse implements Serializable {

    private int id;

    private String name;

    private String username;

    private String password;

    private List<RoleResponse> roles;
}
