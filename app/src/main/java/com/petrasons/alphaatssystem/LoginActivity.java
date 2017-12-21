package com.petrasons.alphaatssystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;


/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginSession mSession = null;
    // UI references.
    private EditText mUsernameView = null;
    private EditText mPasswordView = null;
    private int pressed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("FCM", "Firebase Token: " + FirebaseInstanceId.getInstance().getToken());
        // Set up the login form.
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(pressed % 2 == 1)
                    attemptLogin();
                pressed++;
                return false;
            }
        });

        Button mUsernameLogInButton = (Button) findViewById(R.id.username_log_in_button);
        mUsernameLogInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        View mForgetPasswordView = findViewById(R.id.forget_password);
        mForgetPasswordView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession = null;
                new PopupMessage(mSession, LoginActivity.this);
            }
        });
        View mRegisterView = findViewById(R.id.register);
        mRegisterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        loginFromLastSession();
    }
    private void loginFromLastSession()
    {
        System.out.println("test");
        mSession = new LoginSession(LoginActivity.this);
        mSession.getData(true,true);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid user name.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mSession = new LoginSession(this, username, password);
            mSession.login();
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }
}