package com.dreamcode.BREADCRUMBS.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> list = new ArrayList();
    private int headLayout, childLayout;
    HashMap<String, List> listDataChild;
    private Context context;
    ReturnView returnView;
    int from;

    public interface ReturnView {
        void getAdapterChildView(View view, Object objects, int from);

        void getAdapterHeaderView(View view, boolean isExpanded, int position, Object objects, int from);

        void getAdapterHeaderView(View view, boolean isExpanded, Object objects, int from);
    }

    public ExpandableListAdapter(ArrayList<String> list, HashMap<String, List> listDataChild, Context context, int headLayout, int childLayout, ReturnView returnView, int from) {
        this.list = list;
        this.context = context;
        this.listDataChild = listDataChild;
        this.headLayout = headLayout;
        this.childLayout = childLayout;
        this.returnView = returnView;
        this.from = from;
    }

    public void notifyData(ArrayList<String> list, HashMap<String, List> listDataChild) {
        this.list = list;
        this.listDataChild = listDataChild;
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataChild.get(list.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(childLayout, null);
        }

        returnView.getAdapterChildView(convertView, getChild(groupPosition, childPosition), from);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(headLayout, null);
        }
        returnView.getAdapterHeaderView(convertView, isExpanded, list.get(groupPosition), from);
        returnView.getAdapterHeaderView(convertView, isExpanded, groupPosition, list.get(groupPosition), from);
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