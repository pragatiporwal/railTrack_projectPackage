package com.example.dell.railtrack;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Userlist extends ArrayAdapter<twentyone_database> {
    private Activity context;
    private List<twentyone_database> userlist;

    public Userlist(Activity context, List<twentyone_database> userlist)
    {
        super(context, R.layout.listlayout, userlist);
         this.context=context;
         this.userlist=userlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater;
        inflater = context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.listlayout,null,true);
        TextView tvuser1=(TextView)listViewItem.findViewById(R.id.tvuser1);
        TextView tvuser2=(TextView)listViewItem.findViewById(R.id.tvuser2);
        twentyone_database user=userlist.get(position);
        tvuser1.setText(user.getScity());
        tvuser2.setText(user.getSaddress());

        return super.getView(position, convertView, parent);
    }
}
