package com.codedawn.word.util;

import com.codedawn.word.entity.Callback;
import com.google.gson.Gson;

public class GsonUtil {
    private static Gson gson = new Gson();

    public static Callback parseJsonToCallback(String json) {
        return gson.fromJson(json, Callback.class);
    }

    public static String responseUtilToJson(ResponseUtil responseUtil) {
        return gson.toJson(responseUtil);
    }
}
