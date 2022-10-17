package com.dmitri.mobileclientservice.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dmitri.mobileclientservice.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserSpinnerAdapter extends ArrayAdapter<User> {

    private static final String TAG = "UserSpinnerAdapter";

    private Context context;
    private List<User> users;

    public UserSpinnerAdapter(Context context, int textViewResourceId,
                              List<User> users) {
        super(context, textViewResourceId, users);
        this.context = context;
        this.users = (users != null) ? users : new ArrayList<>();
    }

    @Override
    public int getCount(){
        return users.size();
    }

    @Override
    public User getItem(int position){
        return users.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(users.get(position).getUser());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(users.get(position).getUser());

        return label;
    }
}