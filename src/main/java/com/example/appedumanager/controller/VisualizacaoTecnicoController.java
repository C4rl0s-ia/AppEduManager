package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Tecnico;
import com.example.appedumanager.service.AtualizarService;
import com.example.appedumanager.service.BuscarService;
import com.example.appedumanager.service.ExcluirService;
import com.example.appedumanager.service.GerarArquivoService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;

import static com.example.appedumanager.util.AlertUtils.showCustomAlert;

public class VisualizacaoTecnicoController {
    // --- Elementos da Interface (Vêm do FXML) ---
    @FXML
    private TextField txtBusca;
    @FXML private Label lblStatusBusca;

    @FXML private GridPane gridResultados;
    @FXML private HBox boxBotoes;

    // Campos de Dados do Aluno Tecnico
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private TextField txtCurso;
    @FXML private TextField txtMensalidade;
    @FXML private CheckBox chkMatricula;
    @FXML private TextField txtRegistro;

    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;

    // --- Variáveis de Controle ---
    private Tecnico tecnicoAtual;
    private boolean modoEdicao = false;

    private final String CLASSE_LEITURA = "campo-somente-leitura";

    @FXML
    public void initialize() {
        txtBusca.setOnAction(event -> handleBuscar(event));
    }

    // Busca:
    @FXML protected void handleBuscar(ActionEvent event){
        String idBusca = txtBusca.getText();

        if (idBusca.isEmpty()){
            showCustomAlert(Alert.AlertType.ERROR, "ID não informado", "Informe um ID para a busca ser possível");
            lblStatusBusca.setText("ID não informado. Informar um ID existente e válido");
            lblStatusBusca.setStyle("-fx-text-fill: red;");
            return;
        }

        // Classe de busca (regra da busca)
        try{
            tecnicoAtual = BuscarService.buscarTecnicoPorId(idBusca, Application.listaTecnico);

            if ( tecnicoAtual != null) {
                resetarVisualizacao();
                preencherCampos(tecnicoAtual);
                mostrarResultados(true);
                lblStatusBusca.setText("Aluno Tecnico encontrado!");
                lblStatusBusca.setStyle("-fx-text-fill: green;");
            } else {
                mostrarResultados(false);
                lblStatusBusca.setText("Nenhum aluno tecnico encontrado com ID " + idBusca);
                lblStatusBusca.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "Erro", "Valor informado é inválido.");
        }

    }

    // Editar:
    @FXML protected void handleEditar(ActionEvent event){
        try {
            if (!modoEdicao){
                habilitarEdicao(true);
                btnEditar.setText("Salvar");
                modoEdicao = true;
                lblStatusBusca.setText("Edição habilitada. Altere os campos e clique em Salvar.");
                lblStatusBusca.setStyle("-fx-text-fill: green;");
            } else {
                salvarAlteracoes();
            }
        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "ErroNumberInvalid", "Valores Idade/mensalidade inválidos");
            lblStatusBusca.setText("Erro: Verifique os campos numéricos (Idade/Mensalidade).");
            lblStatusBusca.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            showCustomAlert(Alert.AlertType.ERROR, "BadRequest", "Erro 500: " + e.getMessage());
            lblStatusBusca.setText("Erro ao atualizar: " + e.getMessage());
            lblStatusBusca.setStyle("-fx-text-fill: red;");
        }
    }

    // Excluir:
    @FXML
    protected void handleExcluir(ActionEvent event){
        if(tecnicoAtual != null){
            ExcluirService.excluirTecnico(tecnicoAtual, Application.listaTecnico);
            GerarArquivoService.GuardarTecnico(Application.listaTecnico);

            limparCampos();
            mostrarResultados(false);
            tecnicoAtual = null;

            showCustomAlert(Alert.AlertType.CONFIRMATION, "Exclusão bem sucedida", "Aluno técnico excluído!");
            lblStatusBusca.setText("Aluno técnico excluído!");
            lblStatusBusca.setStyle("-fx-text-fill: green;");
        }
    }

    // Metodos auxiliares:

    private void preencherCampos(Tecnico tecnico) {
        txtNome.setText(tecnico.getNome());
        txtIdade.setText(String.valueOf(tecnico.getIdade()));
        txtSexo.setText(tecnico.getSexo());
        txtCurso.setText(tecnico.getCurso());
        txtMensalidade.setText(String.valueOf(tecnico.getMensalidade()));
        txtRegistro.setText(tecnico.getRegistroProfissional());

        // Matricula
        chkMatricula.setSelected(tecnico.isMatricula());
        atualizarTextoCheckbox(); // Atualiza o texto visual (Ativa/Inativa)

    }
    private void habilitarEdicao(boolean habilitar) {
        txtNome.setEditable(habilitar);
        txtIdade.setEditable(habilitar);
        txtSexo.setEditable(habilitar);
        txtCurso.setEditable(habilitar);
        txtMensalidade.setEditable(habilitar);
        txtIdade.setEditable(habilitar);
        txtRegistro.setEditable(habilitar);

        chkMatricula.setDisable(!habilitar); // para aparecer ou desaparecer.

        TextField[] camposTexto = {txtNome, txtIdade, txtSexo, txtCurso, txtMensalidade, txtRegistro};

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
    private void salvarAlteracoes(){
        try {
            // Setando as informações do Tecnico
            tecnicoAtual.setNome(txtNome.getText());
            tecnicoAtual.setIdade(Integer.parseInt(txtIdade.getText()));
            tecnicoAtual.setSexo(txtSexo.getText());
            tecnicoAtual.setCurso(txtCurso.getText());
            tecnicoAtual.setMensalidade(Float.parseFloat(txtMensalidade.getText()));
            tecnicoAtual.setRegistroProfissional(txtRegistro.getText());

            // Matricula
            tecnicoAtual.setMatricula(chkMatricula.isSelected());

            // Usa o service para editar o valor dentro do arraylist.
            AtualizarService.editarTecnico(tecnicoAtual, Application.listaTecnico);

            // 3. Finaliza edição
            habilitarEdicao(false);
            btnEditar.setText("Editar");
            modoEdicao = false;

            preencherCampos(tecnicoAtual);

            lblStatusBusca.setText("Aluno Tecnico atualizado com sucesso!");
            lblStatusBusca.setStyle("-fx-text-fill: green;");

        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "NumberError", "Camps Idade/Mensalidade informados de forma errada");
            lblStatusBusca.setText("Erro: Não foi possível salvar os dados.");
            lblStatusBusca.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            lblStatusBusca.setText("Erro ao atualizar: " + e.getMessage());
            lblStatusBusca.setStyle("-fx-text-fill: red;");
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
        txtRegistro.clear();
        chkMatricula.setSelected(false); // para desmarcar a caixinha.
        txtBusca.clear();
    }
    private void resetarVisualizacao() {
        // 1. Reset dos TextFields
        TextField[] campos = {txtNome, txtIdade, txtSexo, txtCurso, txtMensalidade, txtRegistro};
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
    // Método extra para deixar o visual bonito (Texto muda conforme clica)
    private void atualizarTextoCheckbox() {
        if (chkMatricula.isSelected()) {
            chkMatricula.setText("Matrícula Ativa");
        } else {
            chkMatricula.setText("Matrícula Inativa");
        }
    }
}
