package com.jullae.retrofit;


import com.jullae.constant.AppConstant;
import com.jullae.model.AllLikeModel;
import com.jullae.model.LikeResponseModel;
import com.jullae.model.PostRequestModel;
import com.jullae.ui.homefeed.HomeFeedModel;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Developer: Rahul Abrol.
 * Dated: 27-09-2016.
 * <p>
 * Interface used to hold all the api methods used to hit api
 * with required params.
 */
public interface ApiInterface {

    //    @FormUrlEncoded
//    @POST(AppConstant.ApiEndPoints.LOGIN)
//    Call<User> userLogin(@FieldMap Map<String, String> map);

    @GET(AppConstant.ApiEndPoints.API_FEEDS)
    Call<HomeFeedModel> getFeeds();

    @FormUrlEncoded
    @POST(AppConstant.ApiEndPoints.API_PICTURE_LIKE)
    Call<LikeResponseModel> feedLike(@Path("id") String id);

    @FormUrlEncoded
    @POST(AppConstant.ApiEndPoints.API_PICTURE_UNLIKE)
    Call<LikeResponseModel> feedUnLike(@Path("id") String id);

    @GET(AppConstant.ApiEndPoints.API_ALL_PICTURE_LIKE)
    Call<AllLikeModel> getAllPicLike(@Path("id") String id);

    @GET(AppConstant.ApiEndPoints.API_ALL_STORY_LIKE)
    Call<AllLikeModel> getAllStoryLike(@Path("id") String id);

    @POST(AppConstant.ApiEndPoints.API_FOLLOW)
    Call<CommonResponse> followUser(@Path("id") int clickedId);

    @POST(AppConstant.ApiEndPoints.API_REPORT)
    Call<CommonResponse> reportUser(@Path("id") int clickedId);

    @POST(AppConstant.ApiEndPoints.API_SAVE)
    Call<CommonResponse> savePost(@Path("id") int clickedId);

    @POST(AppConstant.ApiEndPoints.API_STORY_DRAFT)
    Call<CommonResponse> saveAsDraft(@Body PostRequestModel model);

    @POST(AppConstant.ApiEndPoints.API_STORY_PUBLISH)
    Call<CommonResponse> publish(@Body PostRequestModel model);

    @POST(AppConstant.ApiEndPoints.API_STORY_REPORT)
    Call<CommonResponse> reportStory(@Path("id") int clickedId);

    @POST(AppConstant.ApiEndPoints.API_STORY_SAVE)
    Call<CommonResponse> saveStory(@Path("id") int clickedId);

    @Multipart
    @POST(AppConstant.ApiEndPoints.API_PICTURE_UPLOAD)
    Call<CommonResponse> uploadPic(@PartMap HashMap<String, RequestBody> map);
}

