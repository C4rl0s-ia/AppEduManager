package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.service.LerArquivoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class MainViewController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Label lblStatus;

    @FXML
    public void initialize() {
        loadView("Welcome.fxml");
        lblStatus.setText("Bem-vindo ao AppEduManager!");
        // Carrega as listas de cada arquivo
        LerArquivoService.lerAlunos(Application.listaAluno);
        LerArquivoService.lerBolsistas(Application.listaBolsista);
        LerArquivoService.lerTecnicos(Application.listaTecnico);
        LerArquivoService.lerVisitantes(Application.listaVisitante);
        LerArquivoService.lerProfessores(Application.listaProfessor);
    }

    // --- Métodos de Navegação ---

    @FXML
    void handleHomeClick(ActionEvent event) {
        loadView("Welcome.fxml");
        lblStatus.setText("Página Inicial");
    }

    // --- CADASTROS ---
    @FXML void handleCadastrarProfessor(ActionEvent event) {
        loadView("professor-form-view.fxml");
        lblStatus.setText("Cadastrando novo Professor...");
    }

    @FXML void handleCadastrarAluno(ActionEvent event) {
        loadView("aluno-form-view.fxml");
        lblStatus.setText("Cadastrando novo Aluno...");
    }

    @FXML void handleCadastrarBolsista(ActionEvent event) {
        loadView("bolsista-form-view.fxml");
        lblStatus.setText("Cadastrando novo Bolsista...");
    }

    @FXML void handleCadastrarTecnico(ActionEvent event) {
        loadView("tecnico-form-view.fxml");
        lblStatus.setText("Cadastrando novo Técnico...");
    }

    @FXML void handleCadastrarVisitante(ActionEvent event) {
        loadView("visitante-form-view.fxml");
        lblStatus.setText("Cadastrando novo Visitante...");
    }

    // --- VISUALIZAÇÕES ---
    @FXML void handleVisualizarProfessor(ActionEvent event) {
        loadView("professor-visualizar-view.fxml");
        lblStatus.setText("Buscando Professores...");
    }

    @FXML void handleVisualizarAluno(ActionEvent event) {
        loadView("aluno-visualizar-view.fxml");
        lblStatus.setText("Buscando Alunos...");
    }

    @FXML void handleVisualizarBolsista(ActionEvent event) {
        loadView("bolsista-visualizar-view.fxml");
        lblStatus.setText("Buscando Bolsistas...");
    }

    @FXML void handleVisualizarTecnico(ActionEvent event) {
        loadView("tecnico-visualizar-view.fxml");
        lblStatus.setText("Buscando Técnicos...");
    }

    @FXML void handleVisualizarVisitante(ActionEvent event) {
        loadView("visitante-visualizar-view.fxml");
        lblStatus.setText("Buscando Visitantes...");
    }


    private void loadView(String fxmlFile) {
        String fxmlPath = "views/" + fxmlFile;

        try {
            URL resource = Application.class.getResource(fxmlPath);

            if (resource == null) {
                System.err.println("ERRO: Arquivo não encontrado: " + fxmlPath);
                lblStatus.setText("ERRO: Arquivo não encontrado: " + fxmlFile);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent view = loader.load();

            contentArea.getChildren().setAll(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao carregar a tela: " + fxmlFile);
        }
    }
}