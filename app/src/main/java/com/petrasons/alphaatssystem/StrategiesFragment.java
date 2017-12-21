package com.petrasons.alphaatssystem;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class StrategiesFragment extends Fragment {
    private static LoginSession mSession;
    private static Boolean runned = false;
    private static StrategyAdapter adapter = null;
    private static TextView totalStrategies = null;
    private static TextView totalStrategiesON = null;
    private static TextView totalStrategiesOFF = null;
    public static View v = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(v == null)
            v = inflater.inflate(R.layout.activity_strategies_fragment, container, false);
        runned = true;
        // Create the adapter to convert the array to views
        if(adapter == null) {
            adapter = new StrategyAdapter(this.getContext(), mSession.getAllStrategies(), mSession);
        }
        // Attach the adapter to a ListView
        ListView listView = v.findViewById(R.id.strategies_list);
        listView.setAdapter(adapter);
        totalStrategies  = v.findViewById(R.id.totalStrategies);
        totalStrategiesON = v.findViewById(R.id.totalStrategiesON);
        totalStrategiesOFF = v.findViewById(R.id.totalStrategiesOFF);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!runned)
        {
            adapter.clear();
            adapter.addAll(mSession.getAllStrategies());
            adapter.notifyDataSetChanged();
            totalStrategies.setText(String.format("%02d", mSession.getNumStrategies()));
            totalStrategiesON.setText(String.format("%02d", mSession.getNumStrategiesON()));
            totalStrategiesOFF.setText(String.format("%02d", mSession.getNumStrategiesOFF()));
            runned = true;
        }
    }

    public static StrategiesFragment newInstance(LoginSession loginSession) {
        StrategiesFragment f = new StrategiesFragment();
        mSession = loginSession;
        return f;
    }
    public static void setFlag(){
        runned = false;
    }
}