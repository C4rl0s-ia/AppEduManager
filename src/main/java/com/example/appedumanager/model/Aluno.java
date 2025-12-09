package com.example.appedumanager.model;

public class Aluno extends Pessoa {

    protected float mensalidade;
    protected boolean matricula;
    protected String curso;

    // --- GETTERS E SETTERS ---

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public boolean isMatricula() {
        return matricula;
    }

    public void setMatricula(boolean matricula) {
        this.matricula = matricula;
    }

    public float getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(float mensalidade) {
        this.mensalidade = mensalidade;
    }

    @Override
    public String toString() {
        return id + "," + nome + "," + idade + "," + sexo + "," + curso + "," +  mensalidade + "," + matricula;
    }
}