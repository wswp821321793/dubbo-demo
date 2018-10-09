package com.geotmt.dubbo.auth.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {
    private int code;

    private String msg;

    private T data;

    public Response(){}

    public Response(int code){
        this.code = code;
    }

    public Response(int code,T data){
        this.code = code;
        this.data = data;
    }

    public Response(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
