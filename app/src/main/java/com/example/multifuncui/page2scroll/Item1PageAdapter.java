package com.example.multifuncui.page2scroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multifuncui.R;
import com.example.multifuncui.databinding.Item1PageViewBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Item1PageAdapter extends RecyclerView.Adapter<Item1PageAdapter.ViewHolder> {

    private static final String TAG = "Item1PageAdapter";
    final List<Item1Model> itemLists;
    private final Context context;

    public Item1PageAdapter(List<Item1Model> itemLists, Context context) {
        if (itemLists == null)
            this.itemLists = new ArrayList<>();
        else
            this.itemLists = new ArrayList<>(itemLists);
        this.context = context;
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Item1PageViewBinding binding = Item1PageViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;

            // Now you can use the screenWidth
            // For example, set the width of imItem1ViewPage to match the screen width
            ViewGroup.LayoutParams params = holder.binding.imItem1ViewPage.getLayoutParams();
            params.width = screenWidth-100;
            holder.binding.imItem1ViewPage.setLayoutParams(params);
        }



        if (itemLists.isEmpty()) {
            holder.binding.tvItem1ViewPage.setText(R.string.textview);
            Picasso.get()
                    .load(R.drawable.ic_launcher_background) // Assuming you have a placeholder image in your resources
                    .into(holder.binding.imItem1ViewPage);
        } else {
            try {
                final Item1Model model = itemLists.get(position%itemLists.size());
                holder.binding.tvItem1ViewPage.setText(model.title());
                int localImageResourceId = R.drawable.loading;

                Picasso.get()
                        .load(model.photo())
                        .error(R.drawable.badge_background) // Optional: Image to display on error
                        .into(new com.squareup.picasso.Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                Bitmap roundedBitmap = getRoundedCornerBitmap(bitmap, 30,holder.binding.prBox);
                                holder.binding.imItem1ViewPage.setImageBitmap(roundedBitmap);
                                holder.binding.tvItem1ViewPage.setTranslationZ(10);

//
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                holder.binding.imItem1ViewPage.setImageDrawable(errorDrawable);
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                holder.binding.imItem1ViewPage.setImageDrawable(placeHolderDrawable);

                            }
                        });



                holder.binding.getRoot().setOnClickListener(view ->
                        Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
                );
            } catch (Exception e) {
                Log.e(TAG, "Error occurred: " + e.getMessage(), e);
            }
        }
    }

    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int radius, ConstraintLayout prBox) {
        // Resize the bitmap to maintain 16:9 aspect ratio
        Bitmap resizedBitmap = getResizedBitmap(bitmap);
      //  Bitmap resizedBitmap = bitmap;

        // Create an output bitmap with the same size as the resized bitmap
        Bitmap output = Bitmap.createBitmap(resizedBitmap.getWidth(), resizedBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a canvas to draw on the output bitmap
        Canvas canvas = new Canvas(output);

        // Create a paint object
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Create a rect with rounded corners
        RectF rectF = new RectF(0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());

        // Draw a rounded rectangle
        canvas.drawRoundRect(rectF, radius, radius, paint);

        // Set the Xfermode to only draw the bitmap where the rounded rectangle is
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Draw the resized bitmap onto the canvas
        canvas.drawBitmap(resizedBitmap, 0, 0, paint);


        return output;
    }

    public Bitmap getResizedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();

        // Calculate the height for a 16:9 aspect ratio
        int newHeight = (int) (width * 9.0 / 16.0);

        // Resize the bitmap to the new dimensions
        return Bitmap.createScaledBitmap(bitmap, width, newHeight, true);
    }

    @Override
    public final int getItemCount() {
        return itemLists.isEmpty() ? 1 :  Integer.MAX_VALUE;
    }

    // Make ViewHolder public to resolve the scope visibility error
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Item1PageViewBinding binding;

        public ViewHolder(@NonNull Item1PageViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
