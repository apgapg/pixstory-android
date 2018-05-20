package com.jullae.data.db.model;

import com.jullae.ui.base.BaseResponseModel;

public class StoryResponseModel extends BaseResponseModel {
    public StoryResponseModel(boolean b, int errorCode, String errorMessage) {
        super(b, errorCode, errorMessage);
    }

    /*{"success":true,"errorcode":0,"message":"Data Ok.","
    story_id":128,"story_title":"Diaries","writer_id":8,"writer_name":"Ankit sharma","writer_penname":"ankitsharma",
    "writer_avatar":"https://jullaepictures.s3.amazonaws.com/avatars/4/image/original/WhatsApp_Image_2018-03-16_at_14.38.42.jpeg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ\u0026Expires=1524680480\u0026Signature=YIvvsN%2Fs%2Bg%2FGWxtk77zrb2uVBM8%3D",
    "story_text":"Its my college story ","like_count":0,"comment_count":0,"created_at":"2018-04-25T17:51:20.395Z"}*/

}
