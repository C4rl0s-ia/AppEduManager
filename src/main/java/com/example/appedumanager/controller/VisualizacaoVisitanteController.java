package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Visitante;
import com.example.appedumanager.service.AtualizarService;
import com.example.appedumanager.service.BuscarService;
import com.example.appedumanager.service.ExcluirService;
import com.example.appedumanager.service.GerarArquivoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static com.example.appedumanager.util.AlertUtils.showCustomAlert;

public class VisualizacaoVisitanteController {
    // Busca
    @FXML private TextField txtBusca;
    @FXML private Label lblStatusBusca;

    @FXML private GridPane gridResultados;

    // Dados do visitante
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;

    // Ações Excluir e Editar
    @FXML private HBox boxBotoes; // para tornar o campo visivel.
    @FXML private Button btnExcluir;
    @FXML private Button btnEditar;

    // Variaveis de controle:
    private Visitante visitanteAtual;
    private boolean modoEdicao = false;

    @FXML
    public void initialize() {
        txtBusca.setOnAction(event -> handleBuscar(event));
    }

    @FXML protected void handleBuscar(ActionEvent event){
        String idBusca = txtBusca.getText();

        if (idBusca.isEmpty()){
            showCustomAlert(Alert.AlertType.ERROR, "Sem valor de pesquisa", "Informe um valor para pesquisa");
            lblStatusBusca.setText("Visitante não encontrado! Tente novamente.");
            lblStatusBusca.setStyle("-fx-text-fill: red;");
            return;
        }

        try {

            visitanteAtual = BuscarService.buscarVisitantePorId(idBusca, Application.listaVisitante);

            if (visitanteAtual != null){
                resetarVisualizacao();
                preencherCampos(visitanteAtual);
                mostrarResultados(true);
                lblStatusBusca.setText("Visitante encontrado!");
                lblStatusBusca.setStyle("-fx-text-fill: green;");
            } else {
                mostrarResultados(false);
                showCustomAlert(Alert.AlertType.ERROR, "Falha", "Visitante não encontrado! Tente novamente.");
                lblStatusBusca.setText("Visitante não encontrado! Tente novamente.");
                lblStatusBusca.setStyle("-fx-text-fill: green;");
            }

        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "Erro", "Valor informado é inválido.");
        }

    }

    @FXML protected  void handleExcluir(ActionEvent event){
        if (visitanteAtual != null) {
            ExcluirService.excluirVisitante(visitanteAtual, Application.listaVisitante);
            GerarArquivoService.GuardarVisitante(Application.listaVisitante);
            limparCampos();
            mostrarResultados(false);
            showCustomAlert(Alert.AlertType.INFORMATION, "Exclusão concluída", "Visitante excluído com sucesso!");
            lblStatusBusca.setText("Visitante excluído!");
            lblStatusBusca.setStyle("-fx-text-fill: black;");
        }
    }

    @FXML protected void handleEditar(ActionEvent event){
        if (visitanteAtual == null) return;

        if (!modoEdicao) {
            habilitarEdicao(true);
            btnEditar.setText("Salvar");
            modoEdicao = true;
            lblStatusBusca.setText("Edição habilitada. Altere os campos e clique em Salvar.");
            lblStatusBusca.setStyle("-fx-text-fill: green;");
        } else {
            salvarAlteracoes();
        }
    }

    // métodos auxiliares:

    private void preencherCampos(Visitante visitante) {
        txtNome.setText(visitante.getNome());
        txtIdade.setText(String.valueOf(visitante.getIdade()));
        txtSexo.setText(visitante.getSexo());
    }
    private void habilitarEdicao(boolean habilitar) {
        txtNome.setEditable(habilitar);
        txtIdade.setEditable(habilitar);
        txtSexo.setEditable(habilitar);

        // Nomes das classes CSS que controlam a aparência
        final String CLASSE_LEITURA = "campo-somente-leitura";
        final String CLASSE_DADO = "result-data";

        // Cria um array dos TextFields para facilitar a iteração
        TextField[] campos = {txtNome, txtIdade, txtSexo};

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
    private void salvarAlteracoes(){
        try {
            // Setando as informações do professor
            visitanteAtual.setNome(txtNome.getText());
            visitanteAtual.setIdade(Integer.parseInt(txtIdade.getText()));
            visitanteAtual.setSexo(txtSexo.getText());

            // Usa o service para editar o valor dentro do arraylist.
            AtualizarService.editarVisitante(visitanteAtual, Application.listaVisitante);

            // 3. Finaliza edição
            habilitarEdicao(false);
            btnEditar.setText("Editar");
            modoEdicao = false;

            // Atualiza a visualização para garantir formatação (ex: matrícula volta a ser "Ativa" em vez de "sim")
            preencherCampos(visitanteAtual);

            lblStatusBusca.setText("Professor atualizado com sucesso!");
            lblStatusBusca.setStyle("-fx-text-fill: green;");

        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "NumberError", "Camps Idade/Salario informados de forma errada");
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
        txtBusca.clear();
    }
    private void resetarVisualizacao() {
        // Lista de todos os campos
        TextField[] campos = {txtNome, txtIdade, txtSexo};

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
