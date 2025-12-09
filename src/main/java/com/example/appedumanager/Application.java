package com.example.appedumanager;

import com.example.appedumanager.model.*; // Importa todas as suas classes de modelo
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.image.Image;

public class Application extends javafx.application.Application {

    // --- BANCO DE DADOS EM MEMÓRIA (Listas Globais) ---
    public static ArrayList<Professor> listaProfessor = new ArrayList<>();
    public static ArrayList<Aluno> listaAluno = new ArrayList<>();
    public static ArrayList<Bolsista> listaBolsista = new ArrayList<>();
    public static ArrayList<Tecnico> listaTecnico = new ArrayList<>();
    public static ArrayList<Visitante> listaVisitante = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        // ATUALIZADO: Carrega a nova "MainView" robusta
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("views/main-view.fxml"));

        //ícone do aplicativo
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/appedumanager/views/image/icon-logo.png")));
        stage.getIcons().add(appIcon);

        // Ajusta o tamanho da cena e o título da janela
        Scene scene = new Scene(fxmlLoader.load(), 900, 600); // Tamanho ajustado
        stage.setTitle("AppEduManager - Sistema de Gestão"); // Título ajustado
        stage.setScene(scene);
        stage.show();
    }

}