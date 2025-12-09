package com.example.appedumanager.model;

public final class Bolsista extends Aluno{
    private float bolsa;

    public float getBolsa() {
        return this.bolsa;
    }

    public void setBolsa(float bolsa) {
        this.bolsa = bolsa;
    }

    @Override
    public String toString() {
        return id + "," + nome + "," + idade + "," + sexo + "," + curso + "," +  mensalidade + "," + matricula + "," + bolsa;
    }
}
