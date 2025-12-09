package com.example.appedumanager.model;

public class Visitante extends Pessoa {
    @Override
    public String toString() {
        return id + "," + nome + "," + idade + "," + sexo;
    }
}
