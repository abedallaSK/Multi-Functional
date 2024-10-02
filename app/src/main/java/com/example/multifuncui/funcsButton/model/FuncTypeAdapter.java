package com.example.multifuncui.funcsButton.model;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.multifuncui.funcsButton.model.enums.Type;
import com.example.multifuncui.funcsButton.page.fragment.FuncAIFragment;
import com.example.multifuncui.funcsButton.page.fragment.FuncColumnFragment;
import com.example.multifuncui.funcsButton.page.fragment.FuncListViewFragment;

import java.util.List;

public class FuncTypeAdapter extends FragmentStateAdapter {

    private final FuncListViewFragment s;

    public FuncTypeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
       s = FuncListViewFragment.newInstance(-1);
    }


    @NonNull
    @Override
    public final Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> s;
            case 1 -> FuncAIFragment.newInstance();
            case 2 -> FuncColumnFragment.newInstance();
            default -> FuncListViewFragment.newInstance(position - 3);
        };
    }

   public void update(List<HomeItemModel> item)
    {
        s.update(item);
    }
    @Override
    public final int getItemCount() {
        return Type.values().length+5;
    }

    public final String getPageTitle(Context context, int position) {

        return  context.getString( HomeItemModel.getColumnTitle(position));
    }


}
