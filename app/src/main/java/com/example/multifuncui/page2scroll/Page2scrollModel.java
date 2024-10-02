package com.example.multifuncui.page2scroll;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.multifuncui.databinding.ItemHomeBinding;

public class Page2scrollModel implements LifecycleObserver {
    private final long speed; // Speed in seconds
    private final ViewPager2 viewPager2;
    private final ItemHomeBinding binding;
    private final RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter;
    private final Handler sliderHandler = new Handler();
    private final Mode type;


    // Runnable for auto-scrolling
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = (viewPager2.getCurrentItem() + 1) % adapter.getItemCount();
            viewPager2.setCurrentItem(nextItem, true);
            resetAutoSlide(); // Schedule the next auto-scroll
        }
    };

    // Constructor
    public Page2scrollModel(Activity activity, ViewGroup container, int title, int subtitle, long speed, RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter, Mode type, View.OnClickListener onClickListener) {
        if (activity == null || container == null || adapter == null) {
            throw new IllegalArgumentException("Context, container, and adapter cannot be null.");
        }
        this.speed = speed > 0 ? speed : 5; // Default speed if invalid
        this.adapter = adapter;
        this.type = type;


        LayoutInflater inflater = LayoutInflater.from(activity);
        binding = ItemHomeBinding.inflate(inflater, container, false);
        binding.tvTitleHomeItem.setText(binding.getRoot().getContext().getString(title));
        binding.tvFuncHomeItem.setText(binding.getRoot().getContext().getString(subtitle));
        binding.tvFuncHomeItem.setOnClickListener(onClickListener);
        viewPager2 = binding.viewPager2;
        viewPager2.setAdapter(adapter);
        update();
    }

    public void update() {
        CompositePageTransformer compositePageTransformer = getCompositePageTransformer(type);
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                resetAutoSlide();
            }
        });
        configureViewPager();
    }

    private void resetAutoSlide() {
        sliderHandler.removeCallbacks(sliderRunnable);
        sliderHandler.postDelayed(sliderRunnable, speed * 1000);
    }

    private void configureViewPager() {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }


    private static @NonNull CompositePageTransformer getCompositePageTransformer(Mode mode) {
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();

        // Common margin for all modes
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));

        switch (mode) {
            case PageTransformerMode:
                compositePageTransformer.addTransformer((page, position) -> {
                    float r = 1 - Math.abs(position);
                    page.setScaleY(0.85f + r * 0.15f);
                    page.setAlpha(1 - Math.abs(position)); // Fade effect
                });
                break;

            case ScaleTransformer:
                compositePageTransformer.addTransformer((page, position) -> {
                    float scaleFactor = 1 - Math.abs(position); // Scale down when far
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setAlpha(scaleFactor); // Fade based on position
                });
                break;

            case FadeSlideTransformer:
                compositePageTransformer.addTransformer((page, position) -> {
                    float alpha = position < 0 ? 1 + position : 1 - position;
                    page.setAlpha(alpha);
                    page.setTranslationX(-position * page.getWidth() / 2); // Slide effect
                });
                break;

            case DepthTransformer:
                compositePageTransformer.addTransformer((page, position) -> {
                    if (position < -1 || position > 1) {
                        page.setAlpha(0);
                    } else {
                        float alpha = 1 - Math.abs(position);
                        page.setAlpha(alpha);
                        page.setTranslationZ(-Math.abs(position) * 10); // Reduced depth effect
                    }
                });
                break;

            case CombinedTransformer:
                compositePageTransformer.addTransformer((page, position) -> {
                    float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
                    page.setScaleY(scale);
                    page.setAlpha(1 - Math.abs(position));
                    page.setTranslationX(-position * page.getWidth() / 2);
                });
                break;

            case ZoomTransformation:
                compositePageTransformer.addTransformer((page, position) -> {
                    float scaleFactor = Math.max(0.85f, 1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setAlpha(1 - Math.abs(position)); // Fade effect
                });
                break;

            case CarouselTransformation:
                compositePageTransformer.addTransformer((page, position) -> {
                    if (position >= -1 && position <= 1) {
                        page.setRotationY(position * -30); // Rotate based on position
                        page.setTranslationX(-position * page.getWidth() / 2); // Shift horizontally
                    } else {
                        page.setAlpha(0); // Fade out
                    }
                });
                break;

            case FlipTransformation:
                compositePageTransformer.addTransformer((page, position) -> {
                    if (position < -1 || position > 1) {
                        page.setAlpha(0);
                    } else {
                        float scaleFactor = position < 0 ? 1 + position : 1 - position;
                        page.setAlpha(scaleFactor);
                        page.setTranslationX(-position * page.getWidth()); // Slide effect
                        page.setRotationY(position * -180); // Flip effect
                    }
                });
                break;

            case FadeScaleTransformation:
                compositePageTransformer.addTransformer((page, position) -> {
                    float scaleFactor = 1 - Math.abs(position);
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setAlpha(scaleFactor); // Fade based on position
                });
                break;

            case StackTransformation:
                compositePageTransformer.addTransformer((page, position) -> {
                    if (position < -1) { // Off-screen to the left
                        page.setTranslationX(-page.getWidth());
                        page.setAlpha(0);
                    } else if (position <= 1) {
                        page.setTranslationX(-position * page.getWidth() / 2); // Slide effect
                        page.setAlpha(1 - Math.abs(position)); // Fade effect
                    } else { // Off-screen to the right
                        page.setTranslationX(page.getWidth());
                        page.setAlpha(0);
                    }
                });
                break;
        }

        return compositePageTransformer;
    }

    public void onResume() {
        sliderHandler.postDelayed(sliderRunnable,(speed * 1000)); // Adjust based on speed
    }

    public void onStop() {
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    public ItemHomeBinding getView() {
        return binding;
    }
    public enum Mode {
        PageTransformerMode,
        ScaleTransformer,
        FadeSlideTransformer,
        DepthTransformer,
        CombinedTransformer,
        ZoomTransformation,
        CarouselTransformation,
        FlipTransformation,
        FadeScaleTransformation,
        StackTransformation
    }
}
