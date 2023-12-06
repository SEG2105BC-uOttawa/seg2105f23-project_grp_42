package com.example.cyclingclub.adapters;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Switch;
import android.widget.TextView;
import com.example.cyclingclub.R;
import com.example.cyclingclub.Registration;

import java.util.List;

public class RegistrationAdapter extends ArrayAdapter<Registration> {
    private int selectedPosition = -1;
    public RegistrationAdapter(Context context, List<Registration> data) {
        super(context, 0, data);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.registration_list, parent, false);
        }

        // Get the data for this position
        Registration reg = getItem(position);

        // Populate other views in the item layout using dataModel
        TextView eventName= itemView.findViewById(R.id.textEventName);
        TextView participant= itemView.findViewById(R.id.textParticipant);

        eventName.setText(reg.getEvent().getId());
        participant.setText(reg.getParticipant().getUsername());


        // Set up the Switch
        Switch switchAccept = itemView.findViewById(R.id.switchAccept);
        Switch switchAward = itemView.findViewById(R.id.switchAward);

        switchAccept.setChecked(reg.isAccepted());
        switchAward.setChecked(reg.isAwarded());

        // Handle Switch state changes if needed
        switchAccept.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update your boolean variable in the data model when the Switch state changes
            reg.setAccepted(isChecked);
        });
        switchAward.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update your boolean variable in the data model when the Switch state changes
            reg.setAwarded(isChecked);
        });



        if (position == selectedPosition) {
            eventName.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
            participant.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
        } else {
            eventName.setTextColor(getContext().getResources().getColor(android.R.color.black));
            participant.setTextColor(getContext().getResources().getColor(android.R.color.black));
        }

        return itemView;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

}