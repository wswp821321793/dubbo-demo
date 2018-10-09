package com.geotmt.dubbo.auth.code;

public enum ErrorCode {
    USER_NAME_NOT_EXIST(300001,"账号不存在"),
    USER_PASSWORD_ERROR(300002,"密码错误"),
    USER_NOT_LOGIN(300003,"用户未登录，请登录后再试"),
    USER_NAME_IS_EMPTY(300004,"用户名为空,请重新填写"),
    USER_PASSWORD_IS_EMPTY(300005,"用户密码为空，请重新填写"),
    USER_HAD_ROLE(300006,"有用户拥有此角色，不能删除"),

    ROLE_NAME_IS_EMPTY(300101,"角色名为空,请重新填写"),
    ROLE_HAD_PERMISSION(300102,"有角色拥有此权限，不能删除"),

    PERMISSION_NAME_IS_EMPTY(300201,"权限名为空,请重新填写"),
    PERMISSION_DATA_IS_EMPTY(300202,"权限规则为空，请重新填写"),

    USER_UNKNOWN_ERROR(300999,"未知错误");
    private int code;
    private String msg;
    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
