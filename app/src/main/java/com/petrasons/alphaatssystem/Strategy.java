package com.petrasons.alphaatssystem;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jake on 2017/08/27.
 */

public class Strategy {
    private String id = "";
    private String platformAccount = "";
    private String brokerAccount = "";
    private String brokerage = "";
    private String productCode = "";
    private int barSize = 0;
    private int orderSize = 0;
    private boolean isMonitoring = false;
    private String entryStrategyID = "";
    private String entryStrategy = "";
    private String entryPriceType = "";
    private int entryPrice = 0;
    private int entryDifference = 0;
    private String exitStrategyID = "";
    private String exitStrategy = "";
    private String exitPriceType = "";
    private int exitPrice = 0;
    private int exitDifference = 0;
    private int version = 0;
    private String createdAt = "";

    public Strategy(JSONObject strategies)
    {
        try{
            version = strategies.getInt("__v");
            if(version == 0)
            {
                id = strategies.getString("_id");
                platformAccount = strategies.getString("platform_account");
                brokerAccount = strategies.getString("broker_account");
                brokerage = strategies.getString("brokerage");
                productCode = strategies.getString("product_code");
                barSize = strategies.getInt("bar_size");
                orderSize = strategies.getInt("order_size");
                isMonitoring = strategies.getBoolean("is_monitoring");
                System.out.println("isMonitoringGET = " +isMonitoring);
                entryStrategyID = strategies.getString("entry_strategy_id");
                entryStrategy = StringCapitalize.capitalizeWord(strategies.getString("entry_strategy").replaceAll("_"," "));
                entryPriceType = strategies.getString("entry_price_type");
                entryPrice = strategies.getInt("entry_price");
                entryDifference = strategies.getInt("entry_diff");
                exitStrategyID = strategies.getString("exit_strategy_id");
                exitStrategy = StringCapitalize.capitalizeWord(strategies.getString("exit_strategy").replaceAll("_"," "));
                exitPriceType = strategies.getString("exit_price_type");
                exitPrice = strategies.getInt("exit_price");
                exitDifference = strategies.getInt("exit_diff");
                createdAt = strategies.getString("created_at");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        try{
            JSONObject object = new JSONObject();
            object.put("id", id);
//            object.put("platform_account", platformAccount);
//            object.put("broker_account", brokerAccount);
//            object.put("brokerage", brokerage);
//            object.put("product_code", productCode);
//            object.put("bar_size", barSize);
            object.put("status", isMonitoring);
//            object.put("entry_strategy_id", entryPriceType);
//            object.put("entry_strategy", entryStrategy);
//            object.put("entry_price_type", entryPriceType);
//            object.put("entry_price", entryPrice);
//            object.put("entry_diff", entryDifference);
//            object.put("exit_strategy_id", exitStrategyID);
//            object.put("exit_strategy",exitStrategy);
//            object.put("exit_price_type", exitPriceType);
//            object.put("exit_price", exitPrice);
//            object.put("exit_diff", exitDifference);
//            object.put("created_at", createdAt);
            return object;
        } catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getPlatformAccount() {
        return platformAccount;
    }

    public void setPlatformAccount(String platformAccount) {
        this.platformAccount = platformAccount;
    }

    public String getBrokerAccount() {
        return brokerAccount;
    }

    public void setBrokerAccount(String brokerAccount) {
        this.brokerAccount = brokerAccount;
    }

    public String getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(String brokerage) {
        this.brokerage = brokerage;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getBarSize() {
        return barSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
    }

    public int getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(int orderSize) {
        this.orderSize = orderSize;
    }

    public boolean isMonitoring() {
        return isMonitoring;
    }

    public void setMonitoring() {
        System.out.println("isMonitoringSET = " + isMonitoring +" " + id);
        isMonitoring = !isMonitoring;
        System.out.println("isMonitoringSET = " + isMonitoring +" " + id);
    }

    public String getEntryStrategyID() {
        return entryStrategyID;
    }

    public void setEntryStrategyID(String entryStrategyID) { this.entryStrategyID = entryStrategyID;  }

    public String getEntryStrategy() {
        return entryStrategy;
    }

    public void setEntryStrategy(String entryStrategy) {
        this.entryStrategy = entryStrategy;
    }

    public String getEntryPriceType() {
        return entryPriceType.substring(0, 1).toUpperCase() + entryPriceType.substring(1);
    }

    public void setEntryPriceType(String entryPriceType) {
        this.entryPriceType = entryPriceType;
    }

    public int getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(int entryPrice) {
        this.entryPrice = entryPrice;
    }

    public int getEntryDifference() {
        return entryDifference;
    }

    public void setEntryDifference(int entryDifference) {
        this.entryDifference = entryDifference;
    }

    public String getExitStrategyID() {
        return exitStrategyID;
    }

    public void setExitStrategyID(String exitStrategyID) {
        this.exitStrategyID = exitStrategyID;
    }

    public String getExitStrategy() {
        return exitStrategy;
    }

    public void setExitStrategy(String exitStrategy) {
        this.exitStrategy = exitStrategy;
    }

    public String getExitPriceType() {
        return exitPriceType.substring(0, 1).toUpperCase() + exitPriceType.substring(1);
    }

    public void setExitPriceType(String exitPriceType) {
        this.exitPriceType = exitPriceType;
    }

    public int getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(int exitPrice) {
        this.exitPrice = exitPrice;
    }

    public int getExitDifference() {
        return exitDifference;
    }

    public void setExitDifference(int exitDifference) {
        this.exitDifference = exitDifference;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
