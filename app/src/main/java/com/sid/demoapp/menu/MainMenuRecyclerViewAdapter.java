package com.sid.demoapp.menu;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sid.demoapp.R;
import com.sid.demoapp.menu.MenuContent.MenuItem;

import java.util.Collections;
import java.util.List;

import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.sid.demoapp.menu.MainMenuFragment.OnListFragmentInteractionListener;


public class MainMenuRecyclerViewAdapter extends RecyclerView.Adapter<MainMenuRecyclerViewAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private List<MenuItem> items;
    private final OnListFragmentInteractionListener listener;
    private final OnStartDragListener dragListener;

    public MainMenuRecyclerViewAdapter(
            List<MenuItem> items,
            OnListFragmentInteractionListener listener,
            OnStartDragListener dragListener) {
        this.items = items;
        this.listener = listener;
        this.dragListener = dragListener;
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
        holder.handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(holder);
                }
                return false;
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        /* no-op */
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View layoutView;
        public final TextView name;
        public final ImageView handle;
        public MenuItem menuItem;

        public ViewHolder(View view) {
            super(view);
            layoutView = view;
            name = (TextView) view.findViewById(R.id.name);
            handle = (ImageView) view.findViewById(R.id.handle);
        }
    }
}
