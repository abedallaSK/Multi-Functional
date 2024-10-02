package com.example.multifuncui.theme;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class BookPageTransformer implements ViewPager2.PageTransformer {

    private static final float MAX_ROTATION = 15f; // Maximum rotation angle

    @Override
    public void transformPage(@NonNull View page, float position) {
        // Position -1 is left off-screen, 0 is currently centered, 1 is right off-screen
        if (position < -1) {
            // This page is way off-screen to the left
            page.setAlpha(0);
        } else if (position <= 0) {
            // This page is moving out to the left
            page.setAlpha(1 + position); // Fade out
            page.setTranslationX(0);
            page.setRotationY(MAX_ROTATION * position); // Rotate to the left
        } else if (position <= 1) {
            // This page is moving in from the right
            page.setAlpha(1 - position); // Fade out
            page.setTranslationX(0);
            page.setRotationY(-MAX_ROTATION * position); // Rotate to the right
        } else {
            // This page is way off-screen to the right
            page.setAlpha(0);
        }

        // Set the scale of the page based on its position
        float scaleFactor = position < 0 ? 1 + position : 1 - position;
        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);
    }
}
