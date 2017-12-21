package com.petrasons.alphaatssystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jake on 2017/08/29.
 */

class AccountAdapter extends ArrayAdapter<Account> {
    // View lookup cache
    private static class ViewHolder {
        private TextView account = null;
        private TextView borkerage = null;
        private TextView aggregatePNL = null;
        private TextView purchasingPower = null;
        private TextView unrealisedPNL = null;

    }
    private LoginSession mSession = null;

    public AccountAdapter(Context context, ArrayList<Account> accounts, LoginSession mSession) {
        super(context, R.layout.account, accounts);
        this.mSession = mSession;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Account account = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.account, parent, false);
            viewHolder.account = convertView.findViewById(R.id.account);
            viewHolder.borkerage = convertView.findViewById(R.id.brokerage);
            viewHolder.aggregatePNL = convertView.findViewById(R.id.aggregatePNL);
            viewHolder.purchasingPower = convertView.findViewById(R.id.purchasingPower);
            viewHolder.unrealisedPNL = convertView.findViewById(R.id.unrealisedPNL);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.account.setText(account.getBrokerAccount());
        viewHolder.borkerage.setText(account.getAccountType());
        viewHolder.aggregatePNL.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(account.getAggregatePNL()));
        viewHolder.purchasingPower.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(account.getBalance()));
        viewHolder.unrealisedPNL.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(account.getUnrealisedPNL()));
        // Return the completed view to render on screen
        return convertView;
    }
}
