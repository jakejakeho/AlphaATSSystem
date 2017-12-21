package com.petrasons.alphaatssystem;

import  android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SystemToggleFragment extends Fragment {
    private static LoginSession mSession;
    private static Boolean runned = false;
    private TextView systemToggleText = null;
    private ImageView systemToggleImg = null;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_system_toggle_fragment, container, false);
        systemToggleText = (TextView)v.findViewById(R.id.system_on_off_text);
        systemToggleImg = (ImageView)v.findViewById(R.id.system_on_off_img);
        systemToggleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.toogleStatus();
                mSession.postData();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!runned)
        {
            setImage();
            runned = true;
        }
    }

    public static SystemToggleFragment newInstance(LoginSession loginSession) {
        SystemToggleFragment f = new SystemToggleFragment();
        mSession = loginSession;
        return f;
    }

    public static void setFlag(){
        runned = false;
    }

    protected void setImage(){
        if(mSession.isStatus()){
            systemToggleText.setText(Html.fromHtml("SYSTEM<br><font color='#006839'>ON</font>"));
            systemToggleImg.setImageResource(R.drawable.system_on);
        }else{
            systemToggleText.setText(Html.fromHtml("SYSTEM<br><font color='#FF0000'>OFF</font>"));
            systemToggleImg.setImageResource(R.drawable.system_off);
        }
    }
}