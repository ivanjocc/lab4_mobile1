package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyActivity extends AppCompatActivity {

    private EditText txtName, txtLastName, txtUsr, txtPwd, txtDateEntry;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        txtName = findViewById(R.id.txtName);
        txtLastName = findViewById(R.id.txtLastName);
        txtUsr = findViewById(R.id.txtUsr);
        txtPwd = findViewById(R.id.txtPwd);
        txtDateEntry = findViewById(R.id.txtDateEntry);

        userId = getIntent().getIntExtra("userId", -1);

        loadUserDataFromDatabase();

        Button saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserDataToDatabase();
            }
        });
    }

    private void loadUserDataFromDatabase() {
        // Implementar la lógica para cargar la información actual del usuario desde la base de datos
        // y mostrarla en los campos de la interfaz de usuario
    }

    private void saveUserDataToDatabase() {
        // Implementar la lógica para guardar las modificaciones en la base de datos
        // Puedes utilizar una AsyncTask similar a la que usas en MainActivity
        // Asegúrate de manejar cualquier error y mostrar mensajes adecuados al usuario
    }
}
