package com.jullae.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by macbunny on 17/11/17.
 */
public class InputValidation {

    private Context context;

    /**
     * constructor
     *
     * @param context calling class reference.
     */
    public InputValidation(final Context context) {
        this.context = context;
    }

    /**
     * method to check InputEditText filled .
     *
     * @param textInputEditText edit text
     * @param errorTextView     tv error
     * @param message           error msg
     * @return true is entered
     */
    public boolean isInputEditTextFilled(final EditText textInputEditText, final TextView errorTextView, final String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()) {
            errorTextView.setText(message);
            hideKeyboardFrom(errorTextView);
            return false;
        } else {
            errorTextView.setText(null);
        }

        return true;
    }

    /**
     * method to check InputEditText has valid email .
     *
     * @param textInputEditText     edit text
     * @param emailErrorTextView    tv error
     * @param passwordErrorTextView password tv
     * @param message               error msg
     * @return true is entered
     */
    public boolean isInputEditTextEmail(final EditText textInputEditText, final TextView emailErrorTextView,
                                        final TextView passwordErrorTextView, final String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            emailErrorTextView.setText(message);
            passwordErrorTextView.setText(null);
            hideKeyboardFrom(emailErrorTextView);
            return false;
        } else {
            emailErrorTextView.setText(null);
        }
        return true;
    }

    /**
     * Is entered text is email
     *
     * @param textInputEditText  text
     * @param emailErrorTextView text error
     * @param message            msg
     * @return true if email
     */
    public boolean isInputEditTextEmail(final EditText textInputEditText, final TextView emailErrorTextView, final String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            emailErrorTextView.setText(message);
            hideKeyboardFrom(emailErrorTextView);
            return false;
        } else {
            emailErrorTextView.setText(null);
        }
        return true;
    }

    /**
     * compare password.
     *
     * @param textInputEditText1         text input 1
     * @param textInputEditText2         text input 2
     * @param passwordMatchErrorTextView error message
     * @param message                    msg
     * @return true if empty
     */
    public boolean isInputEditTextMatches(final EditText textInputEditText1, final EditText textInputEditText2,
                                          final TextView passwordMatchErrorTextView, final String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            passwordMatchErrorTextView.setText(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        } else {
            passwordMatchErrorTextView.setVisibility(View.GONE);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view view to hide
     */
    private void hideKeyboardFrom(final View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
