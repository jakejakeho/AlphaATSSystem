package com.petrasons.alphaatssystem;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Jake on 2017/08/27.
 */

public class PostData {
    private String user_id = "";
    private String session_id = "";
    private String mobile = "";
    private SessionData data = null;

    public PostData(LastSuccessLoginData lastSuccessLoginData , LoginReply data)
    {
        this.user_id = lastSuccessLoginData.getUsername();
        this.session_id = lastSuccessLoginData.getSession_id();
        this.mobile = "android";
        this.data = data.getData();
        System.out.println(data.getData().toJSON());
    }

    public String toJSON(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", user_id);
            jsonObject.put("session_id", session_id);
            jsonObject.put("mobile", mobile);
            jsonObject.putOpt("appData", data.toJSON());
            return jsonObject.toString();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "";
    }

    public void toogleStatus(){
        data.toogleStatus();
    }
    public boolean isStatus(){
        if(data != null)
            return data.isStatus();
        else
            return false;
    }
    public ArrayList<Strategy> getAllStrategies(){
        return data.getAllStrategies();
    }
    public ArrayList<Order> getOpenPosition() {return data.getOpenPosition();}

    public int getStrategiesNum(){
        return data.getStrategiesNum();
    }
    public int getOrdersNum(){
        return data.getOrdersNum();
    }

    public int getNumStrategies() {
        return getAllStrategies().size();
    }

    public int getNumStrategiesON() {
        ArrayList<Strategy> strategies = getAllStrategies();
        int count = 0;
        for(int i = 0; i < strategies.size(); i++){
            if(strategies.get(i).isMonitoring())
                count++;
        }
        return count;
    }

    public int getNumStrategiesOFF() {
        ArrayList<Strategy> strategies = getAllStrategies();
        int count = 0;
        for(int i = 0; i < strategies.size(); i++){
            if(!strategies.get(i).isMonitoring())
                count++;
        }
        return count;
    }

    public ArrayList<Account> getAllAccount() {
        return data.getAllAccount();
    }

    public ArrayList<Order> getHistoricalOrder() {
        return data.getHistoricalOrder();
    }
}
