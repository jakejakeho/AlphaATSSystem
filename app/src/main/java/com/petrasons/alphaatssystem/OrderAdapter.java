package com.petrasons.alphaatssystem;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jake on 2017/09/03.
 */


public class OrderAdapter extends ArrayAdapter<Order> {
    // View lookup cache
    private static class ViewHolder {
        private TextView account = null;
        private TextView product = null;
        private TextView timeFrame = null;
        private TextView size = null;
        private TextView position = null;
        private TextView entry_date = null;
        private TextView exit_date = null;
        private TextView entry_strategy = null;
        private TextView entry_price = null;
        private TextView exit_strategy = null;
        private TextView exit_price = null;
        private TextView pnl = null;
    }
    private LoginSession mSession = null;

    public OrderAdapter(Context context, ArrayList<Order> orders, LoginSession mSession) {
        super(context, R.layout.order, orders);
        this.mSession = mSession;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Order order = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.order, parent, false);
            viewHolder.account = convertView.findViewById(R.id.account);
            viewHolder.product = convertView.findViewById(R.id.product);
            viewHolder.timeFrame = convertView.findViewById(R.id.timeFrame);
            viewHolder.size = convertView.findViewById(R.id.size);
            viewHolder.position = convertView.findViewById(R.id.position);
            viewHolder.entry_date = convertView.findViewById(R.id.entry_date);
            viewHolder.exit_date = convertView.findViewById(R.id.exit_date);
            viewHolder.entry_strategy = convertView.findViewById(R.id.entry_strategy);
            viewHolder.entry_price =  convertView.findViewById(R.id.entry_price);
            viewHolder.exit_strategy = convertView.findViewById(R.id.exit_strategy);
            viewHolder.exit_price = convertView.findViewById(R.id.exit_price);
            viewHolder.pnl = convertView.findViewById(R.id.unrealised_p_u0026l);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.account.setText(order.getBrokerAccount());
        viewHolder.product.setText(order.getProductCode());
        viewHolder.timeFrame.setText("" + order.getBarSize());
        viewHolder.size.setText("" + order.getOrderSize());
        viewHolder.position.setText(order.getPosition());
        viewHolder.entry_date.setText(order.getEntryDate());
        viewHolder.exit_date.setText(order.getExitDate());
        if(order.getEntryStrategy().length() > 0)
            viewHolder.entry_strategy.setText("  " + order.getEntryStrategy() + "  ");
        else
            viewHolder.entry_strategy.setText(order.getEntryStrategy());
        viewHolder.entry_price.setText(order.getEntryPriceType() + " (" + order.getEntryPrice() + ")");
        if(order.getExitStrategy().length() > 0)
            viewHolder.exit_strategy.setText("  " + order.getExitStrategy() + "  ");
        else
            viewHolder.exit_strategy.setText(order.getExitStrategy());
        viewHolder.exit_price.setText(order.getExitPriceType() + " (" + order.getExitPrice() + ")");
        if(order.getPnl() >= 0){
            viewHolder.pnl.setTextColor(Color.parseColor("#006839"));
            viewHolder.pnl.setText("+" + order.getPnl());
        }
        else{
            viewHolder.pnl.setTextColor(Color.parseColor("#FF0000"));
            viewHolder.pnl.setText("+" + order.getPnl());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
