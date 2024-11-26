package com.example.sisong;

import java.util.Date;

public class Doacao {
    private int doadorId;
    private double valorDoacao;
    private String nomeDoador;
    private String dataDoacao;

    public Doacao(int doadorId, double valorDoacao) {
        this.doadorId = doadorId;
        this.valorDoacao = valorDoacao;
    }

    public int getDoadorId() {
        return doadorId;
    }

    public double getValorDoacao() {
        return valorDoacao;
    }

    public void setValorDoacao(double valorDoacao) {
        this.valorDoacao = valorDoacao;
    }

    public String getNomeDoador(){
        return nomeDoador;
    }

    public String getDataDoacao() { return dataDoacao; }
}