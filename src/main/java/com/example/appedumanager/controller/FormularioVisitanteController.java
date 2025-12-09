package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Visitante;
import com.example.appedumanager.service.CadastrarService;
import com.example.appedumanager.service.GerarArquivoService;
import com.example.appedumanager.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

public class FormularioVisitanteController {

    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private Button btnSalvar;

    public void handleSalvar(){
        try{
            if (txtNome.getText().isEmpty() || txtIdade.getText().isEmpty() || txtSexo.getText().isEmpty()) {
                AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Validação",
                        "Campos obrigatórios não podem estar vazios.");
                return;
            }

            Visitante novoVisitante = getNovoVisitante();
            CadastrarService.cadastrarVisitante(novoVisitante, Application.listaVisitante);
            GerarArquivoService.GuardarVisitante(Application.listaVisitante);

            AlertUtils.showCustomAlert(Alert.AlertType.INFORMATION, "Sucesso",
                    "Visitante cadastrado(a) com sucesso! Seu ID é: " + novoVisitante.getId());

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
    private Visitante getNovoVisitante(){
        String nome = txtNome.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        String sexo = txtSexo.getText();

        Visitante novoVisitante = new Visitante();
        novoVisitante.setNome(nome);
        novoVisitante.setIdade(idade);
        novoVisitante.setSexo(sexo);
        return novoVisitante;
    }
    private void limparCampos(){
        txtNome.clear();
        txtIdade.clear();
        txtSexo.clear();
    }

}
