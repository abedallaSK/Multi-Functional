package com.example.multifuncui.funcsButton.page.fragment;



import static com.example.multifuncui.funcsButton.page.FuncActivity.homeSaveData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.multifuncui.databinding.FragmentFuncAiBinding;

public class FuncAIFragment extends Fragment {
    private  boolean ai;
    public FuncAIFragment() {
        // Required empty public constructor
    }
    public static FuncAIFragment newInstance() {
        return new FuncAIFragment();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        FragmentFuncAiBinding binding= FragmentFuncAiBinding.inflate(getLayoutInflater());
        ai=homeSaveData.isAi();
        binding.switch1.setChecked(ai);
        binding.switch1.setOnClickListener(view -> {
            ai=!ai;
            binding.switch1.setChecked(ai);
            homeSaveData.setAi(ai);
        });

        return binding.getRoot();
    }
}
