package com.example.multifuncui.mangment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.multifuncui.funcsButton.model.HelperFunc.defFuncHome;
import static com.example.multifuncui.funcsButton.model.HelperFunc.defHomeColumnsSize;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.multifuncui.R;
import com.example.multifuncui.StartActivity;
import com.example.multifuncui.funcsButton.model.HomeData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class PrefManager {

    private static final String SHARED_PREFERENCES_KEY = "your_shared_preferences_key";
    private static final String DATA_KEY = "data_key";

    public static HomeData getHomeData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        String dataJson = preferences.getString(DATA_KEY, null);
        HomeData homeSaveData = new HomeData(new ArrayList<>(), 0,true);

        if (dataJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<HomeData>() {}.getType();
            homeSaveData = gson.fromJson(dataJson, type);
           // getCurrentLanguage(context);
        }

        if (homeSaveData.getHomeItemModels() == null || homeSaveData.getHomeItemModels().isEmpty()) {
            homeSaveData.setHomeItemModels( defFuncHome(context));
            homeSaveData.setColumnsSize(defHomeColumnsSize());
        }

        return homeSaveData;
    }
    public static String getCurrentLanguage(Context context) {
        Configuration configuration =context.getResources().getConfiguration();
        return configuration.getLocales().get(0).getLanguage();
    }

    private static final String PREFERENCES_NAME = "your_preferences_name";
    private static final String KEY_USE_ALTERNATE_THEME = "useAlternateTheme";

    public static int getUseAlternateTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        return preferences.getInt(KEY_USE_ALTERNATE_THEME, R.style.Base_Theme_MultiFuncUI);
    }

    public static void setUseAlternateTheme(Context context, int value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_USE_ALTERNATE_THEME, value);
        editor.apply();
    }
    public static int getPrimaryColor(Context context) {
        // Resolve the primary color from the theme
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data; // The resolved color
    }
    public static int getPrimaryDarkColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data; // The resolved color
    }


    public static void saveHomeModel(Context context, HomeData homeSaveData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(homeSaveData);
        editor.putString(DATA_KEY, json);
        editor.apply();
    }


    private static final String PREFS_NAME = "ThemePrefs";
    private static final String KEY_THEME_MODE = "theme_mode";
    public static final int MODE_AUTO = 0;
    public static final int MODE_LIGHT = 1;
    public static final int MODE_DARK = 2;

    public static void FullScreen(Activity activity, boolean isFullView){
        if(isFullView) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(getPrimaryColor(activity));
        }
        //activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        // activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int useAlternateTheme =  getUseAlternateTheme(activity);
        activity.setTheme(useAlternateTheme);
        applyLanguage(activity);

        getThemeMode(activity);


    }
    private static void setThemeMode(int mode) {
        switch (mode) {
            case MODE_AUTO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case MODE_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case MODE_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }


    public  static void changeThemeMode(Context context,int mode) {
        setThemeMode(mode);
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_THEME_MODE, mode);
        editor.apply();
    }

    public  static  void getThemeMode(Context context) {
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeMode = sharedPreferences.getInt(KEY_THEME_MODE, MODE_AUTO);
        setThemeMode(themeMode);
    }

    public  static  int getThemeMode2(Context context) {
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
      return sharedPreferences.getInt(KEY_THEME_MODE, MODE_AUTO);

    }



    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public static void applyLanguage(Context context) {
        String languageCode = getLanguage(context);
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        context.getResources().getConfiguration().setLocale(locale);
        context.getResources().updateConfiguration(context.getResources().getConfiguration(), context.getResources().getDisplayMetrics());

    }
    private static final String PREFERENCE_NAME = "LanguagePreference";
    private static final String KEY_SELECTED_LANGUAGE = "SelectedLanguage";

    public static String getLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        return preferences.getString(KEY_SELECTED_LANGUAGE, Locale.getDefault().getLanguage());
    }


    public static void setLanguage(Context context, String languageCode) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_SELECTED_LANGUAGE, languageCode);
        editor.apply();
    }

    public static   void changeImg(Context context, ImageView view) {
        String currentLanguage = getCurrentLanguage(context);
        if ("ar".equals(currentLanguage) || "iw".equals(currentLanguage)) {
            view.setScaleX(-1f);
        } else {
            view.setScaleX(1f);
        }

    }




}
