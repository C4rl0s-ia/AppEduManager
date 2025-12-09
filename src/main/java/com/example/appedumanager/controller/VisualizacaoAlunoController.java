package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Aluno;
import com.example.appedumanager.service.AtualizarService;
import com.example.appedumanager.service.BuscarService;
import com.example.appedumanager.service.ExcluirService;

import com.example.appedumanager.service.GerarArquivoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static com.example.appedumanager.util.AlertUtils.showCustomAlert;

public class VisualizacaoAlunoController {

    // --- Elementos da Interface (Vêm do FXML) ---
    @FXML private TextField txtBusca;
    @FXML private Label lblStatusBusca;

    @FXML private GridPane gridResultados;
    @FXML private HBox boxBotoes;

    // Campos de Dados do Aluno
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private TextField txtCurso;
    @FXML private TextField txtMensalidade;
    @FXML private TextField txtMatricula;

    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;

    // --- Variáveis de Controle ---
    private Aluno alunoAtual;
    private boolean modoEdicao = false;

    @FXML
    public void initialize() {
        txtBusca.setOnAction(event -> handleBuscar(event));
    }

    // --- AÇÃO: BUSCAR ---
    @FXML
    protected void handleBuscar(ActionEvent event) {
        String idTexto = txtBusca.getText();

        if (idTexto.isEmpty()) {
            showCustomAlert(Alert.AlertType.ERROR, "Sem valor de pesquisa", "Preencha o campo de busca");
            return;
        }

        try {
            // Busca na lista global da Application
            alunoAtual = BuscarService.buscarAlunoPorId(idTexto, Application.listaAluno);

            resetarVisualizacao();

            if (alunoAtual != null) {
                preencherCampos(alunoAtual);
                mostrarResultados(true);
                lblStatusBusca.setText("Aluno encontrado!");
                lblStatusBusca.setStyle("-fx-text-fill: green;");
            } else {
                mostrarResultados(false);
                lblStatusBusca.setText("Nenhum aluno encontrado com ID " + idTexto);
                lblStatusBusca.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "Valor inválido", "Digite um ID válido e númerico");
        }
    }

    // --- AÇÃO: EDITAR / SALVAR ---
    @FXML
    protected void handleEditar(ActionEvent event) {
        if (alunoAtual == null) return;

        if (!modoEdicao) {
            // Entrar no modo de edição
            habilitarEdicao(true);
            btnEditar.setText("Salvar");
            modoEdicao = true;
            lblStatusBusca.setText("Edição habilitada. Altere os campos e clique em Salvar.");
        } else {
            // Tentar salvar as alterações
            salvarAlteracoes();
        }
    }

    private void salvarAlteracoes() {
        try {
            // 1. Atualiza o objeto em memória com os dados da tela
            alunoAtual.setNome(txtNome.getText());
            alunoAtual.setIdade(Integer.parseInt(txtIdade.getText()));
            alunoAtual.setSexo(txtSexo.getText());
            alunoAtual.setCurso(txtCurso.getText());
            alunoAtual.setMensalidade(Float.parseFloat(txtMensalidade.getText()));

            // Lógica para converter Texto em Boolean (Matrícula)
            // Aceita "true", "sim", "ativa" como verdadeiro
            String matTexto = txtMatricula.getText().toLowerCase();
            boolean isMatriculado = matTexto.contains("sim") || matTexto.contains("true") || matTexto.contains("ativa");
            alunoAtual.setMatricula(isMatriculado);

            // 2. Chama o Service para persistir/validar a atualização na lista
            AtualizarService.editarAluno(alunoAtual, Application.listaAluno);

            // 3. Finaliza edição
            habilitarEdicao(false);
            btnEditar.setText("Editar");
            modoEdicao = false;

            // Atualiza a visualização para garantir formatação (ex: matrícula volta a ser "Ativa" em vez de "sim")
            preencherCampos(alunoAtual);

            lblStatusBusca.setText("Aluno atualizado com sucesso!");
            lblStatusBusca.setStyle("-fx-text-fill: green;");

        } catch (NumberFormatException e) {
            lblStatusBusca.setText("Erro: Verifique os campos numéricos (Idade/Mensalidade).");
            lblStatusBusca.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            lblStatusBusca.setText("Erro ao atualizar: " + e.getMessage());
            lblStatusBusca.setStyle("-fx-text-fill: red;");
        }
    }

    // --- AÇÃO: EXCLUIR ---
    @FXML
    protected void handleExcluir(ActionEvent event) {
        if (alunoAtual != null) {
            // Chama o serviço de exclusão criado anteriormente
            ExcluirService.excluirAluno(alunoAtual, Application.listaAluno);
            GerarArquivoService.GuardarAluno(Application.listaAluno);

            // Limpa a tela
            limparCampos();
            mostrarResultados(false);
            alunoAtual = null;

            lblStatusBusca.setText("Aluno excluído do sistema.");
            lblStatusBusca.setStyle("-fx-text-fill: black;");
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private void preencherCampos(Aluno a) {
        txtNome.setText(a.getNome());
        txtIdade.setText(String.valueOf(a.getIdade()));
        txtSexo.setText(a.getSexo());
        txtCurso.setText(a.getCurso());
        txtMensalidade.setText(String.valueOf(a.getMensalidade()));

        // Converte o boolean para texto amigável
        txtMatricula.setText(a.isMatricula() ? "Ativa" : "Inativa");
    }

    private void habilitarEdicao(boolean habilitar) {
        txtNome.setEditable(habilitar);
        txtIdade.setEditable(habilitar);
        txtSexo.setEditable(habilitar);
        txtCurso.setEditable(habilitar);
        txtMensalidade.setEditable(habilitar);
        txtMatricula.setEditable(habilitar);

        // Nomes das classes CSS que controlam a aparência
        final String CLASSE_LEITURA = "campo-somente-leitura";
        final String CLASSE_DADO = "result-data";

        // Cria um array dos TextFields para facilitar a iteração
        TextField[] campos = {txtNome, txtIdade, txtSexo, txtCurso, txtMensalidade, txtMatricula};

        for (TextField campo : campos) {
            if (habilitar) {
                // Modo Editável: Remove o estilo de leitura.
                // O estilo padrão (branco/preto) será aplicado pelo CSS principal.
                campo.getStyleClass().remove(CLASSE_LEITURA);
                campo.getStyleClass().add(CLASSE_DADO); // Garante que o estilo base esteja lá
            } else {
                // Modo Leitura: Aplica o estilo cinza/escuro.
                campo.getStyleClass().add(CLASSE_LEITURA);
                campo.getStyleClass().remove(CLASSE_DADO); // Remove a classe de dado, se necessário
            }
        }
    }

    private void mostrarResultados(boolean mostrar) {
        gridResultados.setVisible(mostrar);
        boxBotoes.setVisible(mostrar);
    }

    private void limparCampos() {
        txtNome.clear();
        txtIdade.clear();
        txtSexo.clear();
        txtCurso.clear();
        txtMensalidade.clear();
        txtMatricula.clear();
        txtBusca.clear();
    }

    private void resetarVisualizacao() {
        // Lista de todos os campos
        TextField[] campos = {txtNome, txtIdade, txtSexo, txtCurso, txtMensalidade, txtMatricula};

        // Nome da classe que deixa verde
        final String CLASSE_LEITURA = "campo-somente-leitura";

        for (TextField campo : campos) {
            // 1. Garante que não dá para editar (caso o usuário tenha esquecido o modo edição aberto)
            campo.setEditable(false);

            // 2. REMOVE a cor verde (volta para a cor original do CSS .text-field)
            campo.getStyleClass().remove(CLASSE_LEITURA);
        }

        // Reseta botões e estados internos
        modoEdicao = false;
        btnEditar.setText("Editar");
    }

}