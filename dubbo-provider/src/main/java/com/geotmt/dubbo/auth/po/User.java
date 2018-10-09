package com.geotmt.dubbo.auth.po;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {

    private int userId;

    private String name;

    private String username;

    private String password;

    private List<Role> roles;
}
