package com.github.supermoonie;

import cn.hutool.core.net.URLEncoder;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 * @author supermoonie
 */
public class App {

    private final static String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36 Edg/84.0.522.40";

    private final static String DEFAULT_URL = "http://www.ricedata.cn/ontology/default.aspx";

    private final static String SEARCH_URL = "http://www.ricedata.cn/ontology/default.aspx";

    private final static BasicCookieStore BASIC_COOKIE_STORE = new BasicCookieStore();

    public static void main(String[] args) {
        final String kw = "长度";
        HttpClientBuilder httpClientBuilder = HttpClientUtils.createTrustAllHttpClientBuilder();
        httpClientBuilder.setDefaultCookieStore(BASIC_COOKIE_STORE);
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            DefaultPageResult defaultPageResult = getAndParseDefault(httpClient);
            defaultPageResult.setOntName("茎秆");
            List<SearchResult> searchResultList = search(httpClient, defaultPageResult);
            searchResultList.stream().filter(searchResult -> searchResult.getName().contains(kw)).forEach(searchResult -> {

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DefaultPageResult getAndParseDefault(CloseableHttpClient httpClient) throws IOException {
        HttpGet defaultGet = new HttpGet(DEFAULT_URL);
        defaultGet.setHeader("User-Agent", UA);
        try (CloseableHttpResponse defaultRes = httpClient.execute(defaultGet)) {
            String defaultHtml = EntityUtils.toString(defaultRes.getEntity());
            Document defaultDoc = Jsoup.parse(defaultHtml);
            String viewState = defaultDoc.getElementById("__VIEWSTATE").val();
            String viewStateGenerator = defaultDoc.getElementById("__VIEWSTATEGENERATOR").val();
            DefaultPageResult result = new DefaultPageResult();
            result.setViewState(viewState);
            result.setViewStateGenerator(viewStateGenerator);
            return result;
        }
    }

    private static List<SearchResult> search(CloseableHttpClient httpClient, DefaultPageResult defaultPageResult) throws IOException {
        HttpPost httpPost = new HttpPost(SEARCH_URL);
        httpPost.setHeader("User-Agent", UA);
        httpPost.setHeader("Referer", "http://www.ricedata.cn/ontology/default.aspx");
        List<NameValuePair> paramList = new ArrayList<>();
        paramList.add(new BasicNameValuePair("__VIEWSTATE", defaultPageResult.getViewState()));
        paramList.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", defaultPageResult.getViewStateGenerator()));
        paramList.add(new BasicNameValuePair("txtoLists", defaultPageResult.getTxtoLists()));
        paramList.add(new BasicNameValuePair("ontname", URLEncoder.DEFAULT.encode(defaultPageResult.getOntName(), StandardCharsets.UTF_8)));
        paramList.add(new BasicNameValuePair("btnlist", URLEncoder.DEFAULT.encode(defaultPageResult.getBtnList(), StandardCharsets.UTF_8)));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8);
        httpPost.setEntity(entity);
        try (CloseableHttpResponse searchRes = httpClient.execute(httpPost)) {
            Document searchDoc = Jsoup.parse(EntityUtils.toString(searchRes.getEntity()));
            Element table = searchDoc.getElementsByTag("table").get(1);
            Elements rows = table.getElementsByTag("tr");
            List<SearchResult> resultList = new ArrayList<>(rows.size());
            for (int i = 1; i < rows.size(); i ++) {
                Element row = rows.get(i);
                Elements cols = row.getElementsByTag("td");
                SearchResult result = new SearchResult();
                result.setLoginNo(cols.get(1).text());
                result.setLoginNoUrl("http://www.ricedata.cn/ontology/" + cols.get(1).attr("href"));
                result.setName(cols.get(2).text());
                resultList.add(result);
            }
            return resultList;
        }
    }

    private static GeneNameListPage geneList(CloseableHttpClient httpClient, int pageIndex, String dbName, String loginNo) throws IOException {
        HttpGet httpGet = new HttpGet(String.format("http://www.ricedata.cn/ontology/ontolists.aspx?p=%d&db=%s&ta=%s", pageIndex, dbName, loginNo));
        httpGet.setHeader("User-Agent", UA);
        httpGet.setHeader("Referer", String.format("http://www.ricedata.cn/ontology/ontolists.aspx?db=%s&ta=%s", dbName, loginNo));
        try (CloseableHttpResponse pageRes = httpClient.execute(httpGet)) {
            return null;
        }
    }
}
