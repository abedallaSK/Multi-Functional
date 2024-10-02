package com.example.multifuncui.funcsButton.page.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multifuncui.funcsButton.adapter.HomeItemAdapter;


public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final HomeItemAdapter mAdapter;

    public MyItemTouchHelperCallback(HomeItemAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public final int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // for drag-and-drop
        int swipeFlags = 0;// for swipe-to-dismiss
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public final boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public final void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}

