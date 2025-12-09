package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Professor;
import com.example.appedumanager.service.AtualizarService;
import com.example.appedumanager.service.BuscarService;
import com.example.appedumanager.service.ExcluirService;
import com.example.appedumanager.service.GerarArquivoService;
import com.example.appedumanager.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;

import static com.example.appedumanager.util.AlertUtils.showCustomAlert;

public class VisualizacaoProfessorController {

    // Busca
    @FXML private TextField txtBusca;
    @FXML private Label lblStatusBusca;

    @FXML private GridPane gridResultados;

    // Dados do professor
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private TextField txtEspecialidade;
    @FXML private TextField txtSalario;

    // Ações Excluir e Editar
    @FXML private HBox boxBotoes; // para tornar o campo visivel.
    @FXML private Button btnExcluir;
    @FXML private Button btnEditar;

    // Controle
    private Professor professorAtual;
    private boolean modoEdicao = false;

    @FXML
    public void initialize() {
        txtBusca.setOnAction(event -> handleBuscar(event));
    }

    @FXML protected void handleBuscar(ActionEvent event){
        String id = txtBusca.getText();

        if (id.isEmpty()){
            AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Sem valor de pesquisa", "Preencha o campo de busca.");
            lblStatusBusca.setText("Professor não localizado");
            lblStatusBusca.setStyle("-fx-text-fill: red;");
            return;
        }

        try{
            // Localiza o professor e atribui o objeto a sua váriavel.
            professorAtual = BuscarService.buscarProfessorPorId(txtBusca.getText(), Application.listaProfessor);

            if (professorAtual != null){
                resetarVisualizacao();

                preencherCampos(professorAtual);
                mostrarResultados(true);
                lblStatusBusca.setText("Professor localizado!");
                lblStatusBusca.setStyle("-fx-text-fill: green;");
            } else {
                showCustomAlert(Alert.AlertType.ERROR, "TicherNotFound", "Professor não localizado.");
            }


        } catch (NumberFormatException e) {
            showCustomAlert(Alert.AlertType.ERROR, "Erro", "Valor informado é inválido.");
        }

    }

    @FXML protected void handleEditar(ActionEvent event){
        if (professorAtual == null) return;

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

    @FXML protected void handleExcluir(ActionEvent event){
        if (professorAtual != null) {
            ExcluirService.excluirProfessor(professorAtual, Application.listaProfessor);
            GerarArquivoService.GuardarProfessores(Application.listaProfessor);
            limparCampos();
            mostrarResultados(false);
            showCustomAlert(Alert.AlertType.INFORMATION, "Exclusão concluída", "Professor excluído com sucesso!");
            lblStatusBusca.setText("Professor excluído!");
            lblStatusBusca.setStyle("-fx-text-fill: black;");
        }
    }


    // Metodos auxiliares:

    private void preencherCampos(Professor professor) {
        txtNome.setText(professor.getNome());
        txtIdade.setText(String.valueOf(professor.getIdade()));
        txtSexo.setText(professor.getSexo());
        txtEspecialidade.setText(professor.getEspecialidade());
        txtSalario.setText(String.valueOf(professor.getSalario()));
    }
    private void habilitarEdicao(boolean habilitar) {
        txtNome.setEditable(habilitar);
        txtIdade.setEditable(habilitar);
        txtSexo.setEditable(habilitar);
        txtEspecialidade.setEditable(habilitar);
        txtSalario.setEditable(habilitar);

        // Nomes das classes CSS que controlam a aparência
        final String CLASSE_LEITURA = "campo-somente-leitura";
        final String CLASSE_DADO = "result-data";

        // Cria um array dos TextFields para facilitar a iteração
        TextField[] campos = {txtNome, txtIdade, txtSexo, txtEspecialidade, txtSalario};

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
            professorAtual.setNome(txtNome.getText());
            professorAtual.setIdade(Integer.parseInt(txtIdade.getText()));
            professorAtual.setSexo(txtSexo.getText());
            professorAtual.setEspecialidade(txtEspecialidade.getText());
            professorAtual.setSalario(Float.parseFloat(txtSalario.getText()));

            // Usa o service para editar o valor dentro do arraylist.
            AtualizarService.editarProfessor(professorAtual, Application.listaProfessor);

            // 3. Finaliza edição
            habilitarEdicao(false);
            btnEditar.setText("Editar");
            modoEdicao = false;

            // Atualiza a visualização para garantir formatação (ex: matrícula volta a ser "Ativa" em vez de "sim")
            preencherCampos(professorAtual);

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
        txtEspecialidade.clear();
        txtSalario.clear();
        txtBusca.clear();
    }
    private void resetarVisualizacao() {
        // Lista de todos os campos
        TextField[] campos = {txtNome, txtIdade, txtSexo, txtEspecialidade, txtSalario};

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
