package com.jullae.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Response model class to get
 * the response as a common model.
 */

public class CommonResponse {
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("errorcode")
    @Expose
    public int errorcode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("relationship_id")
    @Expose
    public int relationshipId;
}
