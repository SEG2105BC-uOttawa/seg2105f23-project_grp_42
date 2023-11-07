package com.example.cyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserList extends ArrayAdapter<User> {
    private Activity context;
    List<User> users;

    public UserList(Activity context, List<User> users) {
        super(context, R.layout.user_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.user_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.userName);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.userRole);

        User user = users.get(position);
        textViewName.setText(user.getUsername());
        textViewRole.setText(user.getRole());
        return listViewItem;
    }
}
