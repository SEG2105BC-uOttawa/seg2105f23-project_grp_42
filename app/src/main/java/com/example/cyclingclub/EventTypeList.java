package com.example.cyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventTypeList extends ArrayAdapter<EventType> {
    private Activity context;
    List<EventType> eventTypes;
    private int selectedPosition = -1;

    public EventTypeList(Activity context, List<EventType> eventTypes) {
            super(context, R.layout.event_type_list, eventTypes);
        this.context = context;
        this.eventTypes = eventTypes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.event_type_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.eventName);
        TextView textViewNumber = (TextView) listViewItem.findViewById(R.id.eventNumber);

        EventType eventType = eventTypes.get(position);
        textViewName.setText(eventType.getTypeName());
        textViewNumber.setText(eventType.getDetail());

        if (position == selectedPosition) {
            textViewName.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
            textViewNumber.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
        } else {
            textViewName.setTextColor(getContext().getResources().getColor(android.R.color.black));
            textViewNumber.setTextColor(getContext().getResources().getColor(android.R.color.black));
        }


        return listViewItem;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

}