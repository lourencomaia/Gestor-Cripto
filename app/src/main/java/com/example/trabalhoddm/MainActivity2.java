package com.example.trabalhoddm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    DBhelper db;
    Intent i;
    ActivityResultLauncher<Intent> activityResultLauncher;

    TextView mTvValorConvertido, tv;
    Button mBtnConverter, mBtnAdicionarValor, mBtnCarteira;
    List<CriptoMoeda> lista_moedas;
    ListView mLv_moedas;
    Button mBtnAdicinarMoeda;

    DecimalFormat df = new DecimalFormat("#.000");

    //================
    ArrayList<CriptoMoeda> lista_moeda = new ArrayList<>();
    double Total = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lista_moedas = new ArrayList<>();


        db = new DBhelper(this);
        tv = findViewById(R.id.tv);

        //botao de converter e seu evento
        mBtnConverter = findViewById(R.id.idBtnConverter);
        mBtnConverter.setOnClickListener(mClickHandler);

        mTvValorConvertido = findViewById(R.id.idTvValorConvertido);

        //botao visualizar Carteira
        mBtnCarteira = findViewById(R.id.idBtnCarteira);
        mBtnCarteira.setOnClickListener(mClickHandler);

        mLv_moedas = findViewById(R.id.idLv_moedas);
        mBtnAdicinarMoeda = findViewById(R.id.idBtnAdicinarMoeda);
        mBtnAdicinarMoeda.setOnClickListener(mClickHandler);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                listarMoedasListView();
                calculaValorTotalPortefolioEmDolares();
                //Toast.makeText(getApplicationContext(), "Voltei", Toast.LENGTH_SHORT).show();
            }

        });
        // evento de resposta ao click na lista
        mLv_moedas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity2.this, EditarMoeda.class);
                i.putExtra("moeda", lista_moedas.get(position).getMoeda());
                activityResultLauncher.launch(i);
            }
        });

        calculaValorTotalPortefolioEmDolares();
        listarMoedasListView();

    }

    //Eventos dos botoes
    View.OnClickListener mClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.idBtnConverter:
                    i = new Intent(MainActivity2.this, MainActivity.class);
                    //startActivity(i);
                    activityResultLauncher.launch(i);
                    break;
                case R.id.idBtnAdicinarMoeda:
                    i = new Intent(MainActivity2.this, NovaMoeda.class);
                    activityResultLauncher.launch(i);
                    break;

                case R.id.idBtnCarteira:
                    i = new Intent(MainActivity2.this, Carteira.class);
                    startActivity(i);
                    break;
            }
        }
    };

    double converter(String criptoMoeda, Double quantidadeDeMoedasConcerter) {
        String m = null;
        try {
            m = new BitcoinValueTask(mTvValorConvertido, criptoMoeda, 1.0, "eur").execute().get().toString();
        } catch (Exception e) {
            Toast.makeText(this, criptoMoeda, Toast.LENGTH_SHORT).show();
        }
        return quantidadeDeMoedasConcerter * Double.parseDouble(m);
    }

    private void listartodasAsMoedasNome() {
        lista_moeda.clear();
        Cursor c = db.SelectAll_Carteira();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                @SuppressLint("Range") String moeda = c.getString(c.getColumnIndex("moeda"));
                @SuppressLint("Range") double quantidade = Double.parseDouble(c.getString(c.getColumnIndex("quantidade")));
                lista_moeda.add(new CriptoMoeda(moeda.toUpperCase(), quantidade));
            } while (c.moveToNext());
        }

    }

    public void calculaValorTotalPortefolioEmDolares() {
        Total = 0;
        listartodasAsMoedasNome();
        lista_moeda.size();


        for (CriptoMoeda n : lista_moeda) {
            String moeda = n.getMoeda();
            Double quantidade = n.getQuantidade();
            double valor = converter(moeda.toLowerCase(), quantidade);
            //Toast.makeText(this, ""+valor, Toast.LENGTH_SHORT).show();
            Total += valor;
        }

        tv.setText(df.format(Total));
    }

    private void listarMoedasListView() {
        lista_moedas.clear();
        Cursor c = db.SelectAll_Carteira();
        c.moveToFirst();

        if (c.getCount() > 0) {
            do {
                @SuppressLint("Range") String moeda = c.getString(c.getColumnIndex("moeda"));
                @SuppressLint("Range") double quantidade = Double.parseDouble(c.getString(c.getColumnIndex("quantidade")));
                lista_moedas.add(new CriptoMoeda(moeda.toUpperCase(), quantidade));
            } while (c.moveToNext());
        }
        ArrayAdapter<CriptoMoeda> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, lista_moedas);
        mLv_moedas.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            listarMoedasListView();
        }else if(requestCode == 2){
            Toast.makeText(getApplicationContext(), "Voltei", Toast.LENGTH_SHORT).show();
        }
    }
}