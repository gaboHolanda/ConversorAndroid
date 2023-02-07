package com.example.conversor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class Resultado extends AppCompatActivity {

    String res, para;

    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado);

        resultado = findViewById(R.id.resultado);

        Intent intencao = getIntent();
        res = intencao.getStringExtra("Resultado");
        para = intencao.getStringExtra("para");

        res += " ";
        res += para;

        resultado.setText(res);

    }

}
