package br.com.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Document;

public class JsoupDocumentUtils {

    public static boolean verificacaoClasseValida(Document documentoHtml) {
        String html = documentoHtml.body().select("#container-purchase").html();

        if (html.isEmpty() || html.isBlank())
            return false;

        return true;
    }

    public static void insercaoProdutoNoArquivo(Document documentoHtml, String urlProduto) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("testeNovoFormato",
                true));

        writer.newLine();

        // url produto
        writer.append(urlProduto);

        // titulo produto
        writer.append(documentoHtml.body().getElementsByTag("h1").text());

        // preco produto
        writer.append(documentoHtml.body().select(".finalPrice").text());

        writer.close();
    }
}
