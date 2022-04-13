package com.codedawn.word.util;

/**
 * @author codedawn
 * @date 2021-06-27 17:04
 */

public enum ResponseCode {

    //
    SUCCEED(2000,"成功"),
    FAILURE(4000,"失败"),
    EXPIRE(4200,"登录过期"),
    ILLEGAL_Token(4300,"非法token和userId不匹配"),
    UPLOAD_SUCCEED(2100,"上传成功"),
    UPLOAD_FAILURE(4100,"上传失败"),

    DOWNLOAD_SUCCEED(2200,"同步成功"),
    DOWNLOAD_FAILURE(4200,"同步失败"),
    NO_RECORD_DOWNLOAD_FAILURE(4300,"没有同步记录"),
    ;
    private Integer code;
    private String message;


    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public ResponseCode setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseCode setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
