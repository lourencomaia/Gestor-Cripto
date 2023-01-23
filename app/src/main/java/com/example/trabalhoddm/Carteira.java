package com.example.trabalhoddm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Carteira extends AppCompatActivity {

    List<CriptoMoeda> lista_moedas;
    DBhelper db;
    ListView mLv_moedas;
    Button mBtnAdicinarMoeda;
    Intent i;

    ActivityResultLauncher<Intent> activityResultLauncher;

    //Codigo referente ao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_carteira,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.idMenuInicio:{
                i = new Intent(Carteira.this, ActivityPrincipal.class);
                activityResultLauncher.launch(i);
                break;
            }
            case R.id.idMenuCarteira:{
                i = new Intent(Carteira.this, Conversor.class);
                activityResultLauncher.launch(i);
                break;
            }
            case  R.id.idMenuAdicionarMoeda:{
                i = new Intent(Carteira.this, NovaMoeda.class);
                activityResultLauncher.launch(i);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
    //FIM Codigo referente ao menu

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);
        db = new DBhelper(this);

        mLv_moedas = findViewById(R.id.idLv_moedas);
        mBtnAdicinarMoeda = findViewById(R.id.idBtnAdicinarMoeda);

        mBtnAdicinarMoeda.setOnClickListener(mClickHandler);

        lista_moedas = new ArrayList<>();

        preencherListView();

        mLv_moedas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Carteira.this,EditarMoeda.class);
                i.putExtra("moeda",lista_moedas.get(position).getMoeda());
                activityResultLauncher.launch(i);
            }
        });

         activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                preencherListView();
                //Toast.makeText(getApplicationContext(), "Voltei", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void preencherListView() {
        lista_moedas.clear();
        Cursor c = db.SelectAll_Carteira();
        c.moveToFirst();
        if(c.getCount() > 0){
            do {
                @SuppressLint("Range") String moeda = c.getString( c.getColumnIndex("moeda"));
                @SuppressLint("Range") double quantidade = Double.parseDouble(c.getString(c.getColumnIndex("quantidade")));
                if(quantidade!=0){
                    lista_moedas.add(new CriptoMoeda(moeda.toUpperCase(),quantidade));
                }

            }while (c.moveToNext());
        }
        ArrayAdapter<CriptoMoeda> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,lista_moedas);
        mLv_moedas.setAdapter(adapter);
    }

    View.OnClickListener  mClickHandler = new View.OnClickListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.idBtnAdicinarMoeda:
                    Intent i = new Intent(Carteira.this,NovaMoeda.class);
                    activityResultLauncher.launch(i);
                    break;

            }
        }
    };







}