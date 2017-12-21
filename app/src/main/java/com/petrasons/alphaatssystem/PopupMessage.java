package com.petrasons.alphaatssystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Jake on 2017/08/31.
 */

public class PopupMessage {
    private static boolean noInternetPopped = false;
    private static boolean sessionExpiredPopped = false;
    private LoginSession mSession = null;
    private Activity activity = null;
    private ProgressDialog pd = null;
    public PopupMessage(LoginSession mSession, Activity activity){
        this.mSession = mSession;
        this.activity = activity;
        popupLoginResult();
    }
    private void popupLoginResult() {
        String message;
        int attempts;
        if (mSession == null) {
            popupForgetPassword();
        } else {
            message = mSession.getMessage();
            if (message.equalsIgnoreCase("")) {
            } else if ((message.equalsIgnoreCase("session expired") || message.equalsIgnoreCase("session not found")) && !sessionExpiredPopped) {
                sessionExpiredPopped = true;
                popupSessionExpried();
            } else if (message.equalsIgnoreCase("account not found") || message.equalsIgnoreCase("wrong passowrd")) {
                attempts = 0;
                popupFailedLogin(attempts);
            } else if (message.equalsIgnoreCase("no internet") && !noInternetPopped) {
                noInternetPopped = true;
                popupNoInternet();
            } else if (message.equalsIgnoreCase("account has already logged in ios") || message.equalsIgnoreCase("account has already logged in android")) {
                popupLoggedIn(message, mSession.getDeviceID());
            }
        }
    }

    private void popupSessionExpried() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(activity.getString(R.string.popup_session_expired_detail))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.popup_session_expired_right_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'Yes' Button
                        Log.d("Session Expired", activity.getClass().getSimpleName());
                        if(activity.getClass().getSimpleName().equalsIgnoreCase("MainActivity")){
                            activity.finish();
                            mSession.resetLoginInfo();
                            Intent intent = new Intent(activity, LoginActivity.class);
                            activity.startActivity(intent);
                        }
                        sessionExpiredPopped =false;
                        dialog.cancel();
                    }
                })
                .setNegativeButton(activity.getString(R.string.popup_session_expired_left_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        sessionExpiredPopped =false;
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(activity.getString(R.string.popup_session_expired));

        if(!activity.isFinishing())
        {
            //show dialog
            alert.show();
        }
    }

    private void popupLoggedIn(String message, String device_ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(String.format(activity.getString(R.string.popup_one_device_detail),message.contains("ios") ? ("IOS #" + device_ID) : ("Android #" + device_ID)))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.popup_one_device_right_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'Yes' Button
                        System.out.println(mSession.getMessage());
                        mSession.logout(false);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(activity.getString(R.string.popup_one_device_left_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(activity.getString(R.string.popup_one_device));
        if(!activity.isFinishing())
        {
            //show dialog
            alert.show();
        }
    }

    private void popupForgetPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.popup_forget_password));

        // Set up the input
        final EditText input = new EditText(activity);
        builder.setView(input);
        builder.setMessage(activity.getString(R.string.popup_forget_password_detail));
        // Set up the buttons
        builder.setPositiveButton(activity.getString(R.string.popup_forget_password_right_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // forget password
                input.getText().toString();
            }
        });
        builder.setNegativeButton(activity.getString(R.string.popup_forget_password_left_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        if(!activity.isFinishing())
        {
            //show dialog
            builder.show();
        }
    }

    private void popupNoInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(activity.getString(R.string.popup_no_internet_detail))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.popup_no_internet_right_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'Yes' Button
                        noInternetPopped = false;
                        dialog.cancel();
                    }
                })
                .setNegativeButton(activity.getString(R.string.popup_no_internet_left_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        noInternetPopped = false;
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(activity.getString(R.string.popup_no_internet));
        if(!activity.isFinishing())
        {
            //show dialog
            alert.show();
        }
    }

    private void popupFailedLogin(int attempts) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(String.format(activity.getString(R.string.popup_failed_to_login_detail), attempts))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.popup_failed_to_login_right_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'Yes' Button
                        dialog.cancel();
                    }
                })
                .setNegativeButton(activity.getString(R.string.popup_failed_to_login_left_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(activity.getString(R.string.popup_failed_to_login));
        if(!activity.isFinishing())
        {
            //show dialog
            alert.show();
        }
    }

}
