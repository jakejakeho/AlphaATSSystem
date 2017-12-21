package com.petrasons.alphaatssystem;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Jake on 2017/08/28.
 */

public class LoginInfo implements Parcelable {
    private String user_id = "";
    private String password = "";
    private String device_id = "";
    private String firebase_token = "";
    public LoginInfo(String username, String password, String device_id)
    {
        this.user_id = username;
        this.password = password;
        this.device_id = device_id;
        this.firebase_token = FirebaseInstanceId.getInstance().getToken();
    }
    public LoginInfo(String username, String password)
    {
        this.user_id = username;
        this.password = password;
    }

    public LoginInfo(String username) {
        this.user_id = username;
    }



    public String getUsername() {
        return user_id;
    }

    public void setUsername(String username){
        user_id = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String toJSON(){
        JSONObject object = new JSONObject();
        try{
           object.put("user_id", user_id);
           object.put("password", password);
           object.put("device_id", device_id);
            object.put("firebase_token", firebase_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.password);
    }

    protected LoginInfo(Parcel in) {
        this.user_id = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<LoginInfo> CREATOR = new Parcelable.Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };
}
