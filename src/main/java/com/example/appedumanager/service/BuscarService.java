package com.example.appedumanager.service;

import com.example.appedumanager.model.*;

import java.util.ArrayList;

public class BuscarService {
    public static Professor buscarProfessorPorId(String id, ArrayList<Professor> listaProfessor){
        for (Professor prof : listaProfessor){
            if (prof.getId().equals(id)) {
                return prof;
            }
        }
        return null;
    }
    public static Aluno buscarAlunoPorId(String id, ArrayList<Aluno> listaAluno){
        for (Aluno aluno : listaAluno){
            if (aluno.getId().equals(id)) {
                return aluno;
            }
        }
        return null;
    }
    public static Bolsista buscarBolsistaPorId(String id, ArrayList<Bolsista> listaBolsista){
        for (Bolsista bolsista : listaBolsista){
            if (bolsista.getId().equals(id)) {
                return bolsista;
            }
        }
        return null;
    }
    public static Tecnico buscarTecnicoPorId(String id, ArrayList<Tecnico> listaTecnico){
        for (Tecnico tecnico : listaTecnico){
            if (tecnico.getId().equals(id)) {
                return tecnico;
            }
        }
        return null;
    }
    public static Visitante buscarVisitantePorId(String id, ArrayList<Visitante> listaVisitante){
        for (Visitante visitante : listaVisitante){
            if (visitante.getId().equals(id)) {
                return visitante;
            }
        }
        return null;
    }

}
