package com.example.jhonatashenrique.barapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.getIntent;

/**
 * Created by JhonatasHenrique on 09/04/2018.
 */

public class MesaDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "dbmesas";
    private static final int VERSION = 1;

    public MesaDAO (Context context){
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String produto = "DROP TALBE IF EXISTS ProdutosMesas";
        db.execSQL(produto);

    }

    public void salvaDados (Produtos produtos){
        ContentValues values = new ContentValues();

        values.put("id_mesa", produtos.getId_nmesa());
        values.put("produto", produtos.getProduto());
        values.put("valorproduto",produtos.getValor_produto());

        getWritableDatabase().insert("ProdutosMesas", null, values);

    }

    public void alteraDados (Produtos produtos){
        ContentValues values = new ContentValues();

        values.put("id_mesa", produtos.getId_nmesa());
        values.put("produto", produtos.getProduto());
        values.put("valorproduto", produtos.getValor_produto());

        String[] arg = {String.valueOf(produtos.getId())};

        getWritableDatabase().update("ProdutosMesas", values, "id=?", arg);
    }

    public void deletaDados (Produtos produtos){
        String[] arg = {String.valueOf(produtos.getId())};
        getWritableDatabase().delete("ProdutosMesas", "id=?", arg);
    }


    public ArrayList<Produtos> getLista(Long longer){
        String [] where= {""+longer};
        String [] columns = {"id","id_mesa", "produto", "valorproduto"};
        Cursor cursor = getReadableDatabase().query("ProdutosMesas", columns, "id_mesa=?", where,null, null, null, null);

        ArrayList<Produtos> Produtos = new ArrayList<Produtos>();
        while (cursor.moveToNext()){
            Produtos produt = new Produtos();
            produt.setId(cursor.getLong(0));
            produt.setId_nmesa(cursor.getInt(1));
            produt.setProduto(cursor.getString(2));
            produt.setValor_produto(cursor.getDouble(3));

            Produtos.add(produt);
        }

        return Produtos;
    }
}
