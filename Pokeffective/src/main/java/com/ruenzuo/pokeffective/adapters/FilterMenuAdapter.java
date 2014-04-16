package com.ruenzuo.pokeffective.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.FilterOption;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class FilterMenuAdapter extends ArrayAdapter<FilterOption> {

    private int resourceId;

    public FilterMenuAdapter(Context context, int resource) {
        super(context, resource);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(resourceId, null);
        }
        FilterOption option = getItem(position);
        TextView txtViewFilterOptionLabel = (TextView) convertView.findViewById(R.id.txtViewFilterOptionLabel);
        txtViewFilterOptionLabel.setText(option.getLabel());
        TextView txtViewFilterOptionValue = (TextView) convertView.findViewById(R.id.txtViewFilterOptionValue);
        if (option.getValue() != null) {
            txtViewFilterOptionValue.setText(option.getLabel());
        }
        else {
            txtViewFilterOptionValue.setText("None");
        }
        return convertView;
    }

}
