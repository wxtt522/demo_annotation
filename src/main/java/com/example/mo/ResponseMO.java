package com.example.mo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: wulh
 * @Date: 2020/8/7 15:21
 */
@Data
public class ResponseMO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code = 0;
    private String msg;
    private T data;
    private String debugInfo;

    public ResponseMO() {
    }

    public boolean checkFailure() {
        boolean result = false;
        if (this.code == 1) {
            result = true;
        }

        return result;
    }

    public void setResponseCodeFailure() {
        this.code = 1;
    }

    public static <T> ResponseMO<T> response(int code, String msg, T data, String debugInfo) {
        ResponseMO<T> responseMO = new ResponseMO();
        responseMO.setCode(code);
        responseMO.setMsg(msg);
        responseMO.setData(data);
        responseMO.setDebugInfo(debugInfo);
        return responseMO;
    }

    public static <T> ResponseMO<T> response(int code, String msg, T data) {
        return response(code, msg, data, (String)null);
    }

    public static ResponseMO response(int code, String msg) {
        return response(code, msg, (Object)null, (String)null);
    }

    public static ResponseMO success(String msg) {
        return response(0, msg, (Object)null, (String)null);
    }

    public static ResponseMO success() {
        return response(0, (String)null, (Object)null, (String)null);
    }

    public static <T> ResponseMO<T> successWithData(T data) {
        return response(0, (String)null, data, (String)null);
    }

    public static ResponseMO error(int code, String message) {
        return response(code, message);
    }

    public static ResponseMO error(String message, String debugInfo) {
        return response(1, message, (Object)null, debugInfo);
    }

    public static ResponseMO error(String message) {
        return error(1, message);
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public String getDebugInfo() {
        return this.debugInfo;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }
}
