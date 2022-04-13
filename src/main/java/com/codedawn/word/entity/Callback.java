package com.codedawn.word.entity;

public class Callback {

    private long error;
    private String error_description;
    private String client_id;
    private String openid;

    public long getError() {
        return error;
    }

    public Callback setError(long error) {
        this.error = error;
        return this;
    }

    public String getError_description() {
        return error_description;
    }

    public Callback setError_description(String error_description) {
        this.error_description = error_description;
        return this;
    }

    public String getClient_id() {
        return client_id;
    }

    public Callback setClient_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public String getOpenid() {
        return openid;
    }

    public Callback setOpenid(String openid) {
        this.openid = openid;
        return this;
    }
}
