package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConversationModel {
    /* {
         "conversations": [
         {
             "conversation_id": 4,
                 "user_id": 9,
                 "user_avatar": "https://jullaepictures.s3.amazonaws.com/avatars/5/image/thumb/cropped674604553.jpg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524630615&Signature=iyR0tt787o%2BvIJsso4wZ%2BVQ1tSU%3D",
                 "name": "Ayush p gupta",
                 "penname": "@coddu"
         },
         {
             "conversation_id": 3,
                 "user_id": 8,
                 "user_avatar": "https://jullaepictures.s3.amazonaws.com/avatars/4/image/thumb/WhatsApp_Image_2018-03-16_at_14.38.42.jpeg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524630615&Signature=Xm7sSbZKL0A9YL%2Fk4TlY00vd6u8%3D",
                 "name": "Ankit sharma",
                 "penname": "ankitsharma"
         }
     ]
     }*/
    @SerializedName("conversations")
    @Expose
    public List<Conversation> conversationList;

    public List<Conversation> getConversationList() {
        return conversationList;
    }

    public class Conversation {
        private String conversation_id;
        private String user_id;
        private String user_avatar;
        private String name;
        private String penname;

        public String getConversation_id() {
            return conversation_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public String getName() {
            return name;
        }

        public String getPenname() {
            return penname;
        }
    }
}
