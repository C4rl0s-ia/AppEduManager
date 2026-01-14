package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Professor;
import com.example.appedumanager.service.CadastrarService;
import com.example.appedumanager.service.GerarArquivoService;
import com.example.appedumanager.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.jetbrains.annotations.NotNull;

public class FormularioProfessorController {
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;

    @FXML private TextField txtEspecialidade;
    @FXML private TextField txtSalario;

    @FXML private Button btnSalvar;

    public void handleSalvar(){
        try {
            if (txtNome.getText().isEmpty() ||
                    txtIdade.getText().isEmpty() ||
                    txtSexo.getText().isEmpty() ||
                    txtEspecialidade.getText().isEmpty() ||
                    txtSalario.getText().isEmpty()) { AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Validação",
                        "Campos obrigatórios não podem estar vazios.");
                return;
            }

            Professor novoProfessor = getNovoProfessor();

            CadastrarService.cadastrarProfessor(novoProfessor, Application.listaProfessor);
            GerarArquivoService.GuardarProfessores(Application.listaProfessor);

            AlertUtils.showCustomAlert(Alert.AlertType.INFORMATION, "Sucesso",
                    "Professor(a) cadastrado(a) com sucesso! \n" + "Seu ID é: " + novoProfessor.getId());

            limparCampos();

        } catch (NumberFormatException e) {
            AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Formato",
                    "Ocorreu um erro: Verifique os dados inseridos e tente novamente.");
        } catch (Exception e) {
            AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro Inesperado",
                    "Ocorreu um erro: " + e.getMessage());
        }
    }

    @NotNull
    private Professor getNovoProfessor(){
        String nome = txtNome.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        String sexo = txtSexo.getText();
        String especialidade = txtEspecialidade.getText();
        float salario = Float.parseFloat(txtSalario.getText());

        float salarioTratado = salario / 100;

        Professor novoProfessor = new Professor();
        novoProfessor.setNome(nome);
        novoProfessor.setIdade(idade);
        novoProfessor.setSexo(sexo);
        novoProfessor.setEspecialidade(especialidade);
        novoProfessor.setSalario(salarioTratado);
        return novoProfessor;
    }

    private void limparCampos(){
        txtNome.clear();
        txtIdade.clear();
        txtSexo.clear();
        txtEspecialidade.clear();
        txtSalario.clear();
    }

}
