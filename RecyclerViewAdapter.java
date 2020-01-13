package com.dreamcode.BREADCRUMBS.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List list;
    private Context context;
    private int layout;
    RecyclerViewAdapter.ReturnView returnView;
    int from;

    public RecyclerViewAdapter(List list, Context context, int layout, RecyclerViewAdapter.ReturnView returnView, int from) {
        this.list = list;
        this.context = context;
        this.layout = layout;
        this.returnView = returnView;
        this.from = from;
    }

    public interface ReturnView {
        void getView(View view, List list, int position, int from);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(layout, viewGroup, false);
        return new ViewHolder(itemView);
    }

    public void notifyData(List list) {
        try {
            this.list = list;
            notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        returnView.getView(viewHolder.v, list, position, from);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        View v;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }
}