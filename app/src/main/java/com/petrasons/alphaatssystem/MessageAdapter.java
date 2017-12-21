package com.petrasons.alphaatssystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jake on 2017/09/03.
 */


class MessageAdapter extends ArrayAdapter<Message> {
    // View lookup cache
    private static class ViewHolder {
        private TextView messageID = null;
        private TextView messageTime = null;
        private TextView messageDate = null;
        private TextView message = null;
    }
    private LoginSession mSession = null;

    public MessageAdapter(Context context, ArrayList<Message> messages, LoginSession mSession) {
        super(context, R.layout.message, messages);
        this.mSession = mSession;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.message, parent, false);
            viewHolder.messageID = convertView.findViewById(R.id.messageID);
            viewHolder.messageTime = convertView.findViewById(R.id.messageTime);
            viewHolder.messageDate = convertView.findViewById(R.id.messageDate);
            viewHolder.message = convertView.findViewById(R.id.message);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.messageID.setText(" " + (position + 1));
        viewHolder.messageTime.setText(message.getTime());
        viewHolder.messageDate.setText(message.getDate());
        viewHolder.message.setText(message.getMessage());
        // Return the completed view to render on screen
        return convertView;
    }
}
