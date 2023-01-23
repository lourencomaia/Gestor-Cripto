package com.example.trabalhoddm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarMoeda extends AppCompatActivity {

    EditText mEtMoedaSelecionada, mEtQuantidade;
    Button mBtnAtualizar, mBtnEliminar;
    Intent i;
    DBhelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_moeda);
        db = new DBhelper(this);

        i = getIntent();
        String moeda = i.getExtras().getString("moeda");
        mEtMoedaSelecionada= findViewById(R.id.idEtMoedaSelecionada);
        mEtQuantidade = findViewById(R.id.idEtQuantidade);

        mBtnAtualizar= findViewById(R.id.idBtnAtualizar);
        mBtnEliminar = findViewById(R.id.idBtnEliminar);

        mBtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long res = db.Update_Moeda(moeda.toLowerCase(),0);
                if(res > 0){
                    Toast.makeText(EditarMoeda.this,
                            "Moeda eliminada com sucesso",
                            Toast.LENGTH_SHORT).show();
                }
                setResult(2,i);
                finish();

            }
        });

        mBtnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long res = db.Update_Moeda(moeda.toLowerCase(), Double.parseDouble(mEtQuantidade.getText().toString()));
                if (res > 0) {
                    Toast.makeText(EditarMoeda.this, "Moeda Atualizada com Sucesso", Toast.LENGTH_SHORT).show();
                }
                setResult(1,i);
                finish();
            }});

        carregarDadosMoeda(moeda);
    }

    private void carregarDadosMoeda(String moeda) {
        Cursor c = db.SelectByMoeda_Carteira(moeda.toLowerCase());
        c.moveToFirst();
        if(c.getCount()==1){
            @SuppressLint("Range") String quantildadeMoeda = c.getString(c.getColumnIndex("quantidade"));
            mEtMoedaSelecionada.setText(moeda);
            mEtQuantidade.setText(quantildadeMoeda);
        }else if(c.getCount()==0){
            Toast.makeText(this,
                    "Moeda inexistente", Toast.LENGTH_SHORT).show();
        }else{Toast.makeText(this,
                "Erro ao carregar erro", Toast.LENGTH_SHORT).show();
        }
    }
}