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
            "CREATE TABLE Carteira(moeda TEXT PRIMARY KEY, quantidade NUMBER);",
                    "insert into Carteira Values('bitcoin','2');"};

    public DBhelper(@Nullable Context context) {
        super(context, nomeBD,null, versao);}
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
}

