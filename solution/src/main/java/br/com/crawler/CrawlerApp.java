package br.com.crawler;

import java.net.ConnectException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.com.Exceptions.NullPageException;
import br.com.Utils.JsoupDocumentUtils;
import br.com.Utils.VerificacoesUtils;

public class CrawlerApp {

    public static void main(String[] args) throws Exception {
        String url = "https://www.kabum.com.br/produto/514536/placa-de-video-rx-6600-v2-asus-dual-amd-radeon-8gb-gddr6-90yv0gp2-m0na00";
        crawlerFunction(1, url, new ArrayList<String>());
    }

    private static void crawlerFunction(int profundidadePesquisa, String url, ArrayList<String> arrayUrlsVisitadas)
            throws Exception {

        try {
            if (profundidadePesquisa <= 5) {
                Document documentoHtml = requisicao(url, arrayUrlsVisitadas);

                if (documentoHtml == null)
                    throw new NullPageException();

                for (Element link : documentoHtml.select("a[href]")) {

                    String proximo_link = link.absUrl("href");

                    if (VerificacoesUtils.validacaoProximoLink(arrayUrlsVisitadas, proximo_link)) {
                        crawlerFunction(profundidadePesquisa++, proximo_link, arrayUrlsVisitadas);
                    }
                }
            }
        } catch (NullPageException ex) {
            System.out.println(ex);
        }
    }

    private static Document requisicao(String url, ArrayList<String> arrayUrlsVisitadas) {
        try {
            Connection urlConexao = Jsoup.connect(url);
            Document documentoHtml = urlConexao.get();

            if (urlConexao.response().statusCode() != 200)
                throw new ConnectException("Falha na conexão"); // tirar exception generica

            if (!VerificacoesUtils.verificacaoClasseValida(documentoHtml)) {
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
