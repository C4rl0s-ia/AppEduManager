package com.example.appedumanager.service;

import com.example.appedumanager.model.*;

import java.util.ArrayList;

public class ExcluirService {
    public static void excluirProfessor(Professor professor, ArrayList<Professor> listaProfessor) {
        if (listaProfessor.remove(professor)) {
            System.out.println("Professor removido do sistema: " + professor.getNome());
        }
    }

    public static void excluirAluno(Aluno aluno, ArrayList<Aluno> listaAluno){
        if (listaAluno.remove(aluno)){
            System.out.println("Aluno removido do sistema: " + aluno.getNome());
        }
    }

    public static void excluirBolsista(Bolsista bolsista, ArrayList<Bolsista> listaBolsista) {
        if (listaBolsista.remove(bolsista)) {
            System.out.println("Bolsista removido do sistema: " + bolsista.getNome());
        }
    }

    public static void excluirTecnico(Tecnico tecnico, ArrayList<Tecnico> listaTecnico) {
        if (listaTecnico.remove(tecnico)) {
            System.out.println("Técnico removido do sistema: " + tecnico.getNome());
        }
    }

    public static void excluirVisitante(Visitante visitante, ArrayList<Visitante> listaVisitante) {
        if (listaVisitante.remove(visitante)) {
            System.out.println("Visitante removido do sistema: " + visitante.getNome());
        }
    }

}
