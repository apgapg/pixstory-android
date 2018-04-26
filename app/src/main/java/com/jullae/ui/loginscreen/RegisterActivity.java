package com.jullae.ui.loginscreen;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.jullae.data.db.model.User;
import com.jullae.sql.DatabaseHelper;
import com.jullae.utils.InputValidation;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by macbunny on 19/11/17.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AppController.class
            .getSimpleName();

    private EditText textInputNameTextView;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private EditText textInputEditTextConfirmPassword;
    private TextView alreadyMember;

    private Button registerButton;

    private TextView nameErrorTextView;
    private TextView emailErrorTextView;
    private TextView passwordErrorTextView;
    private TextView confirmPasswordErrorTextView;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        nameErrorTextView = findViewById(R.id.invalidNameWarning);
        emailErrorTextView = findViewById(R.id.invalidEmailRegWarning);
        passwordErrorTextView = findViewById(R.id.invalidPasswordRegWarning);
        confirmPasswordErrorTextView = findViewById(R.id.invalidConfirmPasswordWarning);

        textInputNameTextView = findViewById(R.id.reg_name);
        textInputEditTextEmail = findViewById(R.id.reg_email);
        textInputEditTextPassword = findViewById(R.id.reg_password);
        textInputEditTextConfirmPassword = findViewById(R.id.reg_confirm_password);

        registerButton = findViewById(R.id.reg_register_button);

        alreadyMember = findViewById(R.id.alreadyMember);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        registerButton.setOnClickListener(this);
        alreadyMember.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(this);
        databaseHelper = new DatabaseHelper(this);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v view
     */
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.reg_register_button:
                postDataToSQLite();
                break;
            case R.id.alreadyMember:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            default:
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputNameTextView,
                nameErrorTextView, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail,
                emailErrorTextView, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, emailErrorTextView,
                passwordErrorTextView, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword,
                passwordErrorTextView, getString(R.string.empty_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                confirmPasswordErrorTextView, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputNameTextView.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Tag used to cancel the request
            String tagJsonObj = "json_obj_req";

            String url = "http://headers.jsontest.com/";

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {

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
                    url, null, successListener, errorListener) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", String.valueOf(user.getId()));
                    params.put("name", user.getName());
                    params.put("email", user.getEmail());
                    params.put("password", user.getPassword());

                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tagJsonObj);

            // Toast message to show success message that record saved successfully
            Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_LONG).show();
            emptyInputEditText();

            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);


        } else {
            // Toast message to show error message that record already exists
            Toast.makeText(this, getString(R.string.error_email_exists), Toast.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputNameTextView.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
