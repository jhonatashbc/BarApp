package com.example.jhonatashenrique.barapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JhonatasHenrique on 06/04/2018.
 */

public class ListaDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "dbmesas";
    private static final int VERSION = 1;

    public ListaDAO (Context context){
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String mesa = "CREATE TABLE ListMesas" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "cliente TEXT NOT NULL," +
                "nmesa INTEGER," +
                "valortotal DOUBLE "+
                ");";
        db.execSQL(mesa);
       String mesa2 = "CREATE TABLE ProdutosMesas" +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_mesa INTEGER NOT NULL," +
                "produto TEXT NOT NULL,"+
                "valorproduto DOUBLE NOT NULL,"+
               "FOREIGN KEY(id_mesa) REFERENCES ListMesas(id)"+
                ");";

        db.execSQL(mesa2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String mesa = "DROP TALBE IF EXISTS ListMesas";
        db.execSQL(mesa);

    }

    public void salvaDados (Mesas mesa){
        ContentValues values = new ContentValues();

        values.put("cliente", mesa.getCliente_nome());
        values.put("nmesa", mesa.getN_mesa());
        values.put("valortotal", mesa.getValortotal());


        getWritableDatabase().insert("ListMesas", null, values);

    }

    public void alteraDados (Mesas mesa){
        ContentValues values = new ContentValues();

        values.put("valortotal", mesa.getValortotal());

        String[] arg = {String.valueOf(mesa.getId())};

        getWritableDatabase().update("ListMesas", values, "id=?", arg);
    }

    public void deletaDados (Mesas mesa){
        String[] arg = {String.valueOf(mesa.getId())};
        getWritableDatabase().delete("ListMesas", "id=?", arg);
    }


    public List<Mesas> getLista(){
        String [] columns = {"id", "cliente", "nmesa", "valortotal"};
        Cursor cursor = getReadableDatabase().query("ListMesas", columns, null, null,null, null, null, null);

        ArrayList<Mesas> mesas = new ArrayList<Mesas>();
        while (cursor.moveToNext()){
            Mesas mesa = new Mesas();
            mesa.setId(cursor.getLong(0));
            mesa.setCliente_nome(cursor.getString(1));
            mesa.setN_mesa(cursor.getInt(2));
            mesa.setValortotal(cursor.getDouble(3));

            mesas.add(mesa);
        }

        return mesas;
    }
}
