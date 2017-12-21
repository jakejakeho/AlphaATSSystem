package com.petrasons.alphaatssystem;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class MessageFragment extends Fragment {
    private static LoginSession mSession;
    private static Boolean runned = false;
    private static MessageAdapter adapter = null;
    public static View v = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(v == null)
            v = inflater.inflate(R.layout.activity_message_fragment, container, false);
        runned = true;
        // Create the adapter to convert the array to views
        if(adapter == null) {
            adapter = new MessageAdapter(this.getContext(), mSession.getAllMessage(), mSession);
        }
        // Attach the adapter to a ListView
        ListView messagesList = v.findViewById(R.id.messages_list);
        messagesList.setAdapter(adapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!runned)
        {
            adapter.clear();
            adapter.addAll(mSession.getAllMessage());
            adapter.notifyDataSetChanged();
            runned = true;
        }
    }

    public static MessageFragment newInstance(LoginSession loginSession) {
        MessageFragment f = new MessageFragment();
        mSession = loginSession;
        return f;
    }
    public static void setFlag(){
        runned = false;
    }
}