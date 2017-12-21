package com.petrasons.alphaatssystem;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jake on 2017/08/29.
 */

public class StrategyAdapter  extends ArrayAdapter<Strategy> {
    // View lookup cache
    private static class ViewHolder {
        TextView account;
        TextView product;
        TextView timeFrame;
        TextView size;
        TextView entryStrategy;
        TextView exitStrategy;
        TextView entryOrder;
        TextView exitOrder;
        ImageView strategy_toggle;
    }
    private LoginSession mSession = null;
    private ArrayList<Strategy> strategies = null;

    public StrategyAdapter(Context context, ArrayList<Strategy> strategies, LoginSession mSession) {
        super(context, R.layout.strategy, strategies);
        this.mSession = mSession;
        this.strategies = strategies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Strategy strategy = getItem(position);
        final int myPosition = position;
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.strategy, parent, false);
            viewHolder.account = convertView.findViewById(R.id.account);
            viewHolder.product =  convertView.findViewById(R.id.product);
            viewHolder.timeFrame =  convertView.findViewById(R.id.timeFrame);
            viewHolder.size = convertView.findViewById(R.id.size);
            viewHolder.entryStrategy = convertView.findViewById(R.id.entry_strategy);
            viewHolder.exitStrategy =  convertView.findViewById(R.id.exit_strategy);
            viewHolder.entryOrder = convertView.findViewById(R.id.entry_order);
            viewHolder.exitOrder = convertView.findViewById(R.id.exit_order);
            viewHolder.strategy_toggle = convertView.findViewById(R.id.strategy_toggle);
            viewHolder.strategy_toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSession.setPosting(true);
                    mSession.getAllStrategies().get(myPosition).setMonitoring();
                    mSession.postData();
                }
            });
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.account.setText(strategy.getBrokerage() + "-" + strategy.getBrokerAccount());
        viewHolder.product.setText(strategy.getProductCode());
        viewHolder.timeFrame.setText(String.valueOf(strategy.getBarSize()));
        viewHolder.size.setText(String.valueOf(strategy.getOrderSize()));
        viewHolder.entryStrategy.setText("  " + strategy.getEntryStrategy() + "  ");
        viewHolder.exitStrategy.setText("  " + strategy.getExitStrategy() + "  ");
        viewHolder.entryOrder.setText(strategy.getEntryPriceType() + " (+-" + strategy.getEntryDifference() + ")");
        viewHolder.exitOrder.setText(strategy.getExitPriceType() + " (+-" + strategy.getExitDifference() + ")");
        setImage(strategy, viewHolder.strategy_toggle);
        // Return the completed view to render on screen
        return convertView;
    }

    protected void setImage(Strategy strategy, ImageView strategy_toggle){
        if(strategy.isMonitoring()){
            strategy_toggle.setImageResource(R.drawable.strategytableon);
        }else{
            strategy_toggle.setImageResource(R.drawable.strategytableoff);
        }
    }
}
