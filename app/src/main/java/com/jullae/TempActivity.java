package com.jullae;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jullae.utils.MyActivityLifecycleCallbacks;

public class TempActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplication().registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        textView = findViewById(R.id.text);

        /*ImageView imageView = findViewById(R.id.image);
        GlideApp.with(this).load("https://jullaepictures.s3.amazonaws.com/pictures/36/image/medium/cropped1376548356.jpg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524842664&Signature=52QPRAHB%2FYgBdQe0LVif5peORIM%3D").into(imageView);
   */

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String message = "Ayush P Gupta";

                TempActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(message);
                    }
                });
            }
        });
        thread.start();


    }
}
