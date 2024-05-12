package br.com.Utils;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

public class VerificacoesUtils {

    /**
     * Verifica se a clase é valida. Caso a classe seja valida o retorno sera true.
     * 
     * @param documentoHtml
     * @return boolean
     */
    public static boolean verificacaoClasseValida(Document documentoHtml) {
        boolean resposta = true;

        String html = documentoHtml.body().select("#container-purchase").html();

        if (html.isEmpty() || html.isBlank())
            resposta = false;

        return resposta;
    }

    /**
     * Verifica se a url é valida. Caso seja valida a Url vai retornar true.
     * 
     * @param arrayUrlsVisitadas
     * @param proximo_link
     * @return boolean
     */
    public static boolean validacaoProximoLink(ArrayList<String> arrayUrlsVisitadas, String proximo_link) {
        boolean resposta = false;

        if (arrayUrlsVisitadas.contains(proximo_link) == false
                || (arrayUrlsVisitadas.contains(proximo_link) == false && proximo_link.contains("#")))
            resposta = true;

        return resposta;
    }
}
