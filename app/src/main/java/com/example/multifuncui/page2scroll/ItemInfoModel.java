package com.example.multifuncui.page2scroll;

import static com.example.multifuncui.mangment.PrefManager.changeImg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.multifuncui.databinding.ItemHomeInfoBinding;

public  record  ItemInfoModel(int title, int subtitle, String info, View.OnClickListener onClickListener) {
    public View getView(Context context) {
            ItemHomeInfoBinding binding = ItemHomeInfoBinding.inflate(LayoutInflater.from(context));
            binding.tvTitleHomeInfo.setText(context.getText(title));
            binding.tvFuncHomeInfo.setText(context.getText(subtitle));
            // changeImg(context,binding.imItemInfoViewPage);
            binding.typeWriterView.animateText(info);
            binding.tvFuncHomeInfo.setOnClickListener(onClickListener);
            return binding.getRoot();
    }

}
