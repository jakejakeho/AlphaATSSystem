package com.petrasons.alphaatssystem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jake on 2017/08/27.
 */

public class Order{
    private String id = "";
    private String exitDate = "";
    private int exitPrice = 0;
    private String exitPriceType = "";
    private String exitStrategy = "";
    private String exitStrategyID = "";
    private int entryPrice = 0;
    private String entryPriceType = "";
    private String entryStrategy = "";
    private String entryStrategyID = "";
    private String platformAccount = "";
    private String brokerAccount = "";
    private String brokerage = "";
    private String referenceCode = "";
    private String productCode = "";
    private boolean isOpen = false;
    private String position = "";
    private boolean isManualEntry = false;
    private boolean isManualExit = false;
    private int barSize = 0;
    private int orderSize = 0;
    private String entryDate = "";
    private int version = 0;
    private int pnl = 0;

    public Order(JSONObject order)
    {
        try {
            version = order.getInt("__v");
            if(version == 0)
            {
                id = order.getString("_id");
                exitDate = order.getString("exit_date");
                exitPrice = order.getInt("exit_price");
                exitPriceType = order.getString("exit_price_type");
                exitStrategy = StringCapitalize.capitalizeWord(order.getString("exit_strategy").replaceAll("_"," "));
                entryPrice = order.getInt("entry_price");
                entryPriceType = order.getString("entry_price_type");
                entryStrategy = StringCapitalize.capitalizeWord(order.getString("entry_strategy").replaceAll("_"," "));
                entryStrategyID = order.getString("entry_strategy_id");
                platformAccount = order.getString("platform_account");
                brokerAccount = order.getString("broker_account");
                brokerage = order.getString("brokerage");
                referenceCode = order.getString("ref_code");
                productCode = order.getString("product_code");
                isOpen = order.getBoolean("is_open");
                position = order.getString("position");
                isManualEntry = order.getBoolean("is_manual_entry");
                isManualExit = order.getBoolean("is_manual_exit");
                barSize = order.getInt("bar_size");
                orderSize = order.getInt("order_size");
                entryDate = order.getString("entry_date");
                pnl = order.getInt("pnl");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        try{
            JSONObject object = new JSONObject();
            object.put("_id", id);
            object.put("exit_date", exitDate);
            object.put("exit_price", exitPrice);
            object.put("exit_price_type", exitPriceType);
            object.put("exit_strategy", exitStrategy);
            object.put("exit_strategy_id", exitStrategyID);
            object.put("entry_price", entryPrice);
            object.put("entry_price_type", entryPriceType);
            object.put("entry_strategy", entryStrategy);
            object.put("entry_strategy_id", entryStrategyID);
            object.put("platform_account", platformAccount);
            object.put("broker_account", brokerAccount);
            object.put("brokerage",brokerage);
            object.put("ref_code", referenceCode);
            object.put("product_code", productCode);
            object.put("is_open", isOpen);
            object.put("position", position);
            object.put("is_manual_entry", isManualEntry);
            object.put("is_manual_exit", isManualExit);
            object.put("bar_size", barSize);
            object.put("order_size", orderSize);
            object.put("entry_date", entryDate);
            object.put("pnl", pnl);
            return object;
        } catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExitDate() {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "yyyy/MM/dd HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(this.exitDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
    }

    public int getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(int exitPrice) {
        this.exitPrice = exitPrice;
    }

    public String getExitPriceType() {
        return exitPriceType;
    }

    public void setExitPriceType(String exitPriceType) {
        this.exitPriceType = exitPriceType;
    }

    public String getExitStrategy() {
        return exitStrategy;
    }

    public void setExitStrategy(String exitStrategy) {
        this.exitStrategy = exitStrategy;
    }

    public String getExitStrategyID() {
        return exitStrategyID;
    }

    public void setExitStrategyID(String exitStrategyID) {
        this.exitStrategyID = exitStrategyID;
    }

    public int getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(int entryPrice) {
        this.entryPrice = entryPrice;
    }

    public String getEntryPriceType() {
        return entryPriceType;
    }

    public void setEntryPriceType(String entryPriceType) {
        this.entryPriceType = entryPriceType;
    }

    public String getEntryStrategy() {
        return entryStrategy;
    }

    public void setEntryStrategy(String entryStrategy) {
        this.entryStrategy = entryStrategy;
    }

    public String getEntryStrategyID() {
        return entryStrategyID;
    }

    public void setEntryStrategyID(String entryStrategyID) {
        this.entryStrategyID = entryStrategyID;
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

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isManualEntry() {
        return isManualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        isManualEntry = manualEntry;
    }

    public boolean isManualExit() {
        return isManualExit;
    }

    public void setManualExit(boolean manualExit) {
        isManualExit = manualExit;
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

    public String getEntryDate() {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "yyyy/MM/dd HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(this.entryDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getPnl() {
        return pnl;
    }

    public void setPnl(int pnl) {
        this.pnl = pnl;
    }

}
