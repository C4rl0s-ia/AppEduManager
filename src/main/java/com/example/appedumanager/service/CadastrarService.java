package com.example.appedumanager.service;

import java.util.ArrayList;
import com.example.appedumanager.model.*;
import com.example.appedumanager.util.IdUtils;

public class CadastrarService {
    public static void cadastrarProfessor (Professor professor, ArrayList<Professor> listaProfessor){
        String novoId = IdUtils.gerarIdUnico(listaProfessor, Professor::getId, "prf_");
        professor.setId(novoId);

        listaProfessor.add(professor);
        System.out.println("Professor salvo em sistema!!"); // (Idealmente, isso vira um 'return true')
    }
    public static void cadastrarAluno (Aluno aluno, ArrayList<Aluno> listaAluno) {
        String novoId = IdUtils.gerarIdUnico(listaAluno, Aluno::getId, "aln_");
        aluno.setId(novoId);

        listaAluno.add(aluno);
        System.out.println("Aluno salvo em sistema!!"); // (Idealmente, isso vira um 'return true')
    }
    public static void cadastrarBolsista (Bolsista bolsista, ArrayList<Bolsista> listaBolsista) {
        String novoId = IdUtils.gerarIdUnico(listaBolsista, Bolsista::getId, "bol_");
        bolsista.setId(novoId);

        listaBolsista.add(bolsista);
        System.out.println("Aluno bolsista salvo em sistema!!"); // (Idealmente, isso vira um 'return true')
    }
    public static void cadastrarTecnico (Tecnico tecnico, ArrayList<Tecnico> listaTecnico) {
        String novoId = IdUtils.gerarIdUnico(listaTecnico, Tecnico::getId, "tec_");
        tecnico.setId(novoId);

        listaTecnico.add(tecnico);
        System.out.println("Aluno técnico salvo em sistema!!"); // (Idealmente, isso vira um 'return true')
    }
    public static void cadastrarVisitante (Visitante visitante, ArrayList<Visitante> listaVisitante) {
        String novoId = IdUtils.gerarIdUnico(listaVisitante, Visitante::getId, "vis_");
        visitante.setId(novoId);

        listaVisitante.add(visitante);
        System.out.println("Visitante salvo em sistema!!"); // (Idealmente, isso vira um 'return true')
    }
}
