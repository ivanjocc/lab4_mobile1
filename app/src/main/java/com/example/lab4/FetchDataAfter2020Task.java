package com.example.lab4;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FetchDataAfter2020Task extends AsyncTask<Void, Void, List<String>> {

    private WeakReference<MainActivity> activityReference;

    FetchDataAfter2020Task(MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected List<String> doInBackground(Void... voids) {

        List<String> data = new ArrayList<>();
        String url = "jdbc:mysql://10.0.0.2/lab4_mobile1";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Log.d("FetchDataAfter2020Task", "Database connection established successfully.");

            String query = "SELECT name FROM utilisateur WHERE dateEntry > '2020-01-01'";
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                data.add(name);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("FetchDataAfter2020Task", "Error: " + e.getMessage());
        }

        return data;
    }

    @Override
    protected void onPostExecute(List<String> data) {
        MainActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;

        if (!data.isEmpty()) {
            ListView listView = activity.findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, data);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        } else {
            activity.showToast("No data found after 2020.");
        }
    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
