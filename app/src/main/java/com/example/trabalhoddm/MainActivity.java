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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        private Spinner mSpCriptoMoedas;
        private Spinner mSpMoedasGovernamentais;
        private CriptoMoedasAdpater adapter;

        DBhelper db;
        Intent i;

        TextView mTvValorConvertido;
        Button mBtnConverter, mBtnAdicionarValor,mBtnCarteira;
        EditText mEtQuantidadeMoedasConverter;

        double Total;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBhelper(this);

        mSpCriptoMoedas = findViewById(R.id.idSpCriptoMoedas);
        adapter = new CriptoMoedasAdpater(MainActivity.this, Data.getMoedas());
        mSpCriptoMoedas.setAdapter(adapter);



        //botao de converter e seu evento
        mBtnConverter = findViewById(R.id.idBtnConverter);
        mBtnConverter.setOnClickListener(mClickHandler);
        //botao adiciionar valoe e seu evento
        mBtnAdicionarValor = findViewById(R.id.idBtnAdicionarValor);
        mBtnAdicionarValor.setOnClickListener(mClickHandler);

        //botao visualizar Carteira
        mBtnCarteira = findViewById(R.id.idBtnCarteira);
        mBtnCarteira.setOnClickListener(mClickHandler);
        mSpMoedasGovernamentais = findViewById(R.id.idSpMoedas);

        mTvValorConvertido = findViewById(R.id.idTvValorConvertido);
        mEtQuantidadeMoedasConverter = findViewById(R.id.idEtQuantidadeMoedasConverter);

        db = new DBhelper(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.moedaGovernamentalListaItens, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpMoedasGovernamentais.setAdapter(adapter);

    }
    View.OnClickListener  mClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.idBtnConverter:
                    converter();
                    break;
                case R.id.idBtnAdicionarValor:
                    adicionarValor();
                    break;

                case R.id.idBtnCarteira:
                  Intent i = new Intent( MainActivity.this,Carteira.class);
                  startActivity(i);
                  break;

            }
        }
    };

    void converter(){
        //ir buscar o o nome de cripto moeda a converter
        String criptoMoeda=  Data.getMoedas().get(Integer.parseInt(
                mSpCriptoMoedas.getSelectedItem().toString())).getName();
        // buscar a moeda para a qual queremos converter
        String moedaGovernamental =mSpMoedasGovernamentais.getSelectedItem().toString();

        // quantidade de moedas a converter
        String quantidadeDeMoedasConcerter = (mEtQuantidadeMoedasConverter.getText().toString());

        try {
            new BitcoinValueTask(mTvValorConvertido,criptoMoeda,Double.parseDouble(quantidadeDeMoedasConcerter),moedaGovernamental ).execute();
            CriptoMoeda moeda1 = new CriptoMoeda(criptoMoeda,Double.parseDouble( mTvValorConvertido.getText().toString()));
            //Total +=Double.parseDouble( mTvValorConvertido.getText().toString()) + 1;
            Toast.makeText(this,moeda1.getMoeda()+"= "+moeda1.getQuantidade(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this,criptoMoeda, Toast.LENGTH_SHORT).show();
        }
    }

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
                Toast.makeText(MainActivity.this,
                        "Moeda inserida com sucesso", Toast.LENGTH_SHORT).show();
            //caso se a moeda ja exista na base de dados
            }else if(res == -1){
                double valorAnterior = valorExistentePorMoeda(moeda);

                db.Update_Moeda(moeda, Double.parseDouble(
                        String.valueOf(Double.parseDouble(quantidade)+valorAnterior)));
                Toast.makeText(MainActivity.this, "valor atualizado com sucesso",
                        Toast.LENGTH_SHORT).show();

            }
            else{ Toast.makeText(MainActivity.this,
                    "Erro ao inserir user" , Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, moeda, Toast.LENGTH_SHORT).show();

            }
        }
        //setResult(1,i);
    }




}