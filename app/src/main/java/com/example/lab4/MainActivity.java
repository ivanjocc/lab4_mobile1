package com.example.lab4;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    private EditText txtUsr, txtpwd;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        txtUsr = findViewById(R.id.txtUsr);
        txtpwd = findViewById(R.id.txtpwd);

        Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = txtUsr.getText().toString();
                String pwd = txtpwd.getText().toString();

                if (!usr.isEmpty() && !pwd.isEmpty()) {
                    new AuthenticationTask().execute();
                } else {
                    showToast("Please enter both username and password.");
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.login) {
            showToast("Login selected");
            return true;
        } else if (itemId == R.id.register) {
            openRegisterActivity();
            return true;
        } else if (itemId == R.id.modify) {
            Intent modifyIntent = new Intent(this, ModifyActivity.class);
            modifyIntent.putExtra("userId", userId);
            startActivity(modifyIntent);
            return true;
        } else if (itemId == R.id.before) {
            new ShowUsersBefore2020Task(MainActivity.this).execute();
            return true;
        } else if (itemId == R.id.after) {
            new FetchDataAfter2020Task(MainActivity.this).execute();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void showUsersBefore2020(Context context) {
        new ShowUsersBefore2020Task(context).execute();
    }

    private class AuthenticationTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            String usr = txtUsr.getText().toString();
            String pwd = txtpwd.getText().toString();

            String url = "jdbc:mysql://10.0.0.2/lab4_mobile1";
            String user = "root";
            String password = "";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, password);

                Log.d("AuthenticationTask", "Database connection established successfully.");

                String query = "SELECT * FROM utilisateur WHERE usr = ? AND pwd = ?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, usr);
                statement.setString(2, pwd);

                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                Log.e("AuthenticationTask", "Error: " + e.getMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean isAuthenticated) {
            if (isAuthenticated) {
                showToast("Log in Successful");
            } else {
                showToast("Authentication failed. Check your credentials.");
            }
        }
    }

    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}