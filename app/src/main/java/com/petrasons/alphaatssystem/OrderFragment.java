package com.petrasons.alphaatssystem;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import static android.content.ContentValues.TAG;


public class OrderFragment extends Fragment {
    private static LoginSession mSession;
    private static Boolean runned = false;
    private static OrderAdapter adapterOpen = null;
    private static OrderAdapter adapterHistorical = null;
    public static View v = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(v == null)
            v = inflater.inflate(R.layout.activity_order_fragment, container, false);
        runned = true;
        // Create the adapter to convert the array to views
        if(adapterOpen == null) {
            adapterOpen = new OrderAdapter(this.getContext(), mSession.getOpenPosition(), mSession);
        }
        // Attach the adapter to a ListView
        ListView openPositionList = v.findViewById(R.id.openPositionList);
        openPositionList.setAdapter(adapterOpen);
        // Create the adapter to convert the array to views
        if(adapterHistorical == null) {
            adapterHistorical = new OrderAdapter(this.getContext(), mSession.getHistoricalOrder(), mSession);
        }
        // Attach the adapter to a ListView
        ListView historicalOrderList = v.findViewById(R.id.historicalOrderList);
        historicalOrderList.setAdapter(adapterHistorical);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!runned)
        {
            adapterOpen.clear();
            adapterOpen.addAll(mSession.getOpenPosition());
            adapterOpen.notifyDataSetChanged();
            adapterHistorical.clear();
            adapterHistorical.addAll(mSession.getHistoricalOrder());
            adapterHistorical.notifyDataSetChanged();
            runned = true;
        }
    }

    public static OrderFragment newInstance(LoginSession loginSession) {
        OrderFragment f = new OrderFragment();
        mSession = loginSession;
        return f;
    }
    public static void setFlag(){
        runned = false;
    }
}