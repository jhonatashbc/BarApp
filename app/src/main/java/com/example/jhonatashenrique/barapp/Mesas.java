package com.example.jhonatashenrique.barapp;

import java.io.Serializable;

/**
 * Created by JhonatasHenrique on 06/04/2018.
 */

public class Mesas implements Serializable {

    long id;
    double valortotal;
    String cliente_nome;

    public double getValortotal() {
        return valortotal;
    }

    public void setValortotal(double valortotal) {
        this.valortotal = valortotal;
    }

    int n_mesa;

    public long getId() { return id; }

    public String getCliente_nome() {
        return cliente_nome;
    }

    public void setCliente_nome(String cliente_nome) {
        this.cliente_nome = cliente_nome;
    }

    public int getN_mesa() {
        return n_mesa;
    }

    public void setN_mesa(int n_mesa) {
        this.n_mesa = n_mesa;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return String.valueOf("Cliente: "+cliente_nome +" |   Mesa: "+n_mesa +" |  Total: R$"+valortotal);

    }

}
