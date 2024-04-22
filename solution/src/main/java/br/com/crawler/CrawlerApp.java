package br.com.crawler;

import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.com.Utils.JsoupDocumentUtils;
public class CrawlerApp {

    public static void main(String[] args) throws Exception {
        String url = "https://www.kabum.com.br/perifericos/headsets/sem-fio";
        crawlerFunction(1, url, new ArrayList<String>());
    }

    private static void crawlerFunction(int profundidadePesquisa, String url, ArrayList<String> arrayUrlsVisitadas)
            throws Exception {

        try {
            if (profundidadePesquisa <= 5) {
                Document paginaHtml = requisicao(url, arrayUrlsVisitadas);

                if (paginaHtml == null)
                    throw new Exception("Pagina Invalida");

                for (Element link : paginaHtml.select("a[href]")) {

                    String proximo_link = link.absUrl("href");

                    if (arrayUrlsVisitadas.contains(proximo_link) == false
                            || (arrayUrlsVisitadas.contains(proximo_link) == false && proximo_link.contains("#")))
                        crawlerFunction(profundidadePesquisa++, proximo_link, arrayUrlsVisitadas);
                }
            }
        } catch (Exception ex) { // tirar exception generica
            System.out.println(ex);
        }
    }

    private static Document requisicao(String url, ArrayList<String> arrayUrlsVisitadas) {
        try {
            Connection urlConexao = Jsoup.connect(url);
            Document documentoHtml = urlConexao.get(); // rever nome

            if (urlConexao.response().statusCode() != 200)
                throw new Exception("Falha na conexão"); // tirar exception generica

            if (!JsoupDocumentUtils.verificacaoClasseValida(documentoHtml)) {
                System.out.println("------------------------"); // teste visual
                System.out.println("Url: " + url);
                System.out.println("Sem conteudo para venda.");
                System.out.println("------------------------");
                arrayUrlsVisitadas.add(url);
                return documentoHtml;
            }

            // insercao produto no arquivo
            JsoupDocumentUtils.insercaoProdutoNoArquivo(documentoHtml, url);

            arrayUrlsVisitadas.add(url);

            return documentoHtml;
        } catch (Exception ex) {
            return null;
        }
    }
}
