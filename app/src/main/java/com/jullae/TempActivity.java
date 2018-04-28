package com.jullae;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        ImageView imageView = findViewById(R.id.image);
        GlideApp.with(this).load("https://jullaepictures.s3.amazonaws.com/pictures/36/image/medium/cropped1376548356.jpg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524842664&Signature=52QPRAHB%2FYgBdQe0LVif5peORIM%3D").into(imageView);
    }
}
