package com.example.trabalhoddm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    private static int versao =1;
    private static String nomeBD = "ExemploBD.db";
    String[] sql = {
            "CREATE TABLE Carteira(moeda TEXT PRIMARY KEY, quantidade NUMBER,valor_euros NUMBER);",
                    "insert into Carteira Values('bitcoin','0',22588);",
            "insert into Carteira Values('binancecoin','0',300.15);",
            "insert into Carteira Values('ethereum','0',1544.65);",
            "insert into Carteira Values('ripple','0',0.406);",
            "insert into Carteira Values('cardano','0',0.35);",
            "insert into Carteira Values('dogecoin','0',0.08);",
            "insert into Carteira Values('matic','0',0.948);",
            "insert into Carteira Values('solana','0',23.05);",
            "insert into Carteira Values('ravencoin','0',0.0277);",
            "insert into Carteira Values('ethereum-classic','0',21.13);",
            "insert into Carteira Values('chia','0',45.14);",




    };

    public DBhelper(@Nullable Context context) {
        super(context, nomeBD,null, versao);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < sql.length; i++) {db.execSQL(sql[i]);}}
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        versao++;
        db.execSQL("DROP TABLE IF EXISTS Carteira");
    }//======================================INSERT=============================================
    public long insert_Valor(String moeda, double quantidade){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("moeda", moeda);
        values.put("quantidade", quantidade);
        return db.insert("Carteira", null, values);
    }//======================================UPDATE=================================================
    public long Update_Moeda(String moeda, double quantidade){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantidade",quantidade);
        return db.update("Carteira", values,"moeda=?",new String[]{moeda});
    }//======================================DELETE================================================
    public long Delete_Moeda(String moeda){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Carteira","moeda=?", new String[]{moeda});
    }//======================================SELECT===============================================
    public Cursor SelectAll_Carteira(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from Carteira", null);
    }
    public Cursor SelectByMoeda_Carteira(String moeda){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from Carteira where moeda=?", new String[]{moeda});
    }
    public long Update_Valor_Moeda(String moeda, double valor_euros){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("valor_euros",valor_euros);
        return db.update("Carteira", values,"moeda=?",new String[]{moeda});
    }//======================================DELETE================================================
}

