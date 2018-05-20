package com.jullae.utils;

import com.jullae.ui.base.BaseResponseModel;

public class ErrorResponseModel extends BaseResponseModel {

    public ErrorResponseModel(Boolean b, int errorCode, String errorMessage) {
        super(b, errorCode, errorMessage);
    }
}
