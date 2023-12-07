package com.example.cyclingclub.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cyclingclub.R;
import com.example.cyclingclub.EventType;
import com.example.cyclingclub.User;
import com.example.cyclingclub.adapters.EventTypeAdapter;

import java.util.List;

public class EventTypeListFragment extends Fragment {

    private final List<EventType> eventTypeList;

    public EventTypeListFragment(List<EventType> eventTypeList) {
        this.eventTypeList = eventTypeList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_type_management, container, false);

        User user = (User) getActivity().getIntent().getSerializableExtra("user");

        if (!user.getRole().equals("Administrator")) {
            Button newEventTypeButton = view.findViewById(R.id.btnNewEventType);
            newEventTypeButton.setEnabled(false);
            newEventTypeButton.setBackgroundColor(Color.LTGRAY);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        User user = (User) getActivity().getIntent().getSerializableExtra("user");
        EventTypeAdapter adapter = new EventTypeAdapter((FragmentActivity) getContext(), eventTypeList, user);
        recyclerView.setAdapter(adapter);
    }

}
