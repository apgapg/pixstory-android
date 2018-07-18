package com.jullae.ui.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.LikesModel;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.utils.NetworkUtils;

public class LikesActivity extends AppCompatActivity {

    private String picture_id;
    private int liketype;
    private String TAG = LikesActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_likes);


        picture_id = getIntent().getStringExtra("pictureid");
        liketype = getIntent().getIntExtra("liketype", -1);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.close1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final LikeAdapter likeAdapter = new LikeAdapter(this, 0);
        recyclerView.setAdapter(likeAdapter);
        AppDataManager.getInstance().getmApiHelper().getLikesList(picture_id, liketype).getAsObject(LikesModel.class, new ParsedRequestListener<LikesModel>() {

            @Override
            public void onResponse(LikesModel likesModel) {
                NetworkUtils.parseResponse(TAG, likesModel);
                likeAdapter.add(likesModel.getLikes());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);

            }
        });

    }
}
