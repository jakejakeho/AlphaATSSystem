package com.petrasons.alphaatssystem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jake on 2017/08/28.
 */

public class SessionData{
    private String device_id = "";
    private String session_id = "";
    private boolean status = false;
    private Account[] accounts = null;

    public SessionData(JSONObject object){
        try{
            this.device_id = object.getString("device_id");
        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            this.session_id = object.getString("session_id");
            this.status = object.getBoolean("status");
            JSONArray accountArray = object.getJSONArray("accounts");
            System.out.println("accounts = " + accountArray.length());
            accounts = new Account[accountArray.length()];
            for(int i = 0 ; i < accountArray.length(); i++)
            {
                accounts[i] = new Account(accountArray.getJSONObject(i));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public SessionData(JSONObject object, boolean b) {
        try{
            this.status = object.getBoolean("status");
            JSONArray accountArray = object.getJSONArray("accounts");
            System.out.println("accounts = " + accountArray.length());
            accounts = new Account[accountArray.length()];
            for(int i = 0 ; i < accountArray.length(); i++)
            {
                accounts[i] = new Account(accountArray.getJSONObject(i));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public boolean isStatus() {
        return status;
    }

    public Account getAccountById(int id){
        return accounts[id];
    }

    public int getAccountsNum(){
        return accounts.length;
    }

    public String getDevice_id(){ return device_id;}
    public String getSession_id() {
        return session_id;
    }

    public void toogleStatus(){
        status = !status;
        System.out.println("toogleing status = " + status);
    }

    public SessionData() {
    }

    public ArrayList<Strategy> getAllStrategies(){
        Strategy[] temp = new Strategy[0];
        for(int i = 0; i < accounts.length; i++)
        {
            temp = concat(temp,accounts[i].getAllStrategy());
        }
        return new ArrayList<>(Arrays.asList(temp));
    }

    public Strategy[] concat(Strategy[] a, Strategy[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Strategy[] c= new Strategy[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
    public JSONObject toJSON(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", status);
            JSONArray array = new JSONArray();
            for(int i = 0; i < accounts.length; i++)
            {
                array.put(accounts[i].toJSON());
            }
            jsonObject.put("accounts", array);
            return jsonObject;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public int getStrategiesNum(){
        int num = 0;
        for(int i = 0; i < accounts.length; i++)
        {
            num += accounts[i].getStrategiesNum();
        }
        return num;
    }

    public int getOrdersNum(){
        int num = 0;
        for(int i = 0; i < accounts.length; i++)
        {
            num += accounts[i].getOrdersNum();
        }
        return num;
    }

    public ArrayList<Account> getAllAccount() {
        return new ArrayList<>(Arrays.asList(accounts));
    }

    public ArrayList<Order> getOpenPosition() {
        ArrayList<Order> openPosition = new ArrayList<Order>();
        for(int i = 0; i < accounts.length; i++)
        {
            openPosition.addAll(accounts[i].getOpenPosition());
        }
        return openPosition;
    }

    public ArrayList<Order> getHistoricalOrder() {
        ArrayList<Order> historicalOrder = new ArrayList<Order>();
        for(int i = 0; i < accounts.length; i++)
        {
            historicalOrder.addAll(accounts[i].getHistoricalOrder());
        }
        return historicalOrder;
    }
}