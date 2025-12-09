package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Bolsista;
import com.example.appedumanager.service.AtualizarService;
import com.example.appedumanager.service.BuscarService;
import com.example.appedumanager.service.ExcluirService;
import com.example.appedumanager.service.GerarArquivoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static com.example.appedumanager.util.AlertUtils.showCustomAlert;

public class VisualizacaoBolsistaController {
    @FXML private TextField txtBusca;
    @FXML private Label lblStatusBusca;
    @FXML private GridPane gridResultados;
    @FXML private HBox boxBotoes;

    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private TextField txtCurso;
    @FXML private TextField txtMensalidade;
    @FXML private TextField txtBolsa;

    @FXML private CheckBox chkMatricula;

    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;

    private Bolsista bolsistaAtual;
    private boolean modoEdicao = false;

    private final String CLASSE_LEITURA = "campo-somente-leitura";

    @FXML
    public void initialize() {
        txtBusca.setOnAction(event -> handleBuscar(event));
    }

    @FXML
    protected void handleBuscar(ActionEvent event){
        String idDeBusca = txtBusca.getText();

        if (idDeBusca.isEmpty()){
            showCustomAlert(Alert.AlertType.ERROR, "Sem valor de pesquisa", "Preencha o campo de busca");
            return;
        }

        try {
            bolsistaAtual = BuscarService.buscarBolsistaPorId(idDeBusca, Application.listaBolsista);

            if (bolsistaAtual != null){
                resetarVisualizacao(); // Limpa estilos antigos
                preencherCampos(bolsistaAtual);
                mostrarResultados(true);
                lblStatusBusca.setText("Aluno Bolsista encontrado!");
                lblStatusBusca.setStyle("-fx-text-fill: green;");
            } else {
                showCustomAlert(Alert.AlertType.ERROR, "NotFound", "Bolsista não encontrado");
                lblStatusBusca.setText("Aluno Bolsista não encontrado!");
                lblStatusBusca.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "Erro", "Valor informado é inválido");
        }
    }

    @FXML protected void handleEditar(ActionEvent event){
        if (bolsistaAtual == null) return;

        if (!modoEdicao){
            habilitarEdicao(true);
            btnEditar.setText("Salvar");
            modoEdicao = true;
            lblStatusBusca.setText("Edição habilitada.");
            lblStatusBusca.setStyle("-fx-text-fill: green;");
        } else {
            salvarAlteracoes();
        }
    }

    @FXML protected void handleExcluir(ActionEvent event){
        if (bolsistaAtual != null) {
            ExcluirService.excluirBolsista(bolsistaAtual, Application.listaBolsista);
            GerarArquivoService.GuardarBolsista(Application.listaBolsista);
            limparCampos();
            mostrarResultados(false);
            bolsistaAtual = null;
            lblStatusBusca.setText("Aluno excluído.");
            lblStatusBusca.setStyle("-fx-text-fill: black;");
        }
    }

    private void preencherCampos(Bolsista bolsista) {
        txtNome.setText(bolsista.getNome());
        txtIdade.setText(String.valueOf(bolsista.getIdade()));
        txtSexo.setText(bolsista.getSexo());
        txtCurso.setText(bolsista.getCurso());
        txtMensalidade.setText(String.valueOf(bolsista.getMensalidade()));
        txtBolsa.setText(String.valueOf(bolsista.getBolsa()));

        // --- MUDANÇA 3: Usamos setSelected para marcar/desmarcar ---
        chkMatricula.setSelected(bolsista.isMatricula());
        atualizarTextoCheckbox(); // Atualiza o texto visual (Ativa/Inativa)
    }

    private void habilitarEdicao(boolean habilitar) {
        // TextFields usam setEditable
        txtNome.setEditable(habilitar);
        txtIdade.setEditable(habilitar);
        txtSexo.setEditable(habilitar);
        txtCurso.setEditable(habilitar);
        txtMensalidade.setEditable(habilitar);
        txtBolsa.setEditable(habilitar);

        chkMatricula.setDisable(!habilitar); // para aparecer ou desaparecer.

        TextField[] camposTexto = {txtNome, txtIdade, txtSexo, txtCurso, txtMensalidade, txtBolsa};

        // Aplica estilo nos TextFields
        for (TextField campo : camposTexto) {
            if (habilitar) {
                campo.getStyleClass().remove(CLASSE_LEITURA);
            } else {
                if (!campo.getStyleClass().contains(CLASSE_LEITURA)) {
                    campo.getStyleClass().add(CLASSE_LEITURA);
                }
            }
        }

        // Aplica estilo no CheckBox separadamente
        if (habilitar) {
            chkMatricula.getStyleClass().remove(CLASSE_LEITURA);
        } else {
            if (!chkMatricula.getStyleClass().contains(CLASSE_LEITURA)) {
                chkMatricula.getStyleClass().add(CLASSE_LEITURA);
            }
        }

        if(habilitar) {
            chkMatricula.setOnAction(e -> atualizarTextoCheckbox());
        } else {
            chkMatricula.setOnAction(null);
        }
    }

    private void salvarAlteracoes() {
        try {
            bolsistaAtual.setNome(txtNome.getText());
            bolsistaAtual.setIdade(Integer.parseInt(txtIdade.getText()));
            bolsistaAtual.setSexo(txtSexo.getText());
            bolsistaAtual.setCurso(txtCurso.getText());
            bolsistaAtual.setMensalidade(Float.parseFloat(txtMensalidade.getText()));
            bolsistaAtual.setBolsa(Float.parseFloat(txtBolsa.getText()));

            // Captura direta do boolean (Simples e Seguro) ---
            bolsistaAtual.setMatricula(chkMatricula.isSelected());

            AtualizarService.editarAluno(bolsistaAtual, Application.listaAluno);

            habilitarEdicao(false);
            btnEditar.setText("Editar");
            modoEdicao = false;

            // Re-atualiza para garantir consistência visual
            preencherCampos(bolsistaAtual);

            lblStatusBusca.setText("Aluno atualizado com sucesso!");
            lblStatusBusca.setStyle("-fx-text-fill: green;");

        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "Erro", "Verifique os campos numéricos.");
        } catch (Exception e) {
            showCustomAlert(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar: " + e.getMessage());
        }
    }

    private void resetarVisualizacao() {
        // 1. Reset dos TextFields
        TextField[] campos = {txtNome, txtIdade, txtSexo, txtCurso, txtMensalidade, txtBolsa};
        for (TextField campo : campos) {
            campo.setEditable(false);
            campo.getStyleClass().remove(CLASSE_LEITURA);
        }

        // 2. Reset do CheckBox
        chkMatricula.setDisable(true); // Trava
        chkMatricula.getStyleClass().remove(CLASSE_LEITURA); // Tira o verde
        chkMatricula.setSelected(false); // Limpa seleção

        modoEdicao = false;
        btnEditar.setText("Editar");
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
        txtBolsa.clear();
        chkMatricula.setSelected(false); // Limpa checkbox
        txtBusca.clear();
    }

    // Método extra para deixar o visual bonito (Texto muda conforme clica)
    private void atualizarTextoCheckbox() {
        if (chkMatricula.isSelected()) {
            chkMatricula.setText("Matrícula Ativa");
        } else {
            chkMatricula.setText("Matrícula Inativa");
        }
    }
}