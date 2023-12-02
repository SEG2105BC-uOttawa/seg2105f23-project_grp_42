package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

//import java.util.Collections;

public class EventManagement extends AppCompatActivity {


   // private Spinner dropdownType ;

    private ListView listViewEvents;
    public static List<Event> events;
    public static List<String> eventsList;
    private User user;


   // private DatabaseReference databaseEvents;

    //private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("user");

        setContentView(R.layout.activity_event_management);

        listViewEvents = (ListView) findViewById(R.id.listEvents);

       // databaseEvents = FirebaseDatabase.getInstance().getReference("Events1");




        events = new ArrayList<Event>();
        eventsList = new ArrayList<String>();


        ArrayAdapter<Event> eventAdapter = new ArrayAdapter<Event>(
                this,
                android.R.layout.simple_list_item_2, // Built-in layout for a two-line list itemme
                android.R.id.text1, // ID of the TextView for the name
                events
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Customize the appearance of the list item
                View view = super.getView(position, convertView, parent);

                // Get the Person object for this position
                Event ev = events.get(position);

                // Find the TextViews in the layout
                TextView TextView1 = view.findViewById(android.R.id.text1);
                TextView TextView2 = view.findViewById(android.R.id.text2);

                // Set the name and age in the TextViews
                TextView1.setText("Name:"+ev.getId()+ "     Type:"+ev.getType());
                TextView2.setText("Region:"+ev.getRegion()+"    Date:"+ev.getDate());

                return view;
            }
        };

        listViewEvents.setAdapter(eventAdapter);

        DatabaseReference  databaseEvents=Administrator.getEventDB();

        Query query;

        if(user.getRole().equals("cycling club")){
            query = databaseEvents.orderByChild("username").equalTo(user.getUsername());
        }else{
            query = databaseEvents;
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                eventsList.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Event event = postSnapShot.getValue(Event.class);
                    events.add(event);
                    eventsList.add(event.getId()+ " : "+ event.getType()+" : "+event.getDate());
                }
                //ArrayAdapter<String> itemsAdapter =
                //        new ArrayAdapter<String>(EventManagement.this, R.layout.item_view, R.id.textViewItem,eventsList);

                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error querying the database: " + databaseError.getMessage());
            }
        });




        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showUpdateDeleteDialog(event);
                return true;
            }
        };
        listViewEvents.setOnItemLongClickListener(longClickListener);


        Button buttonNewEvent = (Button) findViewById(R.id.btnAddEvent);
        if (user.getRole().equals("Administrator") || user.getRole().equals("participant")) {
            buttonNewEvent.setEnabled(false);
        }


    }



    @Override
    protected void onStart() {
        super.onStart();


    }




    private void showUpdateDeleteDialog(Event event) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_detail, null);
        dialogBuilder.setView(dialogView);

        EditText editEventId = (EditText) dialogView.findViewById(R.id.editEventId);
        EditText editEventType = (EditText) dialogView.findViewById(R.id.editEventType);
        EditText editEventRegion = (EditText) dialogView.findViewById(R.id.editEventRegion);
        EditText editEventDate = (EditText) dialogView.findViewById(R.id.editEventDate);
        EditText editEventRoute = (EditText) dialogView.findViewById(R.id.editEventRoute);
        EditText editEventDistance = (EditText) dialogView.findViewById(R.id.editEventDistance);
        EditText editEventElevation = (EditText) dialogView.findViewById(R.id.editEventElevation);
        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinnerLevel);
        EditText editEventFee = (EditText) dialogView.findViewById(R.id.editEventFee);
        EditText editEventLimit = (EditText) dialogView.findViewById(R.id.editEventLimit);

        editEventId.setText(event.getId());
        editEventRegion.setText(event.getRegion());
        editEventDate.setText(event.getDate());
        editEventRoute.setText(event.getRoute());


        editEventDistance.setText(Integer.toString(event.getDistance()));
        editEventElevation.setText(Integer.toString(event.getElevation()));
        editEventFee.setText(Double.toString(event.getFee()));
        editEventLimit.setText(Integer.toString(event.getLimit()));


        Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEventUpdate);
        Button buttonDelete = (Button) dialogView.findViewById(R.id.btnEventDelete);
        Button buttonRegister = (Button) dialogView.findViewById(R.id.btnRegister);

        if (user.getRole().equals("Administrator") ) {
            buttonUpdate.setEnabled(false);
            buttonRegister.setEnabled(false);
        }

        if (user.getRole().equals("cycling club") ) {
            buttonRegister.setEnabled(false);
        }

        if (user.getRole().equals("participant")) {
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }


        dialogBuilder.setTitle("Update current Event");
        editEventType.setText(event.getType());
        TextView eventTypeDetail = (TextView) dialogView.findViewById(R.id.eventTypeDetail);
        eventTypeDetail.setText(event.getDetail());

        final AlertDialog b = dialogBuilder.create();
        // Create an ArrayAdapter with numeric values from 1 to 5
        if (spinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    b.getContext(),
                    R.array.number_array,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(event.getLevel()-1);
        }

        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String  key=event.getKey();
                String id = editEventId.getText().toString().trim();
                String type = editEventType.getText().toString().trim();
                String region = editEventRegion.getText().toString().trim();
                String date = editEventDate.getText().toString().trim();
                String route = editEventRoute.getText().toString().trim();

                String distance = editEventDistance.getText().toString().trim();
                String elevation = editEventElevation.getText().toString().trim();
                int level = Integer.parseInt( spinner.getSelectedItem().toString());
                String fee = editEventFee.getText().toString().trim();
                String limit = editEventLimit.getText().toString().trim();


                 String message=validateInput(id,route,region,date,distance,elevation,fee,limit);
                 if(message.equals("")){


                     event.setId(id);
                    event.setType(type);
                    event.setRegion(region);
                    event.setRoute(route);
                    event.setDate(date);
                    event.setDistance(Integer.parseInt(distance));
                    event.setElevation(Integer.parseInt(elevation));
                    event.setLevel(level);
                    event.setFee(Double.parseDouble(fee));
                    event.setLimit(Integer.parseInt(limit));

                    event.setDetail(event.getDetail());
                    Administrator.updateEvent(event);
                    b.dismiss();

                }
                else{
                   displayPopupMessage(message,view);
                }

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Administrator.removeEvent(event);

                b.dismiss();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Registration");
                Registration reg = new Registration(event,user);

                String key = dref.push().getKey();
                reg.setKey(key);
                dref.child(key).setValue(reg);

                b.dismiss();
            }
        });

    }


    public void onClickAddEvent(View view) {

            Intent intent = new Intent(getApplicationContext(), EventTypeManagement.class);
            intent.putExtra("user", user);
            startActivity (intent);

    }


    private void displayPopupMessage(String message, View anchorView) {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(Color.RED);

        PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(textView);
        popupWindow.showAsDropDown(anchorView, 10, 0);
    }


    private String validateInput(String id,String route,String region,String date,String distance,String elevation,String fee,String limit ) {
        InputValidator validator = InputValidator.getInstance();

        String message="";


        /* Validate event type */
        if (!validator.isValidString(id)) { message= message+ "ID must start with letter and can not be blank.";}
        if (!validator.isValidString(route)) { message= message+ "Route must start with letter and can not be blank.";}
        if (!validator.isValidString(region)) { message= message+ "Region must start with letter and can not be blank.";}
        if (!validator.isValidDate(date)) { message= message+ "Date must be in format YYYY-MM-DD.";}
        if (!validator.isValidNumber(distance)) { message= message+ "Distance must be a number.";}
        if (!validator.isValidNumber(elevation)) { message= message+ "Elevation must be a number.";}
        // if (!validator.isValidNumber(level)) { message= message+ "Level must be a number";}
        if (!validator.isValidNumber(fee)) { message= message+ "Fee must be a number.";}
        if (!validator.isValidNumber(limit)) { message= message+ "Limit must be a number.";}


        return message;
    }

    public static List<Event> getEvents(){
        return events;
    }

    public static List<String> getEventsList(){
        return eventsList;
    }

}