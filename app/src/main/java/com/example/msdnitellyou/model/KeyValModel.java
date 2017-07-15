package com.example.msdnitellyou.model;

/**
 * Created by Administrator on 2017/4/24.
 */

public class KeyValModel {
    private String key;
    private String val;

    public KeyValModel(String key,String val){
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
