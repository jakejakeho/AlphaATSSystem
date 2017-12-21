package com.petrasons.alphaatssystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LogoutFragment extends Fragment {
    private static LoginSession mSession;
    private static Boolean runned = false;
    private Button logoutButton = null;
    private TextView loggedInAs = null;
    private ProgressDialog pd = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_logout_fragment, container, false);
        logoutButton = (Button) v.findViewById(R.id.username_log_out_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.logout(true);
            }
        });
        loggedInAs = (TextView) v.findViewById(R.id.logged_in_as);
        loggedInAs.setText(String.format(v.getResources().getString(R.string.logged_in_as), mSession.getUsername()));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!runned)
        {
            System.out.println("logout fragment resume");
//            showProgress(false);
            runned = true;
        }
    }

    public static LogoutFragment newInstance(LoginSession loginSession) {

        LogoutFragment f = new LogoutFragment();
        mSession = loginSession;
        return f;
    }

    public static void setFlag(){
        runned = false;
    }

}