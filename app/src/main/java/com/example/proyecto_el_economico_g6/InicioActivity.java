package com.example.proyecto_el_economico_g6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InicioActivity extends AppCompatActivity {

    ImageButton btnPerfilUser, btncategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnPerfilUser = findViewById(R.id.btnPerfilUser);
        btncategoria =findViewById(R.id.btncategoria);


        btnPerfilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, PerfilActivity.class));
            }
        });

        btncategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, CategoriActivity.class));
            }
        });

    }
}