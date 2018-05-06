package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchPeopleMainModel {
    /*{
     "name_suggestions": [
         {
             "user_id": 9,
             "user_name": "Ayush p gupta",
             "user_penname": "coddu",
             "user_avatar": "https://jullaepictures.s3.amazonaws.com/avatars/15/image/thumb/cropped1907687164.jpg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1525522224&Signature=w423rnB1PD9RjrnJJISgiMx7Ffw%3D"
         },
         {
             "user_id": 12,
             "user_name": "Ayush p gupta",
             "user_penname": "temp_EBEz3OFVkm79GdjwWl8_vQ",
             "user_avatar": "https://s3.ap-southeast-1.amazonaws.com/jullaepictures/defaults/default_avatar.png"
         },
         {
             "user_id": 11,
             "user_name": "Ayush p gupta",
             "user_penname": "@coddu2",
             "user_avatar": "https://s3.ap-southeast-1.amazonaws.com/jullaepictures/defaults/default_avatar.png"
         }
     ]
 }*/
    @SerializedName("name_suggestions")
    @Expose
    private List<SearchPeopleModel> searchPeopleModelList;

    public List<SearchPeopleModel> getSearchPeopleModelList() {
        return searchPeopleModelList;
    }

    public class SearchPeopleModel {
        private String user_id;
        private String user_name;
        private String user_penname;
        private String user_avatar;

        public String getUser_id() {
            return user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_penname() {
            return user_penname;
        }

        public String getUser_avatar() {
            return user_avatar;
        }
    }
}
