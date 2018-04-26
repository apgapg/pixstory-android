package com.jullae.ui.loginscreen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.sql.DatabaseHelper;
import com.jullae.utils.InputValidation;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by macbunny on 03/12/17.
 * <p>
 * Forget password activity.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AppController.class.getSimpleName();

    private EditText textInputEditTextEmail;
    private TextView emailForgotErrorTextView;
    private Button resetPasswordButton;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * Initialize views.
     */
    private void initViews() {
        textInputEditTextEmail = findViewById(R.id.forgot_password_email);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        emailForgotErrorTextView = findViewById(R.id.email_forgot_error_text);
    }

    /**
     * Initialize view's listener.
     */
    private void initListeners() {
        resetPasswordButton.setOnClickListener(this);
    }

    /**
     * Initialize Helper classes.
     */
    private void initObjects() {
        inputValidation = new InputValidation(this);
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.reset_password_button:
                postDataToSQLite();
                break;
            default:
                break;
        }
    }

    /**
     * Server request after validation.
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail,
                emailForgotErrorTextView, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail,
                emailForgotErrorTextView, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            // Tag used to cancel the request
            String tagJsonObj = "json_obj_req";

            String url = "http://headers.jsontest.com/";

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            Response.Listener<JSONObject> successResponse = new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(final JSONObject response) {
                    Log.d(TAG, response.toString());
                    Log.v("RegisterActivity", "Test json response");
                    pDialog.hide();
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {

                @Override
                public void onErrorResponse(final VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.hide();
                }
            };
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null, successResponse, errorListener) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", textInputEditTextEmail.getText().toString().trim());
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tagJsonObj);
            Toast.makeText(this, getString(R.string.forgotPasswordEmailSuccessfullySent), Toast.LENGTH_LONG).show();
            emptyInputEditText();

        } else {
            Toast.makeText(this, getString(R.string.error_email_not_exists), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Set the layout empty.
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
    }
}
