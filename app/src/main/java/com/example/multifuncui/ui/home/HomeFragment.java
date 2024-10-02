package com.example.multifuncui.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multifuncui.MainActivity;
import com.example.multifuncui.R;
import com.example.multifuncui.User;
import com.example.multifuncui.databinding.FragmentHomeBinding;
import com.example.multifuncui.funcsButton.adapter.CustomGridLayoutManager;
import com.example.multifuncui.funcsButton.adapter.HomeFuncAdapter;
import com.example.multifuncui.funcsButton.model.HomeData;
import com.example.multifuncui.help.CircleTransform;
import com.example.multifuncui.information.ListOfInfo;
import com.example.multifuncui.page2scroll.Item1Model;
import com.example.multifuncui.page2scroll.Item1PageAdapter;
import com.example.multifuncui.page2scroll.ItemInfoModel;
import com.example.multifuncui.page2scroll.Page2scrollModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeData previousData;
    private Page2scrollModel page2scrollModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        // Load image using Picasso
        Picasso.get().load("https://images.rawpixel.com/image_png_800/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIyLTA4L2pvYjEwMzQtZWxlbWVudC0wNy00MDMucG5n.png")
                .transform(new CircleTransform())
                .into(binding.imageView);

        // Fetch and set up ViewPager
        homeViewModel.fetchHomeData(requireContext());
        homeViewModel.getHomeData().observe(getViewLifecycleOwner(), homeData -> {
            if (homeData != null) {
                setupViewPager();
                FuncButtonUpdate(homeData);
            }
        });

        setupInfoList();
        setupUserName();

        return binding.getRoot();
    }

    private void setupViewPager() {
        List<Item1Model> item1Models = getItem1Models();
        Item1PageAdapter adapter = new Item1PageAdapter(item1Models, requireContext());

        // Initialize Page2ScrollModel with a mode and listener
        page2scrollModel = new Page2scrollModel(requireActivity(), binding.liHomeViewP2, R.string.zoom_transformation, R.string.see_all, 10, adapter,
                Page2scrollModel.Mode.ZoomTransformation,
                v -> Toast.makeText(requireContext(), "TT", Toast.LENGTH_SHORT).show());

        binding.liHomeViewP2.addView(page2scrollModel.getView().getRoot());
        getLifecycle().addObserver(page2scrollModel);
    }

    private void setupInfoList() {
        // Populate ListOfInfo
        ListOfInfo listOfInfo = new ListOfInfo(binding.infoList);
        listOfInfo.addInfo(R.string.into1, 12);
        listOfInfo.addInfo(R.string.info2, 4);
        listOfInfo.addInfo(R.string.info3, 789);
        listOfInfo.addInfo(R.string.info4, 321);
        listOfInfo.populateList(requireActivity());

        // Add new info to liHomeViewP1
        binding.liHomeViewP1.addView(new ItemInfoModel(
                R.string.notifications, R.string.see_all, "This is test for new info",
                v -> Toast.makeText(requireContext(), "TT", Toast.LENGTH_SHORT).show()
        ).getView(requireContext()));
    }

    private void setupUserName() {
        try {
            User user = ((MainActivity) requireActivity()).getUser();
            if (user != null) {
                binding.tvName.setText(user.getUsername());
            } else {
                binding.tvName.setText(R.string.user_not_found);
            }
        } catch (ClassCastException e) {
            binding.tvName.setText(R.string.invalid_activixty);
        } catch (Exception e) {
            binding.tvName.setText(R.string.an_error_occurred);
        }
    }

    private void FuncButtonUpdate(HomeData newData) {
        if (newData != null && !newData.equals(previousData)) {
            HomeFuncAdapter adapter = new HomeFuncAdapter(requireContext(), newData);
            RecyclerView recyclerView = binding.recyclerView;
            recyclerView.setLayoutManager(new CustomGridLayoutManager(requireContext(), newData.getColumnsSize()));
            recyclerView.setAdapter(adapter);
            previousData = newData;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (page2scrollModel != null) {
            page2scrollModel.onStop();
        }
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (page2scrollModel != null) {
            page2scrollModel.onResume();
        }
    }

    private  @NonNull List<Item1Model> getItem1Models() {
        List<Item1Model> item1Models = new ArrayList<>();
        item1Models.add(new Item1Model(getString(R.string.this_is_test_for_data1), "https://img.freepik.com/premium-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_1033124-10.jpg"));
        item1Models.add(new Item1Model(getString(R.string.this_is_test_for_another_data), "https://img.freepik.com/free-photo/beautiful-tree-middle-field-covered-with-grass-with-tree-line-background_181624-29267.jpg"));
        return item1Models;
    }
}
