package com.example.tengxunmap.model.bean;

/**
 * Created by 亮亮 on 2017/8/25.
 */

public class BaseBean<T> {
    private int code;
    private T obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
