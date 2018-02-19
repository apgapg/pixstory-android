package com.jullae.retrofit;

import static com.jullae.retrofit.ErrorUtils.DEFAULT_STATUS_CODE;
import static com.jullae.retrofit.ResponseResolver.UNEXPECTED_ERROR_OCCURRED;

/**
 * Developer: Saurabh Verma
 * Dated: 03-03-2017.
 */

public class APIError {
    private final int statusCode;
    //    private final int code;
    private final String message;
//    private final String error;
//    private final String status;
//    private final Object data;

    /**
     * @param statusCode status code of api error response
     * @param message    message of api error response
     */
    public APIError(final int statusCode, final String message) {
        this.message = message;
        this.statusCode = statusCode;
    }


    /**
     * @return status code of api error response
     */
    public int getStatusCode() {
        if (statusCode == 0) {
//            if (code != 0) {
//                return code;
//            } else {
            return DEFAULT_STATUS_CODE;
//            }
        }
        return statusCode;
    }


    /**
     * @return message of api error response
     */
    public String getMessage() {
        if (message == null) {
//            if (error != null) {
//                return error;
//            } else {
            return UNEXPECTED_ERROR_OCCURRED;
//            }
        } else {
            return message;
        }
    }
}
