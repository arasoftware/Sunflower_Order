package com.ara.sunflowerorder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.ara.sunflowerorder.models.User;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import static com.ara.sunflowerorder.utils.AppConstants.PASSWORD_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.USER_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.fetchWarehouse;
import static com.ara.sunflowerorder.utils.AppConstants.getUserLoginURL;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mUserIdView;
    private EditText mPasswordView;

    private View mLoginFormView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fetchWarehouse();
        // Set up the login form.
        mUserIdView = (AutoCompleteTextView) findViewById(R.id.userid);


        mPasswordView = (EditText) findViewById(R.id.password);


        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUserIdView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userId = mUserIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userId)) {
            mUserIdView.setError(getString(R.string.error_field_required));
            focusView = mUserIdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            HttpRequest httpRequest = new HttpRequest(getUserLoginURL(), HttpRequest.POST);
            httpRequest.addParam(USER_ID_PARAM, mUserIdView.getText().toString());
            httpRequest.addParam(PASSWORD_PARAM, mPasswordView.getText().toString());

            progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Validating User..");


            progressDialog.show();
            new HttpCaller() {
                @Override
                public void onResponse(HttpResponse response) {
                    progressDialog.dismiss();

                    if (response.getStatus() == HttpResponse.ERROR) {
                        showSnackbar(mLoginFormView, response.getMesssage());
                    } else {
                        String message = response.getMesssage();
                        if (message.isEmpty()) {
                            showSnackbar(mLoginFormView, "Server not Responding...");
                        } else if (message.contains("fail")) {
                            showSnackbar(mLoginFormView, "Login Failed..");
                            progressDialog.dismiss();
                        } else {
                            AppConstants.CurrentUser = User.fromJson(message);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }
            }.execute(httpRequest);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

