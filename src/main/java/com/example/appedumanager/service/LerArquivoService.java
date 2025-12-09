package com.example.appedumanager.service;

import com.example.appedumanager.model.*;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class LerArquivoService {

    private static final String DIR_PATH = "src/main/java/com/example/appedumanager/data";

    public static void lerProfessores(ArrayList<Professor> listaProfessor){
        listaProfessor.clear();
        Path caminho = Path.of(DIR_PATH, "professores_db.txt");

        try (BufferedReader br = Files.newBufferedReader(caminho)) {
            String line;
            while ((line = br.readLine()) != null) {
                Professor professor = converterLinhaEmProfesssor(line);
                listaProfessor.add(professor);
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler professores: " + e.getMessage());
        }
    }
    public static void lerAlunos(ArrayList<Aluno> listaAluno){
        listaAluno.clear();
        Path caminho = Path.of(DIR_PATH, "alunos_db.txt");

        try (BufferedReader br = Files.newBufferedReader(caminho)){
            String line;
            while ((line = br.readLine()) != null){
                Aluno aluno = converterLinhaEmAluno(line);
                listaAluno.add(aluno);
            }
        } catch (Exception e){
            System.err.println("Erro ao ler Alunos: " + e.getMessage());
        }

    }
    public static void lerBolsistas(ArrayList<Bolsista> listaBolsista){
        listaBolsista.clear();
        Path caminho = Path.of(DIR_PATH, "bolsistas_db.txt");

        try (BufferedReader br = Files.newBufferedReader(caminho)){
            String line;
            while ((line = br.readLine()) != null){
                Bolsista bolsista = converterLinhaEmBolsista(line);
                listaBolsista.add(bolsista);
            }
        } catch (Exception e){
            System.err.println("Erro ao ler Alunos Bolsistas: " + e.getMessage());
        }
    }
    public static void lerTecnicos(ArrayList<Tecnico> listaTecnico){
        listaTecnico.clear();
        Path caminho = Path.of(DIR_PATH, "tecnicos_db.txt");

        try (BufferedReader br = Files.newBufferedReader(caminho)){
            String line;
            while ((line = br.readLine()) != null){
                Tecnico tecnico = converterLinhaEmTecnico(line);
                listaTecnico.add(tecnico);
            }
        } catch (Exception e){
            System.err.println("Erro ao ler Alunos Tecnicos: " + e.getMessage());
        }
    }
    public static void lerVisitantes(ArrayList<Visitante> listaVisitante) {
        listaVisitante.clear();
        Path caminho = Path.of(DIR_PATH, "visitantes_db.txt");

        try (BufferedReader br = Files.newBufferedReader(caminho)){
            String line;
            while ((line = br.readLine()) != null){
                Visitante visitante = converterLinhaEmVisitante(line);
                listaVisitante.add(visitante);
            }
        } catch (Exception e){
            System.err.println("Erro ao ler Visitantes: " + e.getMessage());
        }
    }


    private static Professor converterLinhaEmProfesssor(String line){
        if (line != null){
            String[] partes = line.split(",");

            String id = partes[0];
            String nome = partes[1];
            int idade = Integer.parseInt(partes[2]);
            String sexo = partes[3];
            String especialidade = partes[4];
            float salario = Float.parseFloat(partes[5]);

            Professor prof = new Professor();
            prof.setId(id);
            prof.setNome(nome);
            prof.setSexo(sexo);
            prof.setIdade(idade);
            prof.setEspecialidade(especialidade);
            prof.setSalario(salario);

            return prof;
        }
        return null;
    }
    private static Aluno converterLinhaEmAluno(String line) {
        if (line != null){
            // Aqui pegamos cada parte e colocamos em um indice desse array de strings:
            String[] partes = line.split(","); // Quebra a linha onde tem ";"

            // Guardando os dados:
            String id = partes[0];
            String nome = partes[1];
            int idade = Integer.parseInt(partes[2]);
            String sexo = partes[3];
            String curso = partes[4];
            float mensalidade = Float.parseFloat(partes[5]);
            boolean matricula = Boolean.parseBoolean(partes[6]);

            // Criando o Objeto Aluno:
            Aluno aln = new Aluno();
            aln.setId(id);
            aln.setNome(nome);
            aln.setSexo(sexo);
            aln.setIdade(idade);
            aln.setCurso(curso);
            aln.setMensalidade(mensalidade);
            aln.setMatricula(matricula);

            // retorna o aluno completo para que o mesmo possa ser salvo dentro do arraylist:
            return aln;
        }
        return null;
    }
    private static Bolsista converterLinhaEmBolsista(String line){
        if (line != null){
            String[] partes = line.split(",");

            String id = partes[0];
            String nome = partes[1];
            int idade = Integer.parseInt(partes[2]);
            String sexo = partes[3];
            String curso = partes[4];
            float mensalidade = Float.parseFloat(partes[5]);
            boolean matricula = Boolean.parseBoolean(partes[6]);
            float bolsa = Float.parseFloat(partes[7]);

            Bolsista bols = new Bolsista();
            bols.setId(id);
            bols.setNome(nome);
            bols.setSexo(sexo);
            bols.setIdade(idade);
            bols.setCurso(curso);
            bols.setMensalidade(mensalidade);
            bols.setMatricula(matricula);
            bols.setBolsa(bolsa);

            return bols;
        }
        return null;
    }
    private static Tecnico converterLinhaEmTecnico(String line){
        if (line != null){
            String[] partes = line.split(",");

            String id = partes[0];
            String nome = partes[1];
            int idade = Integer.parseInt(partes[2]);
            String sexo = partes[3];
            String curso = partes[4];
            float mensalidade = Float.parseFloat(partes[5]);
            boolean matricula = Boolean.parseBoolean(partes[6]);
            String registroProfissional = partes[7];

            Tecnico tec = new Tecnico();
            tec.setId(id);
            tec.setNome(nome);
            tec.setSexo(sexo);
            tec.setIdade(idade);
            tec.setCurso(curso);
            tec.setMensalidade(mensalidade);
            tec.setMatricula(matricula);
            tec.setRegistroProfissional(registroProfissional);

            return tec;
        }
        return null;
    }
    private static Visitante converterLinhaEmVisitante(String line){
        if (line != null){
            String[] partes = line.split(",");

            String id = partes[0];
            String nome = partes[1];
            int idade = Integer.parseInt(partes[2]);
            String sexo = partes[3];

            Visitante visi = new Visitante();
            visi.setId(id);
            visi.setNome(nome);
            visi.setSexo(sexo);
            visi.setIdade(idade);

            return visi;
        }
        return null;
    }
}
