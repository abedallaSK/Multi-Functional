package com.example.multifuncui.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multifuncui.funcsButton.model.HomeData;
import com.example.multifuncui.mangment.PrefManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<HomeData> homeData;
    private final ExecutorService executorService;

    public LiveData<HomeData> getHomeData() {
        return homeData;
    }

    public HomeViewModel() {
        homeData = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor(); // Reuse this instance
    }

    public void fetchHomeData(Context context) {
        executorService.execute(() -> {
            try {
                HomeData data = PrefManager.getHomeData(context);
                homeData.postValue(data); // Update LiveData on the main thread
            } catch (Exception e) {
                // Handle exceptions, maybe log them or set an error state
                e.printStackTrace(); // Replace this with proper error handling if needed
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown(); // Shut down the executor to prevent memory leaks
    }
}
