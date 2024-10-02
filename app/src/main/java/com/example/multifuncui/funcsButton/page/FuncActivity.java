package com.example.multifuncui.funcsButton.page;

import static com.example.multifuncui.mangment.PrefManager.FullScreen;
import static com.example.multifuncui.mangment.PrefManager.getHomeData;
import static com.example.multifuncui.mangment.PrefManager.saveHomeModel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.multifuncui.MainActivity;
import com.example.multifuncui.R;
import com.example.multifuncui.databinding.ActivityFuncBinding;
import com.example.multifuncui.funcsButton.model.FuncTypeAdapter;
import com.example.multifuncui.funcsButton.model.HomeData;
import com.example.multifuncui.funcsButton.model.HomeItemModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class FuncActivity extends AppCompatActivity {

    private FuncTypeAdapter funcTypeAdapter;
    public static HomeData homeSaveData;
    public static   List<HomeItemModel> filteredItems;
    private   ActivityFuncBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FullScreen(this, true);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
         binding = ActivityFuncBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        filteredItems=null;
        // Load home data
        HomeData data = getHomeData(this);
        homeSaveData = new HomeData(data);

        funcTypeAdapter = new FuncTypeAdapter(this);
        ViewPager2 viewPager2 = binding.viewPager;

        viewPager2.setAdapter(funcTypeAdapter);
        scrollToPosition(1,false);
        TabLayout tabs = binding.tabs;

        // TabLayoutMediator for managing tabs
        new TabLayoutMediator(tabs, viewPager2, (tab, position) ->
                tab.setText(funcTypeAdapter.getPageTitle(this, position))).attach();

        // Horizontal scroll behavior for tabs
        HorizontalScrollView horizontalScrollView = binding.horizontalScroll;
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = tab.view;
                int scrollX = (int) (tabView.getX() - ((float) horizontalScrollView.getWidth() / 2) + ((float) tabView.getWidth() / 2));
                horizontalScrollView.smoothScrollTo(scrollX, 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Set up back pressed callback with save changes prompt
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(FuncActivity.this)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.do_you_want_to_save_changes)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            saveHomeModel(FuncActivity.this, homeSaveData);
                            startActivity(new Intent(FuncActivity.this, MainActivity.class));
                            finish();
                        })
                        .setNegativeButton(R.string.no, (dialog, which) -> {
                            startActivity(new Intent(FuncActivity.this, MainActivity.class));
                            finish();
                        })
                        .show();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // Add TextWatcher for search functionality
        binding.editTextText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need to implement this
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the list as user types
                filterHomeItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need to implement this
            }
        });
    }

    // Function to filter homeSaveData based on search input
    private void filterHomeItems(String query) {
        String lowerCaseQuery = query.toLowerCase();
        List<HomeItemModel> filteredItems = new ArrayList<>();
        for (HomeItemModel item : homeSaveData.getHomeItemModels()) {
            if (getString(item.getText()).toLowerCase().contains(lowerCaseQuery)) {
                filteredItems.add(item);
            }
        }
        if (!filteredItems.equals(FuncActivity.filteredItems)) {
            FuncActivity.filteredItems = filteredItems;
            funcTypeAdapter.update(filteredItems);
            scrollToPosition(0, true);
        }
    }

    public void scrollToPosition(int position,boolean smoothScroll) {
        if (position >= 0 && position < funcTypeAdapter.getItemCount()) {
            binding.viewPager.setCurrentItem(position, smoothScroll);
            funcTypeAdapter.notifyItemChanged(position);

        } else {
            // Handle case where position is out of bounds
            Log.e("FuncActivity", "Position out of bounds");
        }
    }


}
