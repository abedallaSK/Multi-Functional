package com.example.multifuncui.theme;

import static com.example.multifuncui.mangment.PrefManager.FullScreen;
import static com.example.multifuncui.mangment.PrefManager.getPrimaryColor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.multifuncui.MainActivity;
import com.example.multifuncui.R;
import com.example.multifuncui.databinding.ActivityThemeBinding;
import com.example.multifuncui.mangment.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class ThemeActivity extends AppCompatActivity {

    private List<ThemeModel> themeList;
    private ActivityThemeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullScreen(this, true);
        binding = ActivityThemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int useAlternateTheme = PrefManager.getUseAlternateTheme(this);
        setTheme(useAlternateTheme);

        ViewPager2 viewPager2 = binding.themeViewPager;
        ThemePagerAdapter adapter = getThemePagerAdapter();
        viewPager2.setAdapter(adapter);


        int savedPosition = getIntent().getIntExtra("CURRENT_ITEM", 0); // Default to 0 if not found
        viewPager2.setCurrentItem(savedPosition, false);

        viewPager2.setPageTransformer(new BookPageTransformer());

        // Handle back press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(ThemeActivity.this, MainActivity.class));
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // Page change listener to update button state
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Change the button state based on the selected theme
                boolean isSelected = themeList.get(position).isSelected();
                binding.button3.setClickable(!isSelected);
                binding.button3.setText(isSelected ? getString(R.string.selected) : getString(R.string.select));
            }
        });


        // Button click listener to save the selected theme
        binding.button3.setOnClickListener(v -> {
            int currentPosition = viewPager2.getCurrentItem(); // Save the current item
            ThemeModel currentTheme = themeList.get(currentPosition);

            // Save the selected theme
            PrefManager.setUseAlternateTheme(this, currentTheme.themeId());

            // Update the selection status for the current theme
            ThemeModel updatedTheme = new ThemeModel(currentTheme.title(), currentTheme.themeId(), true);
            binding.button3.setText(getString(R.string.selected));
            themeList.set(currentPosition, updatedTheme); // Update the list with the new theme model

            // Loop through the theme list to set other themes as not selected
            for (int i = 0; i < themeList.size(); i++) {
                if (i != currentPosition) {
                    ThemeModel theme = themeList.get(i);
                    if (theme.isSelected()) { // Check if the theme is currently selected
                        ThemeModel updatedOtherTheme = new ThemeModel(theme.title(), theme.themeId(),  false);
                        themeList.set(i, updatedOtherTheme); // Update the list with the updated theme model

                        // Notify the adapter that this specific item has changed
                        adapter.notifyItemChanged(i);
                    }
                }
            }

            // Notify the adapter about the changed current theme
            adapter.notifyItemChanged(currentPosition); // Notify the adapter for the current theme

            // Refresh the activity to apply the new theme and keep the current item
            refreshActivity(currentPosition);
        });

        int currentMode = PrefManager.getThemeMode2(this);
        String[] themeDisplay = getResources().getStringArray(R.array.theme_options);
        binding.modeChange.setText(themeDisplay[currentMode]);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, themeDisplay);
        binding.modeChange.setAdapter(adapter2);
        binding.modeChange.setThreshold(1);

        binding.modeChange.setOnItemClickListener((parent, view, position, id) -> {
            PrefManager.changeThemeMode(this, (int)position);
        });

    }

    private @NonNull ThemePagerAdapter getThemePagerAdapter() {
        themeList = new ArrayList<>();
        int currentTheme = PrefManager.getUseAlternateTheme(this);

        // Define your themes and set isSelected based on currentTheme
        themeList.add(new ThemeModel(getString(R.string.cherry_blossom_grove), R.style.Base_Theme_MultiFuncUI,
                currentTheme == R.style.Base_Theme_MultiFuncUI));
        themeList.add(new ThemeModel(getString(R.string.emerald_ember), R.style.Base_Theme_BlueMultiFuncUI,
                currentTheme == R.style.Base_Theme_BlueMultiFuncUI));
        themeList.add(new ThemeModel(getString(R.string.cherry_blossom_garden), R.style.Base_Theme_GreenMultiFuncUI,
                currentTheme == R.style.Base_Theme_GreenMultiFuncUI));
        themeList.add(new ThemeModel(getString(R.string.radiant_nature), R.style.Base_Theme_PurpleMultiFuncUI,
                currentTheme == R.style.Base_Theme_PurpleMultiFuncUI));
        themeList.add(new ThemeModel(getString(R.string.fire_oasis), R.style.Base_Theme_RedMultiFuncUI,
                currentTheme == R.style.Base_Theme_RedMultiFuncUI));
        themeList.add(new ThemeModel(getString(R.string.vibrant_harmony), R.style.Base_Theme_YellowMultiFuncUI,
                currentTheme == R.style.Base_Theme_YellowMultiFuncUI));
        themeList.add(new ThemeModel(getString(R.string.cherry_zen), R.style.Base_Theme_DarkMultiFuncUI,
                currentTheme == R.style.Base_Theme_DarkMultiFuncUI));

        // Set up the adapter
        return new ThemePagerAdapter(themeList);
    }
    private void refreshActivity(int currentItem) {
        Intent intent = getIntent(); // Get the current intent
        intent.putExtra("CURRENT_ITEM", currentItem); // Add the current item as an extra
        finish(); // Close the current activity
        startActivity(intent); // Start the activity again
        overridePendingTransition(0, 0); // Disable activity transition animation (optional)
    }


}
