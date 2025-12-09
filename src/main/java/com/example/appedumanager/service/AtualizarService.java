package com.example.appedumanager.service;

import com.example.appedumanager.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AtualizarService {

    public static void editarProfessor(Professor professor, ArrayList<Professor> listaProfessor) {
        // O índice da lista continua sendo um número inteiro (int)
        int index = getIndexById(listaProfessor, professor.getId());

        if (index != -1) {
            listaProfessor.set(index, professor);
            System.out.println("Dados do professor " + professor.getNome() + " atualizados com sucesso.");
        } else {
            System.out.println("Erro: Professor não encontrado na lista.");
        }
    }

    public static void editarAluno(Aluno aluno, ArrayList<Aluno> listaAluno) {
        int index = getIndexById(listaAluno, aluno.getId());

        if (index != -1) {
            listaAluno.set(index, aluno);
            System.out.println("Dados do aluno " + aluno.getNome() + " atualizados com sucesso.");
        }
    }

    public static void editarBolsista(Bolsista bolsista, ArrayList<Bolsista> listaBolsista){
        int index = getIndexById(listaBolsista, bolsista.getId());

        if (index != -1){
            listaBolsista.set(index, bolsista);
            System.out.println("Dados do bolsista " + bolsista.getNome() + " atualizados com sucesso.");
        }
    }

    public static void editarTecnico (Tecnico tecnico, ArrayList<Tecnico> listaTecnico){
        int index = getIndexById(listaTecnico, tecnico.getId());

        if (index != -1){
            listaTecnico.set(index, tecnico);
            System.out.println("Dados do técnico " + tecnico.getNome() + " atualizados com sucesso.");
        }
    }

    public static void editarVisitante(Visitante visitante, ArrayList<Visitante> listaVisistante){
        int index = getIndexById(listaVisistante, visitante.getId());

        if (index != -1){
            listaVisistante.set(index, visitante);
            System.out.println("Dados do visitante " + visitante.getNome() + " atualizados com sucesso.");
        }
    }

    // ---------------------------------------------------------
    // MÉTODO AJUSTADO PARA STRING
    // ---------------------------------------------------------
    private static int getIndexById(List<? extends Pessoa> lista, String id) {
        // String id: O parâmetro agora é texto

        return IntStream.range(0, lista.size())
                // .equals: Comparação correta para Strings
                .filter(i -> lista.get(i).getId().equals(id))
                .findFirst()
                .orElse(-1);
    }
}