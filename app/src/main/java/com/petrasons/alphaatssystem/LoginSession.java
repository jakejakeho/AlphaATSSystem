package com.petrasons.alphaatssystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jake on 2017/08/25.
 */
@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class LoginSession {
    private Activity activity;
    private LoginInfo loginInfo = null;
    private LoginReply loginReply = null;
    private PostData postData = null;
    private LastSuccessLoginData lastSuccessLoginData = null;
    private JSONArray message = null;
    private Gson gson = new Gson();
    private ProgressDialog pd = null;
    private UserLoginTask mLoginTask = null;
    private UserLogoutTask mLogoutTask = null;
    private UserGetDataTask mGetDataTask = null;
    private UserPostTask mPostDataTask = null;
    private UserGetHistoryTask mGetHistoryTask = null;

    public void setPosting(boolean posting) {
        this.posting = posting;
    }

    private boolean posting = false;
    private String dataToPost;
    private String device_id = "";

    public LoginSession(Activity activity,String username, String password) {
        this.activity = activity;
        lastSuccessLoginData = new LastSuccessLoginData(activity.getApplicationContext());
        device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        loginInfo = new LoginInfo(username, password, device_id);
    }

    public LoginSession(Activity activity) {
        this.activity = activity;
        lastSuccessLoginData = new LastSuccessLoginData(activity.getApplicationContext());
        loginInfo = new LoginInfo(lastSuccessLoginData.getUsername());
    }

    // login
    public void login(){
        if (mLoginTask != null) {
            return;
        }
        mLoginTask = new UserLoginTask();
        showProgress(true);
        mLoginTask.execute((Void)null);
    }

    public ArrayList<Account> getAllAccount() {
        return postData.getAllAccount();
    }

    public String getDeviceID() {
        return device_id;
    }

    public ArrayList<Message> getAllMessage() {
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            if (message != null) {
                int len = message.length();
                for (int i = 0; i < len; i++) {
                    messages.add(new Message(message.getJSONObject(i)));
                }
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        // for debug
//        for (int i = 0; i < 10; i++) {
//            messages.add(new Message("2017-09-27T06:09:50.676Z","System Online"));
//        }
        return messages;
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isNetworkConnected() && isInternetAvailable()){
                try {
                    URL url = new URL("https://0688tckhoj.execute-api.ap-southeast-1.amazonaws.com/dev/mobile/android/login");
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    if (con != null) {
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setRequestProperty("Content-Type", "application/json");
                        con.setRequestMethod("POST");
                    }
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(loginInfo.toJSON());
                    Log.d("LoginData", loginInfo.toJSON());
                    wr.flush();
                    wr.close();

                    //display what returns the POST request
                    StringBuilder sb = new StringBuilder();
                    int HttpResult = con.getResponseCode();
                    if (HttpResult == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br;
                        String line;
                        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        System.out.println("Login OK");
                        try {
                            loginReply = new LoginReply(new JSONObject(sb.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(loginReply.isSuccess() && getMessage().equalsIgnoreCase("success") && !posting){
                            lastSuccessLoginData.updateData(loginInfo, loginReply.getSession_id());
                        }else if(getMessage().equalsIgnoreCase("account has already logged in android") || getMessage().equalsIgnoreCase("account has already logged in ios")){
                            lastSuccessLoginData.updateData(loginInfo);
                            device_id = loginReply.getDeviceID();
                        }
                    } else {
                        System.out.println(con.getResponseMessage());
                        loginReply = new LoginReply(false, "no internet");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(loginReply!=null)
                    return loginReply.isSuccess();
                else{
                    loginReply = new LoginReply(false, "no internet");
                    return false;
                }
            }
            loginReply = new LoginReply(false, "no internet");
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mLoginTask = null;
            showProgress(false);
            if(success) {
                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
            else
                new PopupMessage(LoginSession.this, activity);
        }

        @Override
        protected void onCancelled() {
            mLoginTask = null;
            showProgress(false);
        }
    }

    // logout
    public void logout(boolean returnLogin){
        mLogoutTask = new UserLogoutTask(returnLogin);
        showProgress(true);
        mLogoutTask.execute((Void) null);
    }
    private class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {
        boolean returnLogin;
        public UserLogoutTask(boolean returnLogin){
            this.returnLogin = returnLogin;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isNetworkConnected() && isInternetAvailable()) {
                try {
                    URL url = new URL("https://0688tckhoj.execute-api.ap-southeast-1.amazonaws.com/dev/mobile/logout");
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    if (con != null) {
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setRequestProperty("Content-Type", "application/json");
                        con.setRequestMethod("POST");
                    }
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    if (returnLogin)
                        wr.write(lastSuccessLoginData.toJSON());
                    else
                        wr.write(loginInfo.toJSON());
                    wr.flush();
                    wr.close();

                    //display what returns the POST request
                    StringBuilder sb = new StringBuilder();
                    int HttpResult = con.getResponseCode();

                    if (HttpResult == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br;
                        String line;
                        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        System.out.println("Logout OK");
                        try {
                            loginReply = new LoginReply(new JSONObject(sb.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (loginReply.isSuccess() && getMessage().equalsIgnoreCase("success")) {
                            lastSuccessLoginData.updateData(loginInfo, loginReply.getSession_id());
                        }
                    } else {
                        System.out.println(con.getResponseMessage());
                        loginReply = new LoginReply(false, "no internet");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (loginReply != null)
                    return loginReply.isSuccess();
                else {
                    loginReply = new LoginReply(false, "no internet");
                    return false;
                }
            }
            loginReply = new LoginReply(false, "no internet");
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mLoginTask = null;
            if(success) {
                if(returnLogin){
                    lastSuccessLoginData.logout();
                    Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    showProgress(false);
                }
            }
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mLoginTask = null;
            showProgress(false);
        }
    }

    // GET Data
    public void getData(boolean showProgress, boolean mainActivity){
        mGetDataTask = new UserGetDataTask(showProgress, mainActivity);
        showProgress(showProgress);
        mGetDataTask.execute((Void) null);
    }
    private class UserGetDataTask extends AsyncTask<Void, Void, Boolean> {
        boolean showProgress;
        boolean loginActivity;
        public UserGetDataTask(boolean showProgress,boolean loginActivity){
            this.showProgress = showProgress;
            this.loginActivity = loginActivity;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isNetworkConnected() && isInternetAvailable()) {
                try {
                    if (lastSuccessLoginData.getUsername().length() == 0)
                        return false;
                    URL url = new URL("https://0688tckhoj.execute-api.ap-southeast-1.amazonaws.com/dev/data?mobile=android&user_id=" + lastSuccessLoginData.getUsername() + "&session_id=" + lastSuccessLoginData.getSession_id());
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    //add reuqest header
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(5000);

                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending 'GET' request to URL : " + url);
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //print result
                    System.out.println("getAPIData OK");
                    try {
                        loginReply = new LoginReply(new JSONObject(response.toString()), false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (loginReply.isSuccess() && !posting) {
                        postData = new PostData(lastSuccessLoginData, loginReply);
                    }
                    return loginReply.isSuccess();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loginReply = new LoginReply(false, "no internet");
                return false;
            }
            loginReply = new LoginReply(false, "no internet");
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mGetDataTask = null;
            System.out.println("success" + success);
            if(!posting)
                showProgress(false);
            if(success) {
                if(showProgress && loginActivity){
                    View view = activity.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
                if(showProgress && !loginActivity){
                    ((MainActivity)activity).hideView(false);
                }
            }
            else
                new PopupMessage(LoginSession.this, activity);
        }

        @Override
        protected void onCancelled() {
            mGetDataTask = null;
            if(!posting)
                showProgress(false);
        }
    }

    // POST Data
    public void postData(){
        posting = true;
        showProgress(true);
        mPostDataTask = new UserPostTask();
        mPostDataTask.execute((Void) null);
        dataToPost = postData.toJSON();
    }

    private class UserPostTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            System.out.println("postingData " + dataToPost);
            if(isNetworkConnected() && isInternetAvailable()) {
                try {
                    URL url = new URL("https://0688tckhoj.execute-api.ap-southeast-1.amazonaws.com/dev/mobile/data");
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                    if (con != null) {
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setRequestProperty("Content-Type", "application/json");
                        con.setRequestMethod("POST");
                    }

                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(dataToPost);
                    wr.flush();
                    wr.close();

                    //display what returns the POST request

                    StringBuilder sb = new StringBuilder();
                    int HttpResult = 0;
                    HttpResult = con.getResponseCode();

                    if (HttpResult == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br;
                        String line;
                        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        System.out.println("postAPIData OK");
                        loginReply = gson.fromJson(sb.toString(), loginReply.getClass());
                    } else {
                        System.out.println(con.getResponseMessage());
                        loginReply = new LoginReply(false, "no internet");
                        return false;
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (loginReply != null)
                    return loginReply.isSuccess();
                else {
                    loginReply = new LoginReply(false, "no internet");
                    return false;
                }
            }
            loginReply = new LoginReply(false, "no internet");
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mGetDataTask = null;
            if(!success) {
                new PopupMessage(LoginSession.this, activity);
            }else {
                posting = false;
                getData(false,false);
            }
        }

        @Override
        protected void onCancelled() {
            mPostDataTask = null;
            if(!posting)
                showProgress(false);
        }
    }

    // GET Data
    public void getHistory(boolean showProgress, boolean mainActivity){
        mGetHistoryTask = new UserGetHistoryTask(showProgress, mainActivity);
        mGetHistoryTask.execute((Void) null);
    }
    private class UserGetHistoryTask extends AsyncTask<Void, Void, Boolean> {
        boolean showProgress;
        boolean loginActivity;
        public UserGetHistoryTask(boolean showProgress,boolean loginActivity){
            this.showProgress = showProgress;
            this.loginActivity = loginActivity;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isNetworkConnected() && isInternetAvailable()) {
                try {
                    if (lastSuccessLoginData.getUsername().length() == 0)
                        return false;
                    URL url = new URL("https://0688tckhoj.execute-api.ap-southeast-1.amazonaws.com/dev/data/history?mobile=android&user_id=" + lastSuccessLoginData.getUsername() + "&session_id=" + lastSuccessLoginData.getSession_id());
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    //add reuqest header
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(5000);

                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending 'GET' request to URL : " + url);
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //print result
                    System.out.println("getHistory OK");
                    JSONObject reply = new JSONObject(response.toString());
                    boolean success = reply.getBoolean("success");
                    if (success) {
                        message = reply.getJSONArray("data");
                    }
                    return success;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loginReply = new LoginReply(false, "no internet");
                return false;
            }
            loginReply = new LoginReply(false, "no internet");
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mGetDataTask = null;
            System.out.println("success" + success);
            if(success) {
                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(showProgress && loginActivity){
                    Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
            else
                new PopupMessage(LoginSession.this, activity);
        }

        @Override
        protected void onCancelled() {
            mGetDataTask = null;
        }
    }

    // get
    public String getUsername(){
        return lastSuccessLoginData.getUsername();
    };
    public boolean getSuccess()
    {
        return loginReply.isSuccess();
    }
    public void toogleStatus(){
        if(postData!=null)
            postData.toogleStatus();
    }
    String getMessage() {
        if(loginReply!=null)
            return loginReply.getMessage();
        else
            return "";
    }
    String getSession_id()
    {
        return lastSuccessLoginData.getSession_id();
    }
    Boolean isStatus(){
        if(postData!=null)
            return postData.isStatus();
        else
            return false;
    }

    public ArrayList<Strategy> getAllStrategies() {
        return postData.getAllStrategies();
    }

    public int getNumStrategies() {
        return postData.getNumStrategies();
    }

    public int getNumStrategiesON() {
        return postData.getNumStrategiesON();
    }

    public int getNumStrategiesOFF() {
        return postData.getNumStrategiesOFF();
    }

    public ArrayList<Order> getOpenPosition() {
        return postData.getOpenPosition();
    }

    public ArrayList<Order> getHistoricalOrder() { return postData.getHistoricalOrder(); }

    /**
     * Shows the progress UI and hides the login form.
     */
    protected void showProgress(final boolean show) {
        if(pd==null){
            pd = new ProgressDialog(activity,R.style.MyTheme);
            pd.setCancelable(false);
            pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        }
        if(show)
        {
            try {
                pd.show();
            } catch (final IllegalArgumentException e) {
                // Do nothing.
                e.printStackTrace();
            } catch (final Exception e) {
                // Do nothing.
                e.printStackTrace();
            } finally {
            }
        }
        else
        {
            if(pd!=null && !posting){
                try {
                    pd.cancel();
                } catch (final IllegalArgumentException e) {
                    // Do nothing.
                    e.printStackTrace();
                } catch (final Exception e) {
                    // Do nothing.
                    e.printStackTrace();
                } finally {
                }
            }
        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public void resetLoginInfo(){
        lastSuccessLoginData.logout();
    }
}