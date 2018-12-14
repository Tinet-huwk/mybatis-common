package com.venky.tinet.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 *
 * 封装error对象内容<br>
 * 相关类：{@link ErrorResponse}, {@link ErrorCode}。
 * @Auther: 侯法超
 * @Date: 2018/8/23
 */
public class ErrorResponse {

    @NotNull
    private String code;
    @NotNull
    private String message;
    @NotNull
    private ErrorCode errorCode;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.code = errorCode.toString();
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty()
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
