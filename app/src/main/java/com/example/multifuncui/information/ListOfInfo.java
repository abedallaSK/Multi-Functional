package com.example.multifuncui.information;

import static com.example.multifuncui.mangment.PrefManager.changeImg;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.multifuncui.databinding.InfoListBinding;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ListOfInfo {
    @Setter
    @Getter
    private List<InfoModel> infoModelList;
    @Setter
    private final InfoListBinding infoListBinding;// To hold reference to the activity

    public ListOfInfo( InfoListBinding infoListBinding) {
        this.infoListBinding = infoListBinding;

        this.infoModelList = new ArrayList<>(); // Initialize the list

    }

    public void addInfo(InfoModel infoModel) {
        infoModelList.add(infoModel);
    }

    public void addInfo(int name, int number) {
        infoModelList.add(new InfoModel(name, number));
    }

    public void populateList(Activity activity) {

        if(infoModelList.isEmpty())
        {
            infoListBinding.imageView2.setVisibility(View.GONE);
            infoListBinding.horizontalScrollView.setVisibility(View.GONE);
            infoListBinding.view5.setVisibility(View.GONE);
            return;
        }

      //  Drawable drawable = ContextCompat.getDrawable(activity, img);
     //   infoListBinding.imageView2.setImageDrawable(drawable);
        changeImg(activity,   infoListBinding.imageView2);
        InfoAdapter adapter = new InfoAdapter( infoModelList, infoListBinding.liInfoHome);
        adapter.populateList(activity);

        LinearLayout linearLayout = infoListBinding.liInfoHome; // Getting the LinearLayout from binding

        // Add global layout listener to adjust view width once layout is rendered
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to avoid being called multiple times
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Get the current width of linearLayout and set it to the horizontalScrollView
                int newWidth = linearLayout.getWidth() + 16;

                // Update the LayoutParams of the horizontalScrollView
                ViewGroup.LayoutParams params = infoListBinding.horizontalScrollView.getLayoutParams();
                params.width = newWidth;
                infoListBinding.horizontalScrollView.setLayoutParams(params);
            }
        });
    }
}
