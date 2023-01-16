package com.example.trabalhoddm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NovaMoeda extends AppCompatActivity {
    private Spinner mSpCriptoMoedas;

    private CriptoMoedasAdpater adapter;

    DBhelper db;
    Intent i;

    //TextView mTvValorConvertido;
    Button  mBtnAdicionarValor,mBtnCarteira;
    EditText mEtQuantidadeMoedasConverter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_moeda);

        db = new DBhelper(this);

        mSpCriptoMoedas = findViewById(R.id.idSpCriptoMoedas);
        adapter = new CriptoMoedasAdpater(NovaMoeda.this, Data.getMoedas());
        mSpCriptoMoedas.setAdapter(adapter);



        //botao de converter e seu evento
//        mBtnConverter = findViewById(R.id.idBtnConverter);
//        mBtnConverter.setOnClickListener(mClickHandler);
        //botao adiciionar valoe e seu evento
        mBtnAdicionarValor = findViewById(R.id.idBtnAdicionarValor);
        mBtnAdicionarValor.setOnClickListener(mClickHandler);

        //botao visualizar Carteira
        mBtnCarteira = findViewById(R.id.idBtnCarteira);
        mBtnCarteira.setOnClickListener(mClickHandler);


        //mTvValorConvertido = findViewById(R.id.idTvValorConvertido);
        mEtQuantidadeMoedasConverter = findViewById(R.id.idEtQuantidadeMoedasConverter);

        db = new DBhelper(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.moedaGovernamentalListaItens, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);


    }
    View.OnClickListener  mClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
//                c
                case R.id.idBtnAdicionarValor:
                    adicionarValor();
                    setResult(1,i);
                    finish();
                    break;

                case R.id.idBtnCarteira:
                    Intent i = new Intent( NovaMoeda.this,Carteira.class);
                    startActivity(i);

                    break;

            }
        }
    };

//    void converter(){
//        //ir buscar o o nome de cripto moeda a converter
//        String criptoMoeda=  Data.getMoedas().get(Integer.parseInt(
//                mSpCriptoMoedas.getSelectedItem().toString())).getName();
//        // buscar a moeda para a qual queremos converter
//        String moedaGovernamental =mSpMoedasGovernamentais.getSelectedItem().toString();
//
//        // quantidade de moedas a converter
//        String quantidadeDeMoedasConcerter = (mEtQuantidadeMoedasConverter.getText().toString());
//
//        try {
//            new BitcoinValueTask(mTvValorConvertido,criptoMoeda,Double.parseDouble(quantidadeDeMoedasConcerter),moedaGovernamental ).execute();
//        }catch (Exception e){
//            Toast.makeText(this,criptoMoeda, Toast.LENGTH_SHORT).show();
//        }
//    }

    @SuppressLint("Range")
    private double valorExistentePorMoeda(String moeda) {
        String text;
        Cursor c = db.SelectByMoeda_Carteira(moeda);
        c.moveToFirst();
        double quantidade = 0;
        if (c.getCount() == 1) {
            quantidade = Double.parseDouble(c.getString(c.getColumnIndex("quantidade")));
            Toast.makeText(this, "" + quantidade, Toast.LENGTH_SHORT).show();
        }
        return quantidade;
    }

    public void adicionarValor(){
        String moeda = Data.getMoedas().get(Integer.parseInt(
                mSpCriptoMoedas.getSelectedItem().toString())).getName();;
        String quantidade = mEtQuantidadeMoedasConverter.getText().toString();

        if(!moeda.trim().isEmpty() && !quantidade.trim().isEmpty()){
            //inserir nova moeda
            long res = db.insert_Valor(moeda, Double.parseDouble(quantidade));

            if(res>0){
                Toast.makeText(NovaMoeda.this,
                        "Moeda inserida com sucesso" + res, Toast.LENGTH_SHORT).show();
                //caso se a moeda ja exista na base de dados
            }else if(res == -1){
                double valorAnterior = valorExistentePorMoeda(moeda);

                db.Update_Moeda(moeda, Double.parseDouble(
                        String.valueOf(Double.parseDouble(quantidade)+valorAnterior)));
                Toast.makeText(NovaMoeda.this, "valor atualizado com sucesso",
                        Toast.LENGTH_SHORT).show();

            }
            else{ Toast.makeText(NovaMoeda.this,
                    "Erro ao inserir user" , Toast.LENGTH_SHORT).show();
                Toast.makeText(NovaMoeda.this, moeda, Toast.LENGTH_SHORT).show();

            }
        }
        //setResult(1,i);
    }




}