package com.jullae.data.db.model;

public class PushNotificationModel extends NotificationModel {
    /*{
       "notifications": [
           {
               "actor_id": 9,
               "actor_avatar": "https://jullaepictures.s3.amazonaws.com/avatars/5/image/thumb/cropped674604553.jpg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524909459&Signature=VeS0ndzB9R%2FDkK384K9XF6QL3gs%3D",
               "actor_name": "Ayush p gupta",
               "actor_penname": "@coddu",
               "text": "started following you",
               "story_id": null,
               "picture_id": null,
               "picture_url_thumb": null,
               "notification_type_id": 1
           }
       ]
   }*/

    private String title;


    public String getTitle() {
        return title;
    }


}
