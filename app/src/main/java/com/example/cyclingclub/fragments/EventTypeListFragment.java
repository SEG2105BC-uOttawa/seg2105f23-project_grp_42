package com.example.cyclingclub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cyclingclub.R;
import com.example.cyclingclub.EventType;
import com.example.cyclingclub.adapters.EventTypeAdapter;

import java.util.List;

public class EventTypeListFragment extends Fragment {

    private final List<EventType> eventTypeList;

    public EventTypeListFragment(List<EventType> eventTypeList) {
        this.eventTypeList = eventTypeList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_event_type_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EventTypeAdapter adapter = new EventTypeAdapter((FragmentActivity) getContext(), eventTypeList);
        recyclerView.setAdapter(adapter);
    }

}
