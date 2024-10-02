package com.example.multifuncui.funcsButton.adapter;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GuessNumberTask extends AsyncTask<String, Void, String> {

    private final GuessNumberListener listener;

    public interface GuessNumberListener {
        void onGuessNumber(int guessedNumber);
    }

    public GuessNumberTask(GuessNumberListener listener) {
        this.listener = listener;
    }

    @Override
    protected final String doInBackground(String... params) {
        if (params.length != 3) {
            return "Invalid parameters";
        }

        String userId = params[0];
        String role = params[1];
        String currentElement = params[2];
        try {
            URL url = new URL("AI_URL");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create JSON request data
            String jsonInputString = "{\"user_id\": \"" + userId + "\", \"role\": \"" + role + "\", \"current_element\": \"" + currentElement + "\"}";




            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (IOException e) {
            Log.e("GuessNumberTask",e.getMessage(),e);
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected final void onPostExecute(String result) {
        try {
            // Parse the JSON string to a JsonObject
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();

            // Extract the integer value associated with the key "guessed_number"
            int guessedNumber = jsonObject.get("guessed_number").getAsInt();

            // Pass the int value to the listener
            if (listener != null) {
                listener.onGuessNumber(guessedNumber);
            }
        } catch (Exception e) {
            listener.onGuessNumber(0);
            // Handle parsing errors or other exceptions as needed
        }
    }
}