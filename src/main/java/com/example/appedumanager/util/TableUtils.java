package com.example.appedumanager.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.text.NumberFormat;
import java.util.Locale;

public class TableUtils {

    private static final NumberFormat MOEDA_FORMAT =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static <S, T extends Number> Callback<TableColumn<S, T>, TableCell<S, T>> formatarMoeda() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // O format trata Float, Double e BigDecimal automaticamente
                    setText(MOEDA_FORMAT.format(item));
                }
            }
        };
    }

    /**
     * Formata colunas booleanas com texto personalizado e cores (Verde/Vermelho).
     * @param textoTrue O que escrever se for verdadeiro (ex: "Ativa", "Pago", "Sim")
     * @param textoFalse O que escrever se for falso (ex: "Inativa", "Pendente", "Não")
     */
    public static <S> Callback<TableColumn<S, Boolean>, TableCell<S, Boolean>> formatarBoolean(String textoTrue, String textoFalse) {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle(""); // Limpa o estilo para não "sujar" células vazias
                } else {
                    // Define o texto baseado no parâmetro
                    setText(item ? textoTrue : textoFalse);

                    // Define a cor (Verde para True, Vermelho para False)
                    if (item) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;"); // Um vermelho mais elegante
                    }
                }
            }
        };
    }

    /**
     * Formata números como porcentagem (ex: 50.0 vira "50,0%").
     * Ideal para valores onde o número inteiro já representa a porcentagem.
     */
    public static <S, T extends Number> Callback<TableColumn<S, T>, TableCell<S, T>> formatarPorcentagem() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.1f%%", item.doubleValue()));
                }
            }
        };
    }

}