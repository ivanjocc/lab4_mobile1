package com.example.lab4;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowUsersBefore2020Task extends AsyncTask<Void, Void, List<String>> {

    private Context context;

    public ShowUsersBefore2020Task(Context context) {
        this.context = context;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> userList = new ArrayList<>();

        String url = "jdbc:mysql://10.0.0.2/lab4_mobile1";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Log.d("ShowUsersBefore2020Task", "Database connection established successfully.");

            String query = "SELECT name, last_name FROM utilisateur WHERE dateEntry < '2020-01-01'";
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                userList.add(name + " " + lastName);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("ShowUsersBefore2020Task", "Error: " + e.getMessage());
        }

        return userList;
    }

    @Override
    protected void onPostExecute(List<String> userList) {
        showToast("Users Before 2020:\n" + TextUtils.join("\n", userList));
    }

    private void showToast(String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
