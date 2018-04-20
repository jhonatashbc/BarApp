package com.example.jhonatashenrique.barapp;

import java.io.Serializable;

/**
 * Created by JhonatasHenrique on 11/04/2018.
 */

public class Produtos implements Serializable {

    long id;
    String produto;
    double valor_produto;
    long id_nmesa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getValor_produto() {
        return valor_produto;
    }

    public void setValor_produto(double valor_produto) {
        this.valor_produto = valor_produto;
    }

    public long getId_nmesa() {
        return id_nmesa;
    }

    public void setId_nmesa(long id_nmesa) {
        this.id_nmesa = id_nmesa;
    }

    @Override
    public String toString() {

        return String.valueOf("Produto: "+produto +" |   Valor: R$"+valor_produto);

    }
}
