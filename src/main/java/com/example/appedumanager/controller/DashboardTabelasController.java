package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.*;
import com.example.appedumanager.service.ExcluirService;

import com.example.appedumanager.service.ExportarCsvService;
import com.example.appedumanager.service.GerarArquivoService;
import com.example.appedumanager.util.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.function.Consumer;

public class DashboardTabelasController {

    // ---------------- TABELA DE ALUNOS ----------------
    @FXML private TableView<Aluno> tabelaAlunos;

    @FXML private TableColumn<Aluno, String> colAlunoId;
    @FXML private TableColumn<Aluno, String> colAlunoNome;
    @FXML private TableColumn<Aluno, Integer> colAlunoIdade;
    @FXML private TableColumn<Aluno, String> colAlunoSexo;
    @FXML private TableColumn<Aluno, String> colAlunoCurso;
    @FXML private TableColumn<Aluno, Boolean> colAlunoMatricula;
    @FXML private TableColumn<Aluno, Float> colAlunoMensalidade;
    @FXML private TableColumn<Aluno, Void> colAlunoAcoes;

    // ---------------- TABELA DE PROFESSORES ----------------
    @FXML private TableView<Professor> tabelaProfessores;

    @FXML private TableColumn<Professor, String> colProfessorId;
    @FXML private TableColumn<Professor, String> colProfessorNome;
    @FXML private TableColumn<Professor, Integer> colProfessorIdade;
    @FXML private TableColumn<Professor, String> colProfessorSexo;
    @FXML private TableColumn<Professor, String> colProfessorEspecialidade;
    @FXML private TableColumn<Professor, Float> colProfessorSalario;
    @FXML private TableColumn<Professor, Void> colProfessorAcoes;

    // ----------------  TABELA DE BOLSISTAS  ----------------
    @FXML private TableView<Bolsista> tabelaBolsistas;

    @FXML private TableColumn<Bolsista, String> colBolsistaId;
    @FXML private TableColumn<Bolsista, String> colBolsistaNome;
    @FXML private TableColumn<Bolsista, Integer> colBolsistaIdade;
    @FXML private TableColumn<Bolsista, String> colBolsistaSexo;
    @FXML private TableColumn<Bolsista, String> colBolsistaCurso;
    @FXML private TableColumn<Bolsista, Boolean> colBolsistaMatricula;
    @FXML private TableColumn<Bolsista, Float> colBolsistaMensalidade;
    @FXML private TableColumn<Bolsista, Float> colBolsistaBolsa;
    @FXML private TableColumn<Bolsista, Void> colBolsistaAcoes;


    // ----------------  TABELA DE TECNICOS   ----------------
    @FXML private TableView<Tecnico> tabelaTecnicos;

    @FXML private TableColumn<Tecnico, String> colTecnicoId;
    @FXML private TableColumn<Tecnico, String> colTecnicoNome;
    @FXML private TableColumn<Tecnico, Integer> colTecnicoIdade;
    @FXML private TableColumn<Tecnico, String> colTecnicoSexo;
    @FXML private TableColumn<Tecnico, String> colTecnicoCurso;
    @FXML private TableColumn<Tecnico, Boolean> colTecnicoMatricula;
    @FXML private TableColumn<Tecnico, Float> colTecnicoMensalidade;
    @FXML private TableColumn<Tecnico, String> colTecnicoRegistroProfissional;
    @FXML private TableColumn<Tecnico, Void> colTecnicoAcoes;


    // ---------------- TABELA DE VISISTANTES ----------------
    @FXML private TableView<Visitante> tabelaVisistantes;

    @FXML private TableColumn<Visitante, String> colVisitanteId;
    @FXML private TableColumn<Visitante, String> colVisitanteNome;
    @FXML private TableColumn<Visitante, Integer> colVisitanteIdade;
    @FXML private TableColumn<Visitante, String> colVisitanteSexo;
    @FXML private TableColumn<Visitante, Void> colVisistanteAcoes;




    // Aplicado uma ObservableList para que sempre que houver qualquer atualização na lista Aluno,
    // ele atualize os dados e sejam informados corretamente dentro da tabela
    private ObservableList<Aluno> obsListaAlunos;
    private ObservableList<Professor> obsListaProfessores;
    private ObservableList<Bolsista> obsListaBolsistas;
    private ObservableList<Tecnico> obsListaTecnico;
    private ObservableList<Visitante> obsListaVisitante;

    @FXML
    public void initialize() {
        carregarDadosReais(); // Carrega os dados da lista, atualizando com as informações
        configurarTabelaAlunos(); // Configuração das informações da lista do Aluno
        configurarTabelaProfessores();
        configurarTabelaBolsistas();
        configurarTabelaTecnicos();
        configurarTabelaVisitante();
    }

    private void carregarDadosReais() {
        obsListaAlunos = FXCollections.observableList(Application.listaAluno);
        obsListaProfessores = FXCollections.observableList(Application.listaProfessor);
        obsListaBolsistas = FXCollections.observableList(Application.listaBolsista);
        obsListaTecnico = FXCollections.observableList(Application.listaTecnico);
        obsListaVisitante = FXCollections.observableList(Application.listaVisitante);
    }

    // Configurações das tabelas
    private void configurarTabelaAlunos() {
        colAlunoId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colAlunoNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colAlunoIdade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdade()));
        colAlunoSexo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexo()));
        colAlunoCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurso()));
        colAlunoMensalidade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMensalidade()));
        colAlunoMensalidade.setCellFactory(TableUtils.formatarMoeda());
        colAlunoMatricula.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isMatricula()));
        colAlunoMatricula.setCellFactory(TableUtils.formatarBoolean("Ativa", "Inativa"));

        adicionarBotaoExcluir(colAlunoAcoes, aluno -> {
            ExcluirService.excluirAluno(aluno, Application.listaAluno);
            GerarArquivoService.GuardarAluno(Application.listaAluno);
            System.out.println("Aluno Excluído " + aluno.getNome());
        });

        tabelaAlunos.setItems(obsListaAlunos);
    }
    private void configurarTabelaProfessores() {
        colProfessorId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colProfessorNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colProfessorIdade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdade()));
        colProfessorSexo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexo()));
        colProfessorEspecialidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidade()));
        colProfessorSalario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSalario()));
        colProfessorSalario.setCellFactory(TableUtils.formatarMoeda());
        adicionarBotaoExcluir(colProfessorAcoes, (professor) -> {
            ExcluirService.excluirProfessor(professor, Application.listaProfessor);
            GerarArquivoService.GuardarProfessores(Application.listaProfessor);
            System.out.println("Professor excluído " + professor.getNome());
        });

        tabelaProfessores.setItems(obsListaProfessores);
    }
    private void configurarTabelaBolsistas(){
        colBolsistaId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colBolsistaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colBolsistaIdade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdade()));
        colBolsistaSexo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexo()));
        colBolsistaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurso()));
        colBolsistaMensalidade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMensalidade()));
        colBolsistaMensalidade.setCellFactory(TableUtils.formatarMoeda());
        colBolsistaMatricula.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isMatricula()));
        colBolsistaMatricula.setCellFactory(TableUtils.formatarBoolean("Ativa", "Inativa"));

        colBolsistaBolsa.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBolsa()));
        colBolsistaBolsa.setCellFactory(TableUtils.formatarPorcentagem());

        adicionarBotaoExcluir(colBolsistaAcoes, bolsista -> {
            ExcluirService.excluirBolsista(bolsista, Application.listaBolsista);
            GerarArquivoService.GuardarBolsista(Application.listaBolsista);
            System.out.println("Bolsista excluído " + bolsista.getNome());
        });

        tabelaBolsistas.setItems(obsListaBolsistas);
    }
    private void configurarTabelaTecnicos(){
        colTecnicoId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colTecnicoNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colTecnicoIdade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdade()));
        colTecnicoSexo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexo()));
        colTecnicoCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurso()));
        colTecnicoMensalidade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMensalidade()));
        colTecnicoMensalidade.setCellFactory(TableUtils.formatarMoeda());
        colTecnicoMatricula.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isMatricula()));
        colTecnicoMatricula.setCellFactory(TableUtils.formatarBoolean("Ativa", "Inativa"));
        colTecnicoRegistroProfissional.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegistroProfissional()));

        adicionarBotaoExcluir(colTecnicoAcoes, tecnico -> {
            ExcluirService.excluirTecnico(tecnico, Application.listaTecnico);
            GerarArquivoService.GuardarTecnico(Application.listaTecnico);
            System.out.println("Tecnico excluído " + tecnico.getNome());
        });

        tabelaTecnicos.setItems(obsListaTecnico);
    }
    private void configurarTabelaVisitante(){
        colVisitanteId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colVisitanteNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colVisitanteIdade.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdade()));
        colVisitanteSexo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexo()));

        adicionarBotaoExcluir(colVisistanteAcoes, visitante -> {
            ExcluirService.excluirVisitante(visitante, Application.listaVisitante);
            GerarArquivoService.GuardarVisitante(Application.listaVisitante);
            System.out.println("Visitante Excluído: " + visitante.getNome());
        });

        tabelaVisistantes.setItems(obsListaVisitante);
    }
    // <T> significa que este método aceita qualquer Tipo (Aluno, Professor, Tecnico...)

    @FXML
    public void handleExportarAlunos(ActionEvent event) {
        // Tenta pegar a janela pai da tabela para abrir o FileChooser nela
        ExportarCsvService.exportarTabelaParaCsv(tabelaAlunos, tabelaAlunos.getScene().getWindow());
    }

    @FXML
    public void handleExportarProfessores(ActionEvent event) {
        ExportarCsvService.exportarTabelaParaCsv(tabelaProfessores, tabelaProfessores.getScene().getWindow());
    }

    @FXML
    public void handleExportarTecnicos(ActionEvent event) {
        ExportarCsvService.exportarTabelaParaCsv(tabelaTecnicos, tabelaTecnicos.getScene().getWindow());
    }

    @FXML
    public void handleExportarBolsistas(ActionEvent event) {
        ExportarCsvService.exportarTabelaParaCsv(tabelaBolsistas, tabelaBolsistas.getScene().getWindow());
    }

    @FXML
    public void handleExportarVisitantes(ActionEvent event) {
        ExportarCsvService.exportarTabelaParaCsv(tabelaVisistantes, tabelaVisistantes.getScene().getWindow());
    }

    private <T> void adicionarBotaoExcluir(TableColumn<T, Void> coluna, Consumer<T> acaoExcluir) {
        coluna.setCellValueFactory(param -> new SimpleObjectProperty<>(null));
        Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Excluir");

                    {
                        btn.getStyleClass().add("btn-excluir-tabela");
                        btn.setOnAction((event) -> {
                            T item = getTableView().getItems().get(getIndex());

                            acaoExcluir.accept(item);

                            getTableView().refresh();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            setAlignment(javafx.geometry.Pos.CENTER);
                        }
                    }
                };
            }
        };

        coluna.setCellFactory(cellFactory);
    }
}