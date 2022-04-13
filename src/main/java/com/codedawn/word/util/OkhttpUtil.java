package com.codedawn.word.util;

import okhttp3.*;

import java.io.IOException;

public class OkhttpUtil {

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static Response request(String url) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }




}
