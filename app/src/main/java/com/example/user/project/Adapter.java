package com.example.user.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 08.05.2018.
 */

public class Adapter extends ArrayAdapter<Currency> {

    private ArrayList<Currency> dataSet;
    Context mContext;

    public Adapter(ArrayList<Currency> data, Context context) {
        super(context, R.layout.list, data);
        this.dataSet = data;
        this.mContext=context;
    }

    public Adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    private static class ViewHolder {
        TextView text1;
        TextView text2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Currency currency = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list, parent, false);
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.text2 = (TextView) convertView.findViewById(R.id.textView3);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        return convertView;
    }
}

