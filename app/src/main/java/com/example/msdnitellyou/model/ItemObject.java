package com.example.msdnitellyou.model;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ItemObject {
    private boolean status;
    private List<Project> result;

    public List<Project> getResult() {
        return result;
    }

    public void setResult(List<Project> result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
