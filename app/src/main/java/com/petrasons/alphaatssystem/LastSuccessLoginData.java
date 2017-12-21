package com.petrasons.alphaatssystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jake on 2017/08/28.
 */

public class LastSuccessLoginData {
    private static SharedPreferences pref = null;
    private static SharedPreferences.Editor editor = null;
    private LoginInfo loginInfo = null;
    private String session_id = "";

    public LastSuccessLoginData(Context context){
        pref =  PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
        loginInfo = new LoginInfo("","");
    }


    public String getUsername() {
        loginInfo.setUsername(pref.getString("username", ""));
        return loginInfo.getUsername();
    }

    public String getPassword(){
        return loginInfo.getPassword();
    }
    public String getSession_id() {
        session_id = pref.getString("session_id", "");
        return session_id;
    }

    public void updateData(LoginInfo loginInfo){
        this.loginInfo = loginInfo;
        editor.putString("username", loginInfo.getUsername());
        editor.putString("password", loginInfo.getPassword());
        editor.apply();
    }

    public void updateData(LoginInfo loginInfo, String session_id){
        this.loginInfo = loginInfo;
        this.session_id = session_id;
        editor.putString("username", loginInfo.getUsername());
        editor.putString("password", loginInfo.getPassword());
        editor.putString("session_id", session_id);
        editor.apply();
    }

    public void logout(){
        this.loginInfo = loginInfo;
        this.session_id = session_id;
        loginInfo.setUsername("");
        loginInfo.setPassword("");
        session_id = "";
        editor.putString("username", "");
        editor.putString("password", "");
        editor.putString("session_id", "");
        editor.apply();
    }

    public String toJSON(){
        JSONObject object = new JSONObject();
        try{
            object.put("user_id", getUsername());
            object.put("password", getPassword());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }
}
