package com.petrasons.alphaatssystem;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class AccountSummaryFragment extends Fragment {
    private static LoginSession mSession;
    private static Boolean runned = false;
    private static AccountAdapter adapter = null;
    public static View v = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(v == null)
            v = inflater.inflate(R.layout.activity_account_summary_fragment, container, false);
        runned = true;
        // Create the adapter to convert the array to views
        if(adapter == null) {
            adapter = new AccountAdapter(this.getContext(), mSession.getAllAccount(), mSession);
        }
        // Attach the adapter to a ListView
        ListView listView = v.findViewById(R.id.accounts_list);
        listView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!runned)
        {
            adapter.clear();
            adapter.addAll(mSession.getAllAccount());
            adapter.notifyDataSetChanged();
            runned = true;
        }
    }

    public static AccountSummaryFragment newInstance(LoginSession loginSession) {
        AccountSummaryFragment f = new AccountSummaryFragment();
        mSession = loginSession;
        return f;
    }
    public static void setFlag(){
        runned = false;
    }
}