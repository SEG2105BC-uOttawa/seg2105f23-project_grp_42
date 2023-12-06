package com.example.cyclingclub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder> {

    private final List<EventType> eventTypes;
    private final FragmentActivity activity;

    public EventTypeAdapter(FragmentActivity activity, List<EventType> eventTypes) {
        this.activity = activity;
        this.eventTypes = eventTypes;
    }

    @NonNull
    @Override
    public EventTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_type_recycler_row, parent, false);
        return new EventTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeViewHolder holder, int position) {
        EventType eventType = eventTypes.get(position);
        holder.eventTypeNameText.setText(eventType.getTypeName());
        holder.eventTypeDescriptionText.setText(eventType.getDetail());

        holder.optionsButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(activity, v);
            popup.getMenuInflater().inflate(R.menu.user_options_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_delete) {
                    handleEventTypeDeletion(eventType);
                } else if (id == R.id.action_update) {
                    // Handle "Modify" button press
                    handleEventTypeModification(eventType);
                }
                return true;
            });

            popup.show();
        });
    }

    private void handleEventTypeModification(EventType eventType) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.update_event_type_dialog, null);
        dialogBuilder.setView(dialogView);

        EditText editTextTypeName = dialogView.findViewById(R.id.editTextTypeName);
        EditText editTextDetail = dialogView.findViewById(R.id.editTextDescription);

        editTextTypeName.setText(eventType.getTypeName());
        editTextDetail.setText(eventType.getDetail());

        dialogBuilder.setPositiveButton("Update", (dialog, id) -> {
            String typeName = editTextTypeName.getText().toString().trim();
            String detail = editTextDetail.getText().toString().trim();

            // Update the event type
            eventType.setTypeName(typeName);
            eventType.setDetail(detail);
            Administrator.updateEventType(eventType);
        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    private void handleEventTypeDeletion(EventType eventType) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(activity);
        dialogBuilder.setTitle("Confirm Deletion");
        dialogBuilder.setMessage("Are you sure you want to delete this event type? This action cannot be undone.");

        dialogBuilder.setPositiveButton("Confirm", (dialog, id) -> {
            Administrator.deleteEventType(eventType)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DynamicToast.makeSuccess(activity, "Event type deleted successfully").show();
                        } else {
                            DynamicToast.make(activity, "Failed to delete event type").show();
                        }
                    });
        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    @Override
    public int getItemCount() {
        return eventTypes.size();
    }

    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        View optionsButton;
        TextView eventTypeNameText;
        TextView eventTypeDescriptionText;

        public EventTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTypeNameText = itemView.findViewById(R.id.edit_text_type_name);
            eventTypeDescriptionText = itemView.findViewById(R.id.edit_text_description);
            optionsButton = itemView.findViewById(R.id.options_button);
        }
    }
}
