package com.example.msdnitellyou.model;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class LangItemModel {
    private boolean status;
    private List<LangResult> result;

    public List<LangResult> getResult() {
        return result;
    }

    public void setResult(List<LangResult> result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
