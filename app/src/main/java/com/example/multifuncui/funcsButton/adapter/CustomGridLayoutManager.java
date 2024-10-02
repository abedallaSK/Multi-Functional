package com.example.multifuncui.funcsButton.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomGridLayoutManager extends GridLayoutManager {


    public CustomGridLayoutManager(Context context, int columns) {
        super(context,  columns, GridLayoutManager.VERTICAL, false);
    }

    @Override
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("RecyclerView", "IndexOutOfBoundsException in RecyclerView happens.");
        }
    }

    @Override
    public boolean canScrollVertically() {
        // Return false to disable vertical scrolling
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        // Return false to disable horizontal scrolling (optional)
        return false;
    }

    @Override
    public final boolean supportsPredictiveItemAnimations() {
        return false;
    }
}