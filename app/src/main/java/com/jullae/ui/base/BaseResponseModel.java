package com.jullae.ui.base;

public class BaseResponseModel {
  /*  {
        "success":true,
            "errorcode":0,
            "message":"Data Ok.",
      }*/

    private String success;
    private int errorcode;
    private String message;

    public BaseResponseModel(String b, int errorCode, String errorMessage) {
        this.success = b;
        this.errorcode = errorCode;
        this.message = errorMessage;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }

    public boolean isReqSuccess() {
        return success.equals("true");
    }


}
