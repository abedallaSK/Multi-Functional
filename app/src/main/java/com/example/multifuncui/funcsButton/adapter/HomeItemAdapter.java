package com.example.multifuncui.funcsButton.adapter;

import static com.example.multifuncui.funcsButton.model.HelperFunc.inFuncClick;
import static com.example.multifuncui.funcsButton.page.FuncActivity.homeSaveData;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multifuncui.R;
import com.example.multifuncui.databinding.HomeFuncsAdapterBinding;
import com.example.multifuncui.funcsButton.model.HomeItemModel;

import java.util.Collections;
import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    private final List<HomeItemModel> items;

    public HomeItemAdapter(List<HomeItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeFuncsAdapterBinding binding = HomeFuncsAdapterBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeItemModel item = items.get(position);
        holder.bind(item);
    }

    @Override
    public final int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final HomeFuncsAdapterBinding binding;
        private boolean status;

        public ViewHolder(HomeFuncsAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public final void bind(HomeItemModel item) {
            binding.funcTitle.setText(itemView.getContext().getString(item.getText()));
            binding.include2.tvhomeIcon.setImageResource(item.getImageResource());
            binding.btInfo.setOnClickListener(view -> { AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(item.getText())
                        .setMessage(item.getDesc())
                        .setPositiveButton(view.getContext().getString(R.string.ok), (dialog, id) -> dialog.dismiss());

                // Show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();});
            SwitchCompat s = binding.switchAthome;
            status = item.isAtHome();
            s.setChecked(status);
            binding.funcTxSwith.setText(status ? itemView.getContext().getString(R.string.remove_from_home) : itemView.getContext().getString(R.string.add_to_home));

            TextView tv = binding.include2.tvhomeName;
            tv.setSelected(true);

            binding.include2.homeItem.setOnClickListener(view -> inFuncClick(item.getCode(), view.getContext()));

            s.setOnCheckedChangeListener((buttonView, isChecked) -> {
                status = isChecked;
                binding.funcTxSwith.setText(status ? itemView.getContext().getString(R.string.remove_from_home) : itemView.getContext().getString(R.string.add_to_home));
                item.setAtHome(isChecked);
            });
        }
    }

    public final void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        homeSaveData.setHomeItemModels(items);
        notifyItemMoved(fromPosition, toPosition);
    }

    public final void onItemDismiss(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }
}
