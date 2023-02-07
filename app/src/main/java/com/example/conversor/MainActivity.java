package com.example.conversor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText nome, senha;
    Button entrar;

    SQLHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.nome);
        senha = findViewById(R.id.senha);

        entrar = findViewById(R.id.button);

        helper = new SQLHelper(this);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUser();
            }
        });
    }

    public void passar(){
        Intent intent = new Intent(MainActivity.this, Conversor.class);
        intent.putExtra("Nome", nome.getText().toString());
        startActivity(intent);
    }

    public void validarUser(){

        String nom  = nome.getText().toString();
        String sen = senha.getText().toString();

        Cursor cursor = helper.findUser(nom);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Login do usuario");
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            String data = cursor.getString(0);
            if (sen.equals(data)){
                builder.setMessage("Sucesso ao logar");
                builder.show();
                passar();
            }
            else{
                builder.setMessage("Senha incorreta");
                builder.show();
            }
        }
        else {
            Boolean insert = helper.insertUserData(nom, sen);
            if (insert == true){
                builder.setMessage("Usuario criado");
                passar();
            }
            else {
                builder.setMessage("Falha ao criar usuario");
            }
        }
    }
}