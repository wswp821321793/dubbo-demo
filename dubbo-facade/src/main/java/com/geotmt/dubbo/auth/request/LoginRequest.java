package com.geotmt.dubbo.auth.request;

import com.geotmt.dubbo.auth.common.Request;

import java.io.Serializable;

public class LoginRequest extends Request implements Serializable {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}