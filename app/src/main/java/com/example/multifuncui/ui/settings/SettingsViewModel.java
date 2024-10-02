package com.example.multifuncui.ui.settings;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SettingsViewModel extends AndroidViewModel {

    private final MutableLiveData<String> appVersion;
    private final MutableLiveData<Boolean> updateAvailable;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        appVersion = new MutableLiveData<>();
        updateAvailable = new MutableLiveData<>();

        // Initialize app version and check for update
        String currentVersion = fetchAppVersion();
        appVersion.setValue(currentVersion);

        // Simulating fetching latest version (could be fetched from server)
        String latestVersion = "1.2"; // Example version
        updateAvailable.setValue(isUpdateAvailable(currentVersion, latestVersion));
    }

    // Expose the app version to the fragment via LiveData
    public LiveData<String> getAppVersion() {
        return appVersion;
    }

    // Expose whether an update is available
    public LiveData<Boolean> isUpdateAvailable() {
        return updateAvailable;
    }

    // Private method to fetch the app version
    private String fetchAppVersion() {
        try {
            PackageManager packageManager = getApplication().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getApplication().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    // Method to check if an update is available
    private boolean isUpdateAvailable(String currentVersion, String latestVersion) {
        return !currentVersion.equals(latestVersion);
    }
}
