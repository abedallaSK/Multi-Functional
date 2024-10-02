package com.example.multifuncui.funcsButton.page.fragment;

import static kotlinx.coroutines.flow.FlowKt.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.multifuncui.databinding.FragmentFuncListViewBinding;
import com.example.multifuncui.funcsButton.adapter.HomeItemAdapter;
import com.example.multifuncui.funcsButton.model.HomeItemModel;
import com.example.multifuncui.funcsButton.model.enums.Type;
import com.example.multifuncui.funcsButton.page.FuncActivity;

import java.util.ArrayList;
import java.util.List;

public class FuncListViewFragment extends Fragment {

    private static final String HOME_ITEM="home_item";

    private List<HomeItemModel> items;
    private    FragmentFuncListViewBinding binding;

    public FuncListViewFragment() {
    }

    public static FuncListViewFragment newInstance(int position) {
        FuncListViewFragment fragment = new FuncListViewFragment();
        Bundle args = new Bundle();
        args.putInt(HOME_ITEM, position); // Directly store the int
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = FuncActivity.homeSaveData.getHomeItemModels();
        if (getArguments() != null) {
            int position = getArguments().getInt(HOME_ITEM);
            if (position != 0) {
                List<HomeItemModel> filteredItems = new ArrayList<>();
                if (position == 1) {
                    // Filter by isAtHome
                    for (HomeItemModel item : items) {
                        if (item.isAtHome()) {
                            filteredItems.add(item);
                        }
                    }
                }
                else if(position!=-1) {
                    // Filter by Type
                    Type targetType = Type.values()[position - 2];
                    for (HomeItemModel item : items) {
                        if (item.getType() == targetType) {
                            filteredItems.add(item);
                        }
                    }
                }
                if(position!=-1)
                    items = filteredItems;  // Reassign filtered items
                else
                {
                    if(FuncActivity.filteredItems!=null)
                        items=FuncActivity.filteredItems;
                }
            }
        }

    }


    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        binding = FragmentFuncListViewBinding.inflate(getLayoutInflater());
        HomeItemAdapter adapter = new HomeItemAdapter(items);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        return binding.getRoot();
    }
    public void update( List<HomeItemModel> items)
    {
        if(binding!=null) {
            HomeItemAdapter adapter = new HomeItemAdapter(items);

            binding.recyclerView.setAdapter(adapter);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

            ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        }
    }
}