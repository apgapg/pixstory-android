package com.jullae.ui.base;

public class BaseResponseModel {
  /*  {
        "success":true,
            "errorcode":0,
            "message":"Data Ok.",
      }*/

    private boolean success;
    private int errorcode;
    private String message;

    public BaseResponseModel(boolean b, int errorCode, String errorMessage) {
        this.success = b;
        this.errorcode = errorCode;
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }


}
