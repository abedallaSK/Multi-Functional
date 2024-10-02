package com.example.multifuncui.funcsButton.page.fragment;

import static com.example.multifuncui.funcsButton.page.FuncActivity.homeSaveData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.multifuncui.databinding.FragmentFuncAiBinding;
import com.example.multifuncui.databinding.FuncColumnFragmentBinding;

import java.util.Arrays;
import java.util.List;

public class FuncColumnFragment extends Fragment {



    public  FuncColumnFragment() {
        // Required empty public constructor
    }


    public static  FuncColumnFragment newInstance() {
        return new  FuncColumnFragment();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        FuncColumnFragmentBinding binding=FuncColumnFragmentBinding.inflate(getLayoutInflater());
        List<View> linearLayouts = Arrays.asList(
                binding.linearLayout1,
                binding.linearLayout2,
                binding.linearLayout3,
                binding.linearLayout4,
                binding.linearLayout5
        );


        for (int i = 0; i < linearLayouts.size(); i++) {
            int finalI = i;
            if((i+1)==homeSaveData.getColumnsSize())
                linearLayouts.get(i).setSelected(true);
            linearLayouts.get(i).setOnClickListener(v -> {
                v.setSelected(true);
                homeSaveData.setColumnsSize(finalI + 1);
                for (int j = 0; j < linearLayouts.size(); j++) {
                    if (j != finalI) {
                        linearLayouts.get(j).setSelected(false);
                    }
                }
            });
        }

        return binding.getRoot();
    }
}
