package com.example.conversor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class Conversor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText valor;
    TextView saudar;
    Button converter;
    private Spinner de, para;
    private String valor_de, valor_para;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversor);

        valor = findViewById(R.id.valor);
        de = findViewById(R.id.de);
        para = findViewById(R.id.para);
        saudar = findViewById(R.id.saudar);

        valor_de = "Mm";
        valor_para = "Cm";

        Intent intencao = getIntent();
        saudar.setText(intencao.getStringExtra("Nome"));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.medidas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        de.setAdapter(adapter);
        de.setOnItemSelectedListener(this);

        para.setAdapter(adapter);
        para.setOnItemSelectedListener(this);

        converter = findViewById(R.id.converter);

        converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcular();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String escolha = adapterView.getItemAtPosition(i).toString();

        switch (adapterView.getId()){
            case R.id.de:
                this.valor_de = escolha;
                break;
            case R.id.para:
                this.valor_para = escolha;
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void calcular(){
        float _resultado = 0;
        float val = Float.parseFloat(valor.getText().toString());

        if(valor_de.equals("Mm")){
            if(valor_para.equals("Mm")){
                _resultado = val * 1;
            }
            if(valor_para.equals("Cm")){
                _resultado = val/10;
            }
            if(valor_para.equals("M")){
                _resultado = val/1000;
            }
            if(valor_para.equals("Km")){
                _resultado = val/1000000;
            }
        }
        if(valor_de.equals("Cm")){
            if(valor_para.equals("Mm")){
                _resultado = val * 10;
            }
            if(valor_para.equals("Cm")){
                _resultado = val * 1;
            }
            if(valor_para.equals("M")){
                _resultado = val/100;
            }
            if(valor_para.equals("Km")){
                _resultado = val/100000;
            }
        }
        if(valor_de.equals("M")){
            if(valor_para.equals("Mm")){
                _resultado = val * 1000;
            }
            if(valor_para.equals("Cm")){
                _resultado = val * 100;
            }
            if(valor_para.equals("M")){
                _resultado = val * 1;
            }
            if(valor_para.equals("Km")){
                _resultado = val/1000;
            }
        }
        if(valor_de.equals("Km")){
            if(valor_para.equals("Mm")){
                _resultado = val * 1000000;
            }
            if(valor_para.equals("Cm")){
                _resultado = val * 100000;
            }
            if(valor_para.equals("M")){
                _resultado = val * 1000;
            }
            if(valor_para.equals("Km")){
                _resultado = val * 1;
            }
        }

        Intent intent = new Intent(Conversor.this, Resultado.class);
        intent.putExtra("Resultado", Float.toString(_resultado));
        intent.putExtra("para", valor_para);
        startActivity(intent);
    }
}
