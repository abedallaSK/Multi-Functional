package com.example.multifuncui.funcsButton.adapter;



import static com.example.multifuncui.funcsButton.model.HelperFunc.inFuncClick;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multifuncui.R;

import com.example.multifuncui.databinding.ItemButtonFuncBinding;
import com.example.multifuncui.funcsButton.model.HomeData;
import com.example.multifuncui.funcsButton.model.HomeItemModel;
import com.example.multifuncui.funcsButton.model.enums.ActionType;
import com.example.multifuncui.funcsButton.model.enums.RoleFunc;
import com.example.multifuncui.funcsButton.model.enums.Type;
import com.example.multifuncui.mangment.PrefManager;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeFuncAdapter extends RecyclerView.Adapter<HomeFuncAdapter.ViewHolder> {

    private final List<HomeItemModel> dataList;
    public final Handler handler = new Handler();
    private final Context context;

    private final HomeData homeSaveData;

    public HomeFuncAdapter(Context context,  HomeData data) {
        List<HomeItemModel> filteredData = data.getHomeItemModels().stream()
                .filter(HomeItemModel::isAtHome).collect(Collectors.toList());
        Set<RoleFunc> newRolesSet = new HashSet<>();
        newRolesSet.add(RoleFunc.ADMIN);
        newRolesSet.add(RoleFunc.USER);
        filteredData.add(new HomeItemModel(R.string.setting, R.drawable.baseline_add_24, R.attr.colorPrimaryDark, ActionType.ACTION_ADD,true, Type.TYPE_MANAGEMENT,R.string.setting,
                newRolesSet));
        this.homeSaveData=data;
        this.dataList=  filteredData;
        this.context=context;
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemButtonFuncBinding binding =   ItemButtonFuncBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(homeSaveData.isAi()) {
            if (position == 0) {
                try {
                    holder.binding.tvhomeIcon.setImageResource(R.drawable.ai_icon);
                    new GuessNumberTask(guessedNumber -> {
                        boolean flag = false;
                        for (HomeItemModel t : homeSaveData.getHomeItemModels())
                            if (t.getId() == guessedNumber) {
                                updateView(holder, t, position);
                                holder.binding.tvAi.setVisibility(View.VISIBLE);
                                flag = true;
                                break;
                            }
                        if (!flag) {
                            updateView(holder, dataList.get(position), position);
                            holder.binding.tvAi.setVisibility(View.VISIBLE);
                        }
                        //TODO fix the info
                    }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"id", "new ArrayList<>(user.getRoles()).get(0).toString()", "1");
                    updateView(holder,  dataList.get(position), position);
                }catch (Exception e)
                {
                    Log.e("HomeFuncAdapter",e.getMessage(),e);
                    updateView(holder,  dataList.get(position), position);
                    holder.binding.tvAi.setVisibility(View.VISIBLE);
                }
            } else
                updateView(holder, dataList.get(position-1), position-1);
        }else
            updateView(holder,  dataList.get(position), position);

    }

    private void updateView(ViewHolder holder, HomeItemModel item,int position) {
        ViewTreeObserver vto = holder.binding.homeItem.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = holder.binding.homeItem.getWidth();
                if (width >= 300) {
                    holder.binding.tvhomeName.setText(item.getText());
                    if(item.getCode()!=ActionType.ACTION_ADD) {
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.binding.tvhomeIcon.getLayoutParams();
                        layoutParams.topMargin = 60;
                        holder.binding.tvhomeIcon.setLayoutParams(layoutParams);
                    }
                }
                holder.binding.tvhomeName.setSelected(true);
                holder.binding.homeItem.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        try {
            holder.binding.tvhomeIcon.setImageResource(item.getImageResource());
        } catch (Exception e) {
            holder.binding.tvhomeIcon.setImageResource(R.drawable.baseline_error_24); // fallback image
            Log.e("ImageError", "Failed to set image resource", e);
        }



        if (position == dataList.size() - 1){

            holder.binding.homeItem.setBackgroundColor(PrefManager.getPrimaryDarkColor(context));

            holder.binding.tvhomeIcon.setImageResource(item.getImageResource());
            holder.binding.tvhomeName.setVisibility(View.GONE);

        }
        ImageView delete = holder.binding.deleteImg;

        delete.setVisibility(View.GONE);

        delete.setOnClickListener(view -> {

            HomeData t = PrefManager.getHomeData(view.getContext());
            List<HomeItemModel> biglist = t.getHomeItemModels();

            for(int j=0;j<biglist.size();j++)
            {


                if(biglist.get(j).getId() == item.getId())
                {
                    biglist.get(j).setAtHome(false);
                    break;
                }
            }
            PrefManager.saveHomeModel(view.getContext(), t);
            setDataList(dataList.indexOf(item));

        });
        holder.binding.homeItem.setOnLongClickListener(view -> {
            if (item.getCode() == ActionType.ACTION_ADD) return true;

            delete.setVisibility(View.VISIBLE);
            animateOnClick(holder.binding.homeItem);
            handler.postDelayed(() ->
                            delete.setVisibility(View.GONE)
                    , 3 * 1000);
            return true;
        });
        holder.binding.homeItem.setOnClickListener(v -> inFuncClick(item.getCode(),context));
    }

    private void animateOnClick(View neumorphCardView) {
        neumorphCardView.setClickable(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        alphaAnimation.setDuration(500);
        neumorphCardView.startAnimation(alphaAnimation);
        new Handler(Looper.getMainLooper()).postDelayed(() ->
                restoreOriginalState(neumorphCardView), 500);
    }

    private void restoreOriginalState(View neumorphCardView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(500);
        neumorphCardView.startAnimation(alphaAnimation);
        neumorphCardView.setClickable(true);
    }

    @Override
    public final int getItemCount() {
        if(homeSaveData.isAi())
            return dataList.size()+1;
        else return dataList.size();
    }


    public final void setDataList(int  position) {
        if (position >= 0 && position < dataList.size()) {
            dataList.remove(position);
            notifyItemRemoved(position+1);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemButtonFuncBinding  binding;
        public ViewHolder(ItemButtonFuncBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
