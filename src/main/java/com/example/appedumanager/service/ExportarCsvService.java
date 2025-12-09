package com.example.appedumanager.service;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExportarCsvService {

    public static <T> void exportarTabelaParaCsv(TableView<T> tabela, Window janelaPai) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Tabela como CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv"));

        // Sugere um nome padrão
        fileChooser.setInitialFileName("exportacao_tabela.csv");

        File arquivo = fileChooser.showSaveDialog(janelaPai);

        if (arquivo != null) {
            salvarArquivo(tabela, arquivo);
        }
    }

    private static <T> void salvarArquivo(TableView<T> tabela, File arquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {

            // --- 1. Escreve o BOM (Byte Order Mark) ---
            // Isso força o Excel a reconhecer acentos (ç, ã, é) corretamente em UTF-8
            writer.write('\ufeff');

            // --- 2. Escreve o Cabeçalho (Nomes das Colunas) ---
            for (int i = 0; i < tabela.getColumns().size(); i++) {
                TableColumn<T, ?> col = tabela.getColumns().get(i);
                // Ignora a coluna de "Ações" (botões não vão para o Excel)
                if (!"Ações".equals(col.getText())) {
                    writer.write(escaparCsv(col.getText()));
                    if (i < tabela.getColumns().size() - 2) { // -2 pois ignoramos a última coluna
                        writer.write(";"); // Usando ponto e vírgula para Excel Brasileiro (ou use "," para padrão US)
                    }
                }
            }
            writer.newLine();

            // --- 3. Escreve os Dados (Linhas) ---
            for (T item : tabela.getItems()) {
                for (int i = 0; i < tabela.getColumns().size(); i++) {
                    TableColumn<T, ?> col = tabela.getColumns().get(i);

                    if (!"Ações".equals(col.getText())) {
                        // Pega o dado da célula dinamicamente
                        Object valor = col.getCellData(item);
                        String texto = (valor != null) ? String.valueOf(valor) : "";

                        writer.write(escaparCsv(texto));

                        if (i < tabela.getColumns().size() - 2) {
                            writer.write(";");
                        }
                    }
                }
                writer.newLine();
            }

            System.out.println("Exportação concluída para: " + arquivo.getAbsolutePath());

        } catch (IOException ex) {
            ex.printStackTrace();
            // Aqui você pode chamar um Alert de erro se quiser
        }
    }

    // Trata casos onde o texto já contém ponto e vírgula ou aspas
    private static String escaparCsv(String texto) {
        if (texto.contains(";") || texto.contains("\"") || texto.contains("\n")) {
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }
        return texto;
    }
}