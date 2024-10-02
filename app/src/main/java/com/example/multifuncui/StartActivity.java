package com.example.multifuncui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.multifuncui.databinding.ActivityStartBinding;
import com.example.multifuncui.mangment.PrefManager;

import java.util.Locale;

public class StartActivity extends AppCompatActivity {
   private   ActivityStartBinding binding;
   public  static  final String USER_DATA="user_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefManager.FullScreen(this, false);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Adjust padding to avoid overlap with system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the button to start the MainActivity
        binding.button.setOnClickListener(v -> start());

        // Initialize language selection
     //   setupLanguageSpinner(binding.languageSpinner);
        setupLanguageAutoComplete(binding.languageSpinner);
        binding.textView3.setOnClickListener(v ->
        {
            String url = "https://abedallask.netlify.app/";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
    }

    private void setupLanguageAutoComplete(AutoCompleteTextView languageSpinner) {
        // Get saved language preference
        String currentLanguage = PrefManager.getLanguage(this);
        String[] languageValues = getResources().getStringArray(R.array.language_options_values);
        String[] languageDisplay = getResources().getStringArray(R.array.language_options_display);

        // Set up the ArrayAdapter for the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, languageDisplay);
        languageSpinner.setAdapter(adapter);

        // Ensure the dropdown shows even if the text is empty
        languageSpinner.setOnClickListener(v -> languageSpinner.showDropDown());

        // Prevent the text from being changed when an item is selected
        languageSpinner.setOnItemClickListener((parent, view, position, id) -> {
            String selectedValue = languageValues[position];
            if (!selectedValue.equals(currentLanguage)) {
                // Save new language in preferences and apply it without changing the text
                PrefManager.setLanguage(StartActivity.this, selectedValue);
                setAppLanguage(selectedValue);
            }

            // Reset the text back to the original value (empty or static)
            languageSpinner.setText("", false); // Keep it empty or reset to a default value if needed
        });

        // Optionally, keep the current language visible but reset if needed
        if (!currentLanguage.isEmpty()) {
            languageSpinner.setText("", false);  // Keep the text field empty after selection
        }
    }

    // Start MainActivity
    private void start() {
        // Get the username text
        String username = binding.usernameEditText.getText() != null ? binding.usernameEditText.getText().toString() : "";

        // Check if the username is empty
        if (username.isEmpty()) {
            binding.usernameEditText.setError(getText(R.string.you_must_to_insert_the_username));
        } else {
            User user = new User(username);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(USER_DATA, user);
            startActivity(intent);
        }
    }


    // Method to change the app language and restart MainActivity
    private void setAppLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        createConfigurationContext(configuration);
    }


}
