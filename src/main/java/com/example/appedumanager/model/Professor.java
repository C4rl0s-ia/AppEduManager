package com.example.appedumanager.model;

public class Professor extends Pessoa{

    private String especialidade;
    private float salario;

    // Métodos especiais de acesso.
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public float getSalario() {
        return salario;
    }
    public void setSalario(float salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return id + "," + nome + "," + idade + "," + sexo + "," + especialidade + "," +  salario;
    }
}
