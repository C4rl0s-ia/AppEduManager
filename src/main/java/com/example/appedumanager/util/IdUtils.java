package com.example.appedumanager.util;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class IdUtils {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int TAMANHO_SUFIXO = 7;
    private static final Random RANDOM = new Random();

    /**
     * Gera um ID único no formato "PREFIXO_XXXXXXX".
     *
     * @param lista A lista onde será verificado se o ID já existe.
     * @param getIdMapper A função para pegar o ID do objeto (ex: Aluno::getId).
     * @param prefixo O prefixo desejado (ex: "prf_", "aln_").
     * @return Um ID String único.
     */
    public static <T> String gerarIdUnico(List<T> lista, Function<T, String> getIdMapper, String prefixo) {
        String novoId;
        boolean existe;

        do {
            // 1. Gera o candidato a ID (ex: "prf_A1b2C3d")
            novoId = prefixo + gerarSufixoAleatorio();

            // 2. Verifica se já existe na lista
            String idParaVerificar = novoId;
            existe = lista.stream()
                    .map(getIdMapper) // Transforma a lista de Objetos em lista de IDs (Strings)
                    .anyMatch(id -> id.equals(idParaVerificar));

        } while (existe); // Se existir, repete o laço até criar um inédito

        return novoId;
    }

    private static String gerarSufixoAleatorio() {
        StringBuilder sb = new StringBuilder(TAMANHO_SUFIXO);
        for (int i = 0; i < TAMANHO_SUFIXO; i++) {
            // O nextInt pega automaticamente o novo tamanho da String CARACTERES
            int index = RANDOM.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(index));
        }
        return sb.toString();
    }
}