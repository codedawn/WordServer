package com.codedawn.word.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author codedawn
 */

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ResponseUtil {

    private Boolean succeed;

    private Integer code;

    private String message;

    private Map<Object, Object> data=new HashMap<>();

    public static ResponseUtil succeed() {
        return new ResponseUtil().setCode(ResponseCode.SUCCEED.getCode()).setMessage(ResponseCode.SUCCEED.getMessage()).setSucceed(true);
    }

    public static ResponseUtil failure() {
        return new ResponseUtil().setCode(ResponseCode.FAILURE.getCode()).setMessage(ResponseCode.FAILURE.getMessage()).setSucceed(false);
    }

    public static ResponseUtil ensureSucceed(ResponseCode responseCode) {
        return new ResponseUtil().setCode(responseCode.getCode()).setMessage(responseCode.getMessage()).setSucceed(true);
    }

    public static ResponseUtil ensureFailure(ResponseCode responseCode) {
        return new ResponseUtil().setCode(responseCode.getCode()).setMessage(responseCode.getMessage()).setSucceed(false);
    }

    public ResponseUtil data(String k,Object v) {
        this.data.put(k, v);
        return this;
    }

    public String toJson() {
        return GsonUtil.responseUtilToJson(this);
    }

}
