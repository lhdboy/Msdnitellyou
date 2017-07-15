package com.example.msdnitellyou;

/**
 * Created by Administrator on 2017/4/24.
 */

public class ServerException extends Exception {
    private int errorCode;
    private String errorMsg;

    public ServerException(int errorCode,String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
