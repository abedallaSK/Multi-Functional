package com.example.multifuncui.information;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.multifuncui.R;
import com.example.multifuncui.databinding.ItemInfoBinding;
import com.example.multifuncui.mangment.PrefManager;

import java.util.List;

public class InfoAdapter {
    private final List<InfoModel> infoList;
    private final LinearLayout parentLayout;

    public InfoAdapter( List<InfoModel> infoList, LinearLayout parentLayout) {
        this.infoList = infoList;
        this.parentLayout = parentLayout;
    }

    // Method to populate the HorizontalScrollView using ViewBinding
    public void populateList(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (InfoModel info : infoList) {
            // Use ViewBinding to inflate the custom item layout
            ItemInfoBinding binding = ItemInfoBinding.inflate(inflater, parentLayout, false);
            // Set the data to the views using binding

            binding.infoNumberTextView.setSelected(true);

            binding.infoItemHome.setShadowColorDark(R.attr.colorSecondary);
            binding.infoNameTextView.setText(context.getString(info.name()));
            binding.infoNumberTextView.setText(String.valueOf(info.number()));

            // Add the item view to the LinearLayout
            parentLayout.addView(binding.getRoot());
        }
    }
}