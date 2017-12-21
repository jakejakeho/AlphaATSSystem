package com.petrasons.alphaatssystem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jake on 2017/08/28.
 */

public class LoginReply{
    private boolean success = false;
    private String message = "";
    private SessionData data = null;
    public LoginReply(JSONObject object) {
        try {
            this.success = object.getBoolean("success");
            this.message = object.getString("message");
            this.data = new SessionData(object.getJSONObject("data"));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public LoginReply(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginReply(JSONObject object, boolean b) {
        try {
            this.success = object.getBoolean("success");
            this.message = object.getString("message");
            this.data = new SessionData(object.getJSONObject("data"), b);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getSession_id() {
        if(data != null)
            return data.getSession_id();
        else
            return "";
    }

    public String getMessage() {
        if(message == null)
            return "";
        return message;
    }

    public String getDeviceID(){
        return data.getDevice_id();
    }

    public boolean isSuccess() {
        return success;
    }

    public SessionData getData(){
        return data;
    }

    public boolean isStatus() {
        if (data != null)
            return data.isStatus();
        else {
            return false;
        }
    }

    public int getAccountNum(){
        return data.getAccountsNum();
    }

}
