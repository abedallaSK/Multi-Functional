package com.example.multifuncui.ui.settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multifuncui.MainActivity;
import com.example.multifuncui.R;
import com.example.multifuncui.StartActivity;
import com.example.multifuncui.databinding.FragmentSettingsBinding;
import com.example.multifuncui.funcsButton.page.FuncActivity;
import com.example.multifuncui.mangment.PrefManager;

import java.util.Locale;

import soup.neumorphism.NeumorphCardView;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get ViewModel
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // Inflate layout
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // TextView for app version
        final TextView versionTextView = binding.versionTextView;
        settingsViewModel.getAppVersion().observe(getViewLifecycleOwner(), versionTextView::setText);

        // Button for update
        final NeumorphCardView updateButton = binding.updateButton;
        settingsViewModel.isUpdateAvailable().observe(getViewLifecycleOwner(), isUpdateAvailable -> {
            if (isUpdateAvailable) {
                updateButton.setVisibility(View.VISIBLE);
            } else {
                updateButton.setVisibility(View.GONE);
            }
        });

        // Handle update button click
        updateButton.setOnClickListener(v -> {
            String playStoreUrl = "https://play.google.com/store/apps/details?id=" + requireContext().getPackageName();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl));
            startActivity(intent);
        });

        binding.link.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://abedallask.netlify.app/")); // Your desired link
            startActivity(intent);
        });

        String currentLanguage = PrefManager.getLanguage(requireContext());
        String[] languageValues = getResources().getStringArray(R.array.language_options_values);
        String[] languageDisplay = getResources().getStringArray(R.array.language_options_display);
        int currentLanguageIndex = -1;
        for (int i = 0; i < languageValues.length; i++) {
            if (currentLanguage.equalsIgnoreCase(languageValues[i])) {
                currentLanguageIndex = i;
                break;
            }
        }
        if(currentLanguageIndex!=-1)
            binding.autoLanguage.setText(languageDisplay[currentLanguageIndex]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, languageDisplay);
        binding.autoLanguage.setAdapter(adapter);
        binding.autoLanguage.setThreshold(1);



        binding.autoLanguage.setOnItemClickListener((parent, view, position, id) -> {
            String[] valuesArray = getResources().getStringArray(R.array.language_options_values);
            String selectedValue = valuesArray[position];
            setAppLanguage(selectedValue);
            PrefManager.setLanguage(requireContext(), selectedValue);
        });



        int currentMode = PrefManager.getThemeMode2(requireContext());
        String[] themeDisplay = getResources().getStringArray(R.array.theme_options);
        binding.modeChange.setText(themeDisplay[currentMode]);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, themeDisplay);
        binding.modeChange.setAdapter(adapter2);
        binding.modeChange.setThreshold(1);



        binding.modeChange.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) ->
                PrefManager.changeThemeMode(requireContext(), position));

        binding.logout.setOnClickListener(view -> logoutFunc());
        binding.delete.setOnClickListener(view -> deleteFunc());
        binding.dash.setOnClickListener(view -> requireContext().startActivity(new Intent(requireContext(), FuncActivity.class)));


        return root;
    }

    private void setAppLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        requireContext().createConfigurationContext(configuration);
        requireActivity().finish();
        startActivity(new Intent(requireContext(), MainActivity.class));
    }

    private void deleteFunc() {

    }
    private void logoutFunc() {
        startActivity(new Intent(requireContext(), StartActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
