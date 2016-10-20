package com.sid.demoapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sid.demoapp.dummy.MenuContent.MenuItem;

import java.util.List;

import static com.sid.demoapp.MainMenuFragment.*;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MenuItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MainMenuRecyclerViewAdapter extends RecyclerView.Adapter<MainMenuRecyclerViewAdapter.ViewHolder> {

    private List<MenuItem> items;
    private final OnListFragmentInteractionListener listener;

    public MainMenuRecyclerViewAdapter(List<MenuItem> items, OnListFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mainmenu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.menuItem = items.get(position);
        holder.name.setText(items.get(position).name);

        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onMainMenuListFragmentInteraction(holder.menuItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<MenuItem> data) {
        items = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View layoutView;
        public final TextView name;
        public MenuItem menuItem;

        public ViewHolder(View view) {
            super(view);
            layoutView = view;
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
