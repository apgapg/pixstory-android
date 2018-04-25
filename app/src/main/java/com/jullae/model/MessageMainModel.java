package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageMainModel {
    /* {
    "messages": [
        {
            "message_id": 7,
            "message": "this is a random message test! hahahah",
            "sent_by_avatar": "https://jullaepictures.s3.amazonaws.com/avatars/4/image/thumb/WhatsApp_Image_2018-03-16_at_14.38.42.jpeg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524631873&Signature=%2BeYocEYx2ELcpIWomeHgLf1MWWk%3D",
            "sent_by_id": 8,
            "self": true,
            "sent_at": "2018-04-25T03:58:39.626Z"
        }
    ]
}*/
    @SerializedName("messages")
    @Expose
    public List<MessageModel> messageList;

    public List<MessageModel> getMessageList() {
        return messageList;
    }


}
