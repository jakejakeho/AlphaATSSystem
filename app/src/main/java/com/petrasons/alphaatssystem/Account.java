package com.petrasons.alphaatssystem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jake on 2017/08/27.
 */

public class  Account {
    private String account_id = "";
    private String account_type = "";
    private String broker_account = "";
    private int aggregate_PNL = 0;
    private int unrealised_PNL = 0;
    private int balance = 0;
    private Strategy[] strategies = null;
    private Order[] orders = null;

    public Account(JSONObject account)
    {
        try{
            account_id = account.getString("account_id");
            account_type = account.getString("account_type");
            broker_account = account.getString("broker_account");
            aggregate_PNL = account.getInt("aggregate_PNL");
            unrealised_PNL = account.getInt("unrealised_PNL");
            balance = account.getInt("balance");

            JSONArray tempStrategies = account.getJSONArray("strategies");
            strategies = new Strategy[tempStrategies.length()];
            for(int i = 0; i < tempStrategies.length(); i++)
            {
                strategies[i] = new Strategy(tempStrategies.getJSONObject(i));
            }

            JSONArray tempOrders = account.getJSONArray("orders");
            orders = new Order[tempOrders.length()];
            for(int i = 0; i < tempOrders.length(); i++)
            {
                orders[i] = new Order(tempOrders.getJSONObject(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getAccountID() {
        return account_id;
    }

    public String getAccountType() {
        return account_type;
    }

    public String getBrokerAccount() {
        return broker_account;
    }

    public int getAggregatePNL() {
        return aggregate_PNL;
    }

    public int getUnrealisedPNL() {
        return unrealised_PNL;
    }

    public int getBalance() {
        return balance;
    }

    public Strategy getStrategyById(int id){
        return strategies[id];
    }

    public Strategy[] getAllStrategy(){
        return strategies;
    }

    public int getStrategiesNum(){
        return strategies.length;
    }

    public Order getOrdersById(int id){
        return orders[id];
    }

    public int getOrdersNum(){
        return orders.length;
    }

    public JSONObject toJSON(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account_id", account_id);
            jsonObject.put("account_type", account_type);
            jsonObject.put("broker_account", broker_account);
            jsonObject.put("aggregate_PNL", aggregate_PNL);
            jsonObject.put("unrealised_PNL", unrealised_PNL);
            jsonObject.put("balance", balance);
            JSONArray strategiesArray = new JSONArray();
            for(int i = 0; i < strategies.length; i++)
            {
                strategiesArray.put(strategies[i].toJSON());
            }
            jsonObject.put("strategies", strategiesArray);
            return jsonObject;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Order> getOpenPosition(){
        ArrayList<Order> openPosition = new ArrayList<Order>();
        for(int i = 0; i < orders.length; i++)
        {
            if(orders[i].isOpen())
                openPosition.add(orders[i]);
        }
        return openPosition;
    }

    public ArrayList<Order>  getHistoricalOrder() {
        ArrayList<Order> historicalOrder = new ArrayList<Order>();
        for(int i = 0; i < orders.length; i++)
        {
            if(!orders[i].isOpen())
                historicalOrder.add(orders[i]);
        }
        return historicalOrder;
    }
}
