package com.example.appedumanager.controller;

import com.example.appedumanager.model.Aluno;
import com.example.appedumanager.service.CadastrarService;
import com.example.appedumanager.Application;

import com.example.appedumanager.service.GerarArquivoService;
import com.example.appedumanager.util.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

public class FormularioAlunoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSexo;
    @FXML private TextField txtCurso;
    @FXML private CheckBox checkMatricula;
    @FXML private TextField txtMensalidade;

    @FXML private Button btnSalvar;

    @FXML
    private void handleSalvar(ActionEvent event) {
        try{
            if (txtNome.getText().isEmpty() || txtIdade.getText().isEmpty() || txtSexo.getText().isEmpty() || txtCurso.getText().isEmpty()) {
                AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Validação",
                        "Campos obrigatórios não podem estar vazios.");
                return;
            }

            // Coletar os dados da View e cria o objeto que será salvo na linha 41.
            Aluno novoAluno = getNovoAluno();

            CadastrarService.cadastrarAluno(novoAluno, Application.listaAluno); // Intancia encurtada
            GerarArquivoService.GuardarAluno(Application.listaAluno);

            // Mensagem de sucesso
            AlertUtils.showCustomAlert(Alert.AlertType.INFORMATION, "Sucesso",
                    "Aluno cadastrado(a) com sucesso! " + "Seu ID é: " + novoAluno.getId());

            // Limpa os dados dos campos dentro da view.
            limparCampos();

        } catch (NumberFormatException e) {
            AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro de Formato",
                    "O campo 'Idade' deve conter apenas números.");
        } catch (Exception e) {
            AlertUtils.showCustomAlert(Alert.AlertType.ERROR, "Erro Inesperado",
                    "Ocorreu um erro: " + e.getMessage());
        }
    }

    // Garante que o método getNovoAluno() nunca retorne vazio.
    @NotNull
    private Aluno getNovoAluno() {
        String nome = txtNome.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        String sexo = txtSexo.getText();
        String curso = txtCurso.getText();
        float mensalidade = Float.parseFloat(txtMensalidade.getText());;
        boolean matriculaAtiva = checkMatricula.isSelected();

        // Criar Objeto
        Aluno novoAluno = new Aluno();
        novoAluno.setNome(nome);
        novoAluno.setIdade(idade);
        novoAluno.setSexo(sexo);
        novoAluno.setCurso(curso);
        novoAluno.setMensalidade(mensalidade);
        novoAluno.setMatricula(matriculaAtiva);
        return novoAluno;
    }

    private void limparCampos(){
        txtNome.clear();
        txtIdade.clear();
        txtSexo.clear();
        txtCurso.clear();
        txtMensalidade.clear();
        checkMatricula.setSelected(false);
    }

}
