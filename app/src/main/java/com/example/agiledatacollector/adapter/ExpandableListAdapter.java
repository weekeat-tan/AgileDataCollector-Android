package com.example.agiledatacollector.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.agiledatacollector.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChildAttributes;
    private HashMap<String, List<String>> _listDataChildValues;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listDataChildAttributes, HashMap<String, List<String>> listDataChildValues) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChildAttributes = listDataChildAttributes;
        this._listDataChildValues = listDataChildValues;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return -1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childAttribute = this._listDataChildAttributes.get(this._listDataHeader.get(groupPosition)).get(childPosition);
        final String childValue = this._listDataChildValues.get(this._listDataHeader.get(groupPosition)).get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView textViewPolicyAttribute = (TextView) convertView.findViewById(R.id.textViewPolicyAttribute);
        TextView textViewPolicyValue = convertView.findViewById(R.id.textViewPolicyValue);

        textViewPolicyAttribute.setText(childAttribute);
        textViewPolicyValue.setText(childValue);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChildAttributes.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}