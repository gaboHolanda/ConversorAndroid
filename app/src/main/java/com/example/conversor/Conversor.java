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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Conversor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText valor;
    TextView saudar, fato;
    Button converter;
    private Spinner de, para;
    private String valor_de, valor_para;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private String url = "https://catfact.ninja/fact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversor);

        valor = findViewById(R.id.valor);
        de = findViewById(R.id.de);
        para = findViewById(R.id.para);
        saudar = findViewById(R.id.saudar);
        fato = findViewById(R.id.fato);

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

        getCatFact();

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

    public void getCatFact(){
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    String res = json.getString("fact");
                    fato.setText(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", "Error :" + error.toString());
            }
        });
        requestQueue.add(stringRequest);
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
