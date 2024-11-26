package com.example.sisong;
public class Doador {
    private int doadorId;
    private String nomeDoador;
    private String username;
    private String emailDoador;
    private String senha;

    public Doador(String nomeDoador, String username, String emailDoador, String senha) {
        this.nomeDoador = nomeDoador;
        this.username = username;
        this.emailDoador = emailDoador;
        this.senha = senha;
    }
    public int getDoadorId() {
        return doadorId;
    }

    public void setDoadorId(int doadorId) {
        this.doadorId = doadorId;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailDoador() {
        return emailDoador;
    }

    public void setEmailDoador(String emailDoador) {
        this.emailDoador = emailDoador;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}