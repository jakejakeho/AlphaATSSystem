package com.petrasons.alphaatssystem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jake on 2017/09/08.
 */

public class Message {
    String date = "";
    String message = "";
    public Message(){

    }

    public Message(JSONObject o) {
        try{
            date = o.getString("created_at");
            message = o.getString("message");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    public Message(String date, String message) {
        this.date = date;
        this.message = message;
    }

    public String getTime(){
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        outputFormat.setTimeZone(TimeZone.getDefault());
        Date date;
        String str = null;

        try {
            date = inputFormat.parse(this.date);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String getDate(){
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        outputFormat.setTimeZone(TimeZone.getDefault());
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(this.date);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String getMessage(){
        return message;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setMessage(String message){
        this.message = message;
    }
}