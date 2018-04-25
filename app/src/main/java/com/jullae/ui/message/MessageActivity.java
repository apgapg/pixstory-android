package com.jullae.ui.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.model.MessageModel;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements MessageView {

    private RecyclerView recyclerView;
    private MessagePresentor mPresentor;
    private String user_id;
    private MessageAdapter messageAdapter;
    private String currentUserId;
    private EditText addMessageField;
    private ImageView btn_add_message;
    private View progressBarMessage;
    private String user_name;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_message);

        if (getIntent() != null) {
            user_id = getIntent().getStringExtra("user_id");
            user_name = getIntent().getStringExtra("user_name");
        }
        mPresentor = new MessagePresentor(((AppController) getApplication()).getmAppDataManager());
        mPresentor.attachView(this);

        progressBar = findViewById(R.id.progress_bar);

        TextView title = findViewById(R.id.title);
        title.setText("Chat with " + user_name);
        currentUserId = mPresentor.getCurrentUserId();
        setUpRecyclerView();
        setupAddMessage();

        mPresentor.loadMessage(user_id);
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        messageAdapter = new MessageAdapter(this, currentUserId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    public void onMessageListFetchSuccess(List<MessageModel.Message> messageList) {
        messageAdapter.add(messageList);
    }


    @Override
    public void onMessageListFetchFail() {

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void setupAddMessage() {
        addMessageField = findViewById(R.id.field_add_message);
        btn_add_message = findViewById(R.id.btn_add_message);
        progressBarMessage = findViewById(R.id.progress_bar_message);
        btn_add_message.setEnabled(false);

        btn_add_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCommentUI(0);

                mPresentor.sendMessageReq(addMessageField.getText().toString().trim(), "", new ReqListener() {
                    @Override
                    public void onSuccess(MessageModel.Message messageModel) {
                        updateCommentUI(1);
                        messageAdapter.addMessage(messageModel);
                    }

                    @Override
                    public void onFail() {
                        updateCommentUI(2);
                        Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        addMessageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_add_message.setImageResource(R.drawable.ic_send_active_24dp);
                    btn_add_message.setEnabled(true);
                } else {
                    btn_add_message.setImageResource(R.drawable.ic_send_inactive_24dp);
                    btn_add_message.setEnabled(false);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateCommentUI(int mode) {
        if (mode == 0) {
            progressBarMessage.setVisibility(View.VISIBLE);
            addMessageField.setEnabled(false);
            btn_add_message.setVisibility(View.INVISIBLE);
        } else if (mode == 1) {
            progressBarMessage.setVisibility(View.INVISIBLE);

            addMessageField.setText("");
            addMessageField.setEnabled(true);
            btn_add_message.setVisibility(View.VISIBLE);
        } else {
            progressBarMessage.setVisibility(View.INVISIBLE);

            addMessageField.setEnabled(true);
            btn_add_message.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        mPresentor.detachView();
        super.onDestroy();
    }

    public interface ReqListener {
        void onSuccess(MessageModel.Message messageModel);

        void onFail();
    }

}
