package com.roshan.dev.eventlisitng.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roshan.dev.R;
import com.roshan.dev.beans.EventModel;
import com.roshan.dev.callback.OnItemClickListener;
import com.roshan.dev.databinding.ListItemEventBinding;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    private List<EventModel> eventList;
    private OnItemClickListener<EventModel> itemClickListener;

    public EventListAdapter(@NonNull List<EventModel> eventList) {
        this.eventList = eventList;
    }

    public void setOnItemClickListener(OnItemClickListener<EventModel> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemEventBinding itemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.list_item_event, parent, false);
        return new MyViewHolder(itemBinding);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(eventList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return null != eventList ? eventList.size() : 0;
    }

    public List<EventModel> getItemList() {
        return eventList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ListItemEventBinding binding;

        private MyViewHolder(ListItemEventBinding binding) {
            super(binding.getRoot());
            binding.executePendingBindings();
            this.binding = binding;
        }

        private void bind(EventModel item, OnItemClickListener<EventModel> itemClickListener) {
            binding.setModel(item);
            binding.setCallback(itemClickListener);
        }
    }
}

