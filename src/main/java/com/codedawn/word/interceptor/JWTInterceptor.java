package com.codedawn.word.interceptor;

import com.codedawn.word.util.GsonUtil;
import com.codedawn.word.util.OkhttpUtil;
import com.codedawn.word.util.ResponseCode;
import com.codedawn.word.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author codedawn
 * @date 2021-09-08 9:12
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {


    private String QQUrl = "https://graph.qq.com/oauth2.0/me?access_token=";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userId = request.getHeader("userId");
        if (token==null||userId==null|| "".equals(token)||"".equals(userId)) {
            errorResult(response,ResponseCode.ILLEGAL_Token);
            return false;
        }
        Response resp = OkhttpUtil.request(QQUrl+token);

        assert resp.body() != null;
        String json = resp.body().string();
        int i = json.indexOf('{');
        int j = json.lastIndexOf('}');
        String substring = json.substring(i, j+1);
        com.codedawn.word.entity.Callback callback = GsonUtil.parseJsonToCallback(substring);

        if (callback.getOpenid() != null&&userId.equals(callback.getOpenid())) {
            return true;
        }else {
            //access token和userId不对应
            errorResult(response,ResponseCode.ILLEGAL_Token);
            return false;
        }
    }

    public void errorResult(HttpServletResponse response, ResponseCode responseCode) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(ResponseUtil.ensureFailure(responseCode)));

    }


}
