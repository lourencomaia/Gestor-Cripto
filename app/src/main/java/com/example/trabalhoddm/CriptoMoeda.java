package com.example.trabalhoddm;

public class CriptoMoeda {

    String moeda;
    double quantidade;

    public CriptoMoeda(String moeda, double quantidade) {
        this.moeda = moeda;
        this.quantidade = quantidade;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return getMoeda()+" : "+ getQuantidade();
    }
}
