package com.example.cyclingclub;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class RegistrationAdapter extends ArrayAdapter<Registration> {

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

        switchAccept.setChecked(reg.getAccepted());
        switchAward.setChecked(reg.getAwarded());

        // Handle Switch state changes if needed
        switchAccept.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update your boolean variable in the data model when the Switch state changes
            reg.setAccepted(isChecked);
        });
        switchAward.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update your boolean variable in the data model when the Switch state changes
            reg.setAwarded(isChecked);
        });

        return itemView;
    }
}