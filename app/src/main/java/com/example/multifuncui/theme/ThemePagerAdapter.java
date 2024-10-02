package com.example.multifuncui.theme;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multifuncui.R;
import com.example.multifuncui.databinding.ThemeItemBinding;
import com.example.multifuncui.mangment.PrefManager;

import java.util.List;

public class ThemePagerAdapter extends RecyclerView.Adapter<ThemePagerAdapter.ThemeViewHolder> {

    private final List<ThemeModel> themeList;

    public ThemePagerAdapter(List<ThemeModel> themeList) {
        this.themeList = themeList;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use View Binding to inflate the layout
        ThemeItemBinding binding = ThemeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ThemeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        ThemeModel theme = themeList.get(position);
        // Bind the data using View Binding
        holder.bind(theme);
    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }

    public static class ThemeViewHolder extends RecyclerView.ViewHolder {
        private final ThemeItemBinding binding;

        public ThemeViewHolder(ThemeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Method to bind the ThemeModel data to the views
        public void bind(ThemeModel theme) {
            // Set the theme
            Context context = binding.getRoot().getContext();
            binding.itemThemeTitle.setText(theme.title());
          //  context.setTheme(theme.themeId());

            // Set the title and image
            //binding.itemThemeTitle.setText(theme.title());
           // binding.themeItemImageView.setBackgroundResource(R.drawable.profile_background);

            // Update the button text based on the theme's selected state
//            binding.themeItemButton.setText(theme.isSelected() ? context.getString(R.string.select) : context.getString(R.string.selected));
//            binding.themeItemButton.setActivated(theme.isSelected());

            // Create a ContextThemeWrapper for the specified theme
            ContextThemeWrapper themedContext = new ContextThemeWrapper(context, theme.themeId());

            // Set colors for the color boxes
            setColorForView(themedContext, R.attr.colorPrimary, binding.colorPrimaryBox);
            setColorForView(themedContext, R.attr.colorOnPrimary, binding.colorOnPrimaryBox);
            setColorForView(themedContext, R.attr.colorSecondary, binding.colorSecondaryBox);
            setColorForView(themedContext, R.attr.colorOnSecondary, binding.colorOnSecondaryBox);
            setColorForView(themedContext, R.attr.colorBackground, binding.colorBackgroundBox);
//            setColorForView(themedContext, R.attr.customColorOnBackground, binding.colorOnBackgroundBox);
            // Set up the button click listener
//            binding.themeItemButton.setOnClickListener(v -> {
//                // Save the selected theme
//                PrefManager.setUseAlternateTheme(context, theme.themeId());
//            });
        }

        private void setColorForView(Context context, int attrId, View view) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(attrId, typedValue, true);
            int color = typedValue.data;
            view.setBackgroundColor(color);
        }
    }
}
