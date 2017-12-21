package com.petrasons.alphaatssystem;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

public class Dialog extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        this.getWindow()
                .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setTitle(getIntent().getStringExtra("title"))
                .setMessage(getIntent().getStringExtra("body"))

                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        finish();
                    }
                });


        AlertDialog alert = builder.create();
        alert.show();



    }
}