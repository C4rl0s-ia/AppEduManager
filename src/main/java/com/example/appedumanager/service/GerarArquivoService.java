package com.example.appedumanager.service;

import com.example.appedumanager.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

// Gera o arquivo / reescreve o arquivo existente:

public class GerarArquivoService {
    public static void GuardarProfessores(ArrayList<Professor> listaProfessor){
        salvarListaGenerica(listaProfessor,"professores_db.txt");
    }
    public static void GuardarAluno(ArrayList<Aluno> listaAluno){
        salvarListaGenerica(listaAluno,"alunos_db.txt");
    }

    public static void GuardarBolsista(ArrayList<Bolsista> listaBolsista){
        salvarListaGenerica(listaBolsista,"bolsistas_db.txt");
    }
    public static void GuardarTecnico(ArrayList<Tecnico> listaTecnico){
        salvarListaGenerica(listaTecnico,"tecnicos_db.txt");
    }
    public static void GuardarVisitante(ArrayList<Visitante> listaVisitante) {
        salvarListaGenerica(listaVisitante,"visitantes_db.txt");
    }

    private static <T> void salvarListaGenerica(List<T> list, String nomeArquivo){
        // Aqui é declarado o caminho e o arquivo, o caminho, vai obter a informação toda como se lê-se "BancoDeDados/nomeArquivo";
        Path caminho = Path.of("src","main","java","com","example","appedumanager","data", nomeArquivo); // path.of ajuda para lidar com os arquivos com / e com \ faz a inteligente naturalmente sem ações manuais
        try {
            // Aqui ele valida se a pasta existe, se não ele cria o mesmo:
            if (caminho.getParent() != null) {
                Files.createDirectories(caminho.getParent());
            }

            // Escreve em um StringBuilder a String (texto) completo na var sb para que possamos passar para o arquivo.txt
            StringBuilder sb = new StringBuilder();
            for (T obj : list){
                sb.append(obj.toString()).append(System.lineSeparator());
            }

            // Aqui fazemos a realização da transcrição do sb.toString() dentro do arquivo indicado na variavel nomeArquivo e o mesmo é criado se não existe ou reescrito se já existir.
            Files.writeString(caminho, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Arquivo salvo em: " + caminho.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar " + nomeArquivo, e);
        }


    }

}
