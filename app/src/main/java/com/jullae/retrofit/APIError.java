package com.jullae.retrofit;

import static com.jullae.retrofit.ErrorUtils.DEFAULT_STATUS_CODE;
import static com.jullae.retrofit.ResponseResolver.UNEXPECTED_ERROR_OCCURRED;

/**
 * Developer: Saurabh Verma
 * Dated: 03-03-2017.
 */

public class APIError {
    private final int statusCode;
    private final String message;
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
        if (statusCode != 0) {
            return statusCode;

        } else
            return DEFAULT_STATUS_CODE;


    }


    /**
     * @return message of api error response
     */
    public String getMessage() {
        if (message == null) {
            return UNEXPECTED_ERROR_OCCURRED;
        } else {
            return message;
        }
    }
}
