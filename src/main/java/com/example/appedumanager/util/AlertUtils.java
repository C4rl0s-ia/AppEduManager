package com.example.appedumanager.util;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class AlertUtils {

    /**
     * Exibe um alerta estilizado e personalizado.
     * @param tipo O tipo do alerta (INFORMATION, ERROR, WARNING)
     * @param titulo O título da janela (Header)
     * @param mensagem A mensagem de corpo
     */
    public static void showCustomAlert(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle("AppEduManager");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);

        // 1. Aplicar CSS
        DialogPane dialogPane = alert.getDialogPane();
        try {
            // Usamos caminhos absolutos para garantir que ache independente de onde a classe Utils esteja
            String cssPath = Objects.requireNonNull(AlertUtils.class.getResource("/com/example/appedumanager/views/style.css")).toExternalForm();
            dialogPane.getStylesheets().add(cssPath);
            dialogPane.getStyleClass().add("my-dialog");
        } catch (Exception e) {
            System.err.println("Erro ao carregar CSS do Alerta: " + e.getMessage());
        }

        // 2. Ícone Personalizado para Sucesso
        if (tipo == Alert.AlertType.INFORMATION) {
            try {
                Image image = new Image(Objects.requireNonNull(AlertUtils.class.getResourceAsStream("/com/example/appedumanager/views/image/success-icon.png")));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(48);
                imageView.setFitHeight(48);
                dialogPane.setGraphic(imageView);
            } catch (Exception e) {
                System.err.println("Erro ao carregar ícone de sucesso.");
            }
        }

        if (tipo == Alert.AlertType.ERROR) {
            try {
                Image image = new Image(Objects.requireNonNull(AlertUtils.class.getResourceAsStream("/com/example/appedumanager/views/image/error-icon.png")));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(48);
                imageView.setFitHeight(48);
                dialogPane.setGraphic(imageView);
            } catch (Exception e) {
                System.err.println("Erro ao carregar ícone de falha.");
            }
        }

        alert.showAndWait();
    }
}