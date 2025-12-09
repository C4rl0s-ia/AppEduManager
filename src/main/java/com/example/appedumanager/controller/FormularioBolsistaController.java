package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Bolsista;
import com.example.appedumanager.service.CadastrarService;
import com.example.appedumanager.service.GerarArquivoService;
import com.example.appedumanager.util.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;


public class FormularioBolsistaController {

    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private TextField txtCurso;
    @FXML private CheckBox checkMatricula;
    @FXML private TextField txtMensalidade;
    @FXML private TextField txtBolsa;

    @FXML private Button btnSalvar;

    @FXML
    private void handleSalvar(ActionEvent event) {
        try{
            if (txtNome.getText().isEmpty() || txtIdade.getText().isEmpty() || txtSexo.getText().isEmpty() || txtCurso.getText().isEmpty() ||
            txtBolsa.getText().isEmpty() || txtMensalidade.getText().isEmpty()) {
                AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Validação",
                        "Campos obrigatórios não podem estar vazios.");
                return;
            }

            Bolsista novoBolsista = getNovoBolsista();

            CadastrarService.cadastrarBolsista(novoBolsista, Application.listaBolsista);
            GerarArquivoService.GuardarBolsista(Application.listaBolsista);

            AlertUtils.showCustomAlert(Alert.AlertType.INFORMATION, "Sucesso",
                    "Aluno Bolsista cadastrado(a) com sucesso! \n" + "Seu ID é: " + novoBolsista.getId());

            limparCampos();

        } catch (NumberFormatException e) {
            AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Formato",
                    "O campo 'Idade' deve conter apenas números.");
        } catch (Exception e) {
            AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro Inesperado",
                    "Ocorreu um erro: " + e.getMessage());
        }
    }

    @NotNull
    private Bolsista getNovoBolsista() {
        String nome = txtNome.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        String sexo = txtSexo.getText();
        String curso = txtCurso.getText();
        float mensalidade = Float.parseFloat(txtMensalidade.getText());;
        boolean matriculaAtiva = checkMatricula.isSelected();
        float bolsa = Float.parseFloat(txtBolsa.getText());;

        // Criar Objeto
        Bolsista novoBolsista = new Bolsista();
        novoBolsista.setNome(nome);
        novoBolsista.setIdade(idade);
        novoBolsista.setSexo(sexo);
        novoBolsista.setCurso(curso);
        novoBolsista.setMensalidade(mensalidade);
        novoBolsista.setMatricula(matriculaAtiva);
        novoBolsista.setBolsa(bolsa);
        return novoBolsista;
    }

    private void limparCampos(){
        txtNome.clear();
        txtIdade.clear();
        txtSexo.clear();
        txtCurso.clear();
        txtMensalidade.clear();
        txtBolsa.clear();
        checkMatricula.setSelected(false);
    }

}
