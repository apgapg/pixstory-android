package com.jullae.ui.loginscreen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jullae.ApplicationClass;
import com.jullae.R;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.dialog.MyProgressDialog;

public class ForgotPasswordFragment extends BaseFragment implements ForgotPasswordView {


    private View view;
    private EditText nameField;
    private EditText pennameField;
    private int loginMode = 0;
    private EditText emailField;
    private ForgotPasswordPresentor mPresentor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        mPresentor = new ForgotPasswordPresentor(((ApplicationClass) getmContext().getApplication()).getmAppDataManager());
        emailField = view.findViewById(R.id.email_field);

        view.findViewById(R.id.text_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresentor.makeForgotPasswordReq(emailField.getText().toString().trim());
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
    }


    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        super.onDestroyView();

    }

    @Override
    public void showProgressBar() {
        MyProgressDialog.showProgressDialog(getmContext(), "Please Wait!");

    }

    @Override
    public void hideProgressBar() {
        MyProgressDialog.dismissProgressDialog();
    }

    @Override
    public void onForgotPasswordReqSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getmContext());
        builder.setMessage("We have sent reset password link to your mail. Please check.");
        builder.setCancelable(false);
        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                getmContext().onBackPressed();
            }
        });

        //creating alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onEmailValidationFail() {
        Toast.makeText(getmContext(), "Please check your email!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onForgotPasswordReqFail(String message) {
        Toast.makeText(getmContext(), message, Toast.LENGTH_SHORT).show();

    }
}
