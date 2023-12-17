package com.example.lab4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etNewUser, etNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etNewUser = findViewById(R.id.etNewUser);
        etNewPassword = findViewById(R.id.etNewPassword);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String newUser = etNewUser.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || newUser.isEmpty() || newPassword.isEmpty()) {
            showToast("Por favor, complete todos los campos.");
            return;
        }

        new RegisterTask().execute(firstName, lastName, newUser, newPassword);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class RegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String firstName = params[0];
            String lastName = params[1];
            String newUser = params[2];
            String newPassword = params[3];

            try {
                String urlStr = "http://10.0.2.2/lab4_mobile1/register.php";
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");

                urlConnection.setDoOutput(true);

                JSONObject jsonParams = new JSONObject();
                jsonParams.put("firstName", firstName);
                jsonParams.put("lastName", lastName);
                jsonParams.put("newUser", newUser);
                jsonParams.put("newPassword", newPassword);

                try (OutputStream outputStream = urlConnection.getOutputStream()) {
                    byte[] input = jsonParams.toString().getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }

                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }

                    return response.toString();
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.has("error")) {
                    showToast("Error: " + jsonResult.getString("error"));
                } else if (jsonResult.has("message")) {
                    showToast(jsonResult.getString("message"));
                } else {
                    showToast("Unknown error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showToast("Error: " + e.getMessage());
            }
        }

    }
}
