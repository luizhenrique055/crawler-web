package br.com.crawler;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CrawlerApp {

    public static void main(String[] args) throws Exception {

        String url = "https://www.wikipedia.org/";
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

                    if (arrayUrlsVisitadas.contains(proximo_link) == false)
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
            Document paginaHtml = urlConexao.get(); // rever nome

            if (urlConexao.response().statusCode() != 200)
                return null;

            System.out.println("URl: " + url);
            System.out.println("Titulo pagina Html: " + paginaHtml.title());
            arrayUrlsVisitadas.add(url);

            return paginaHtml;
        } catch (IOException ex) {
            return null;
        }
    }

}
