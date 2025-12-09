package com.example.appedumanager.model;

public final class Tecnico extends Aluno {
    private String registroProfissional;

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    @Override
    public String toString() {
        return id + "," + nome + "," + idade + "," + sexo + "," + curso + "," +  mensalidade + "," + matricula + "," + registroProfissional;
    }
}
