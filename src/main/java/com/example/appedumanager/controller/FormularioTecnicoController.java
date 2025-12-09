package com.example.appedumanager.controller;

import com.example.appedumanager.Application;
import com.example.appedumanager.model.Tecnico;
import com.example.appedumanager.service.CadastrarService;
import com.example.appedumanager.service.GerarArquivoService;
import com.example.appedumanager.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

public class FormularioTecnicoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private TextField txtCurso;
    @FXML private CheckBox checkMatricula;
    @FXML private TextField txtMensalidade;
    @FXML private TextField txtRegistroProfissional;

    @FXML private Button btnSalvar;

    public void handleSalvar(){
        try{
            if (txtNome.getText().isEmpty() ||
                    txtIdade.getText().isEmpty() ||
                    txtSexo.getText().isEmpty() ||
                    txtCurso.getText().isEmpty() ||
                    txtMensalidade.getText().isEmpty() ||
                    txtRegistroProfissional.getText().isEmpty()) {
                AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Validação",
                        "Campos obrigatórios não podem estar vazios.");
                return;
            }

            Tecnico novoTecnico = getNovoTecnico();

            CadastrarService.cadastrarTecnico(novoTecnico, Application.listaTecnico);
            GerarArquivoService.GuardarTecnico(Application.listaTecnico);

            AlertUtils.showCustomAlert(Alert.AlertType.INFORMATION, "Sucesso",
                    "Aluno Tecnico cadastrado(a) com sucesso! \n" + "Seu ID é: " + novoTecnico.getId());

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
    private Tecnico getNovoTecnico() {
        String nome = txtNome.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        String sexo = txtSexo.getText();
        String curso = txtCurso.getText();
        float mensalidade = Float.parseFloat(txtMensalidade.getText());
        boolean matriculaAtiva = checkMatricula.isSelected();
        String registroProfissional = txtRegistroProfissional.getText();

        Tecnico novoTecnico = new Tecnico();
        novoTecnico.setNome(nome);
        novoTecnico.setIdade(idade);
        novoTecnico.setSexo(sexo);
        novoTecnico.setCurso(curso);
        novoTecnico.setMensalidade(mensalidade);
        novoTecnico.setMatricula(matriculaAtiva);
        novoTecnico.setRegistroProfissional(registroProfissional);
        return novoTecnico;
    }

    private void limparCampos(){
        txtNome.clear();
        txtIdade.clear();
        txtSexo.clear();
        txtCurso.clear();
        txtMensalidade.clear();
        txtRegistroProfissional.clear();
        checkMatricula.setSelected(false);
    }

}
