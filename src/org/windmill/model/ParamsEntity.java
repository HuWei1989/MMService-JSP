package org.windmill.model;

import java.io.Serializable;

public class ParamsEntity<T> implements Serializable {
    String method;
    //语言：cn,en
    String language;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getApiversion() {
        return apiversion;
    }

    public void setApiversion(int apiversion) {
        this.apiversion = apiversion;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //api版本
    int apiversion;
    T data;
}
