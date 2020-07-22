package com.github.supermoonie;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final List<String> KW_LIST = new ArrayList<>();

    static {
        KW_LIST.add("叶宽");
        KW_LIST.add("叶长");
        KW_LIST.add("卷叶");
        KW_LIST.add("叶倾角");
        KW_LIST.add("叶夹角");
        KW_LIST.add("节间长度");
        KW_LIST.add("茎秆长度");
        KW_LIST.add("茎直径");
        KW_LIST.add("茎粗");
        KW_LIST.add("茎厚");
        KW_LIST.add("茎秆机械强度");
        KW_LIST.add("穗伸出度");
        KW_LIST.add("叶绿素含量");
    }

    public static void main(String[] args) {

        HttpClientBuilder httpClientBuilder = HttpClientUtils.createTrustAllHttpClientBuilder();
        httpClientBuilder.setDefaultCookieStore(BASIC_COOKIE_STORE);
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            DefaultPageResult defaultPageResult = getAndParseDefault(httpClient);
            List<GeneInfo> geneInfos = new ArrayList<>();
            for (String kw : KW_LIST) {
                defaultPageResult.setOntName(kw);
                // 根据特征进行检索
                List<SearchResult> searchResultList = search(httpClient, defaultPageResult);
                if (CollectionUtil.isEmpty(searchResultList)) {
                    GeneInfo geneInfo = new GeneInfo();
                    geneInfo.setFeature(kw);
                    geneInfos.add(geneInfo);
                    continue;
                }
                for (SearchResult searchResult : searchResultList) {
                    GeneNameListPage geneNameListPage = geneList(httpClient, 1, "gene", searchResult.getLoginNo());
                    for (GeneName geneName : geneNameListPage.getNameList()) {
                        GeneInfo geneInfo = new GeneInfo();
                        geneInfo.setFeature(kw);
                        geneInfo.setGeneEnName(geneName.getEnName());
                        geneInfo.setGeneZhName(geneName.getZhName());
                        geneInfo.setLoginNo(searchResult.getLoginNo());
                        Pair<Integer, String> posPair = positionInfo(httpClient, geneName.getUrl(), "http://www.ricedata.cn/ontology/ontolists.aspx?db=gene&ta=" + searchResult.getLoginNo());
                        geneInfo.setChromosome(posPair.getKey() == -1 ? "未定位" : String.valueOf(posPair.getKey()));
                        geneInfo.setPosition(posPair.getValue());
                        System.out.println(geneInfo);
                        geneInfos.add(geneInfo);
                    }
                    while (geneNameListPage.hasMore()) {
                        geneNameListPage = geneList(httpClient, geneNameListPage.getPageIndex() + 1, "gene", searchResult.getLoginNo());
                        for (GeneName geneName : geneNameListPage.getNameList()) {
                            GeneInfo geneInfo = new GeneInfo();
                            geneInfo.setFeature(kw);
                            geneInfo.setGeneEnName(geneName.getEnName());
                            geneInfo.setGeneZhName(geneName.getZhName());
                            geneInfo.setLoginNo(searchResult.getLoginNo());
                            Pair<Integer, String> posPair = positionInfo(httpClient, geneName.getUrl(), "http://www.ricedata.cn/ontology/ontolists.aspx?db=gene&ta=" + searchResult.getLoginNo());
                            geneInfo.setChromosome(posPair.getKey() == -1 ? "未定位" : String.valueOf(posPair.getKey()));
                            geneInfo.setPosition(posPair.getValue());
                            System.out.println(geneInfo);
                            geneInfos.add(geneInfo);
                        }
                    }
                }
            }
            BigExcelWriter bigWriter = ExcelUtil.getBigWriter(new File("C:\\Users\\wangc\\Desktop\\data_0.xlsx"));
            List<List<String>> rows = new ArrayList<>();
            rows.add(List.of("登录号", "特征", "基因英文名", "基因中文名", "所在染色体", "基因所在位置"));
            for (GeneInfo info : geneInfos) {
                rows.add(List.of(info.getLoginNo(), info.getFeature(), info.getGeneEnName(), info.getGeneZhName(), info.getChromosome(), info.getPosition()));
            }
            bigWriter.write(rows);
            bigWriter.close();
            System.out.println(JSONUtil.toJsonStr(geneInfos));
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
            GeneNameListPage page = new GeneNameListPage();
            page.setPageIndex(pageIndex);
            Document geneListDoc = Jsoup.parse(EntityUtils.toString(pageRes.getEntity()));
            Element table = geneListDoc.getElementsByTag("table").first();
            Elements rows = table.getElementsByTag("tr");
            Element first = rows.first();
//            <tr>
//            <td colspan='4'>
//            <b>38</b>条记录/每页
//            <b>20</b>：
//            <a style='font-size:14' href='ontolists.aspx?p=1&db=gene&ta=TO:0000370'>1</a>&nbsp;
//            <a style='font-size:14' href='ontolists.aspx?p=2&db=gene&ta=TO:0000370'><b><u>2</u></b></a>&nbsp;
//            </td><tr>
//            <tr>
//              <td colspan="4"><b>0</b>条记录/每页<b>20</b>：</td>
//            </tr>
            Elements bs = first.getElementsByTag("b");
            page.setTotalCount(Integer.parseInt(bs.get(0).text()));
            page.setPageSize(Integer.parseInt(bs.get(1).text()));
            Elements as = first.getElementsByTag("a");
            if (as.size() == 0) {
                page.setTotalPage(1);
            } else {
                page.setTotalPage(Integer.parseInt(as.last().text()));
            }
            List<GeneName> nameList = new ArrayList<>();
            for (int i = 2; i < rows.size(); i ++) {
                GeneName geneName = new GeneName();
                Element row = rows.get(i);
//                <tr>
//                <td width='200px' height='23' style='border-bottom: dotted silver 1px'>
//                  <a target='_blank' target=_blank href='../gene/list/2466.htm'><i>LPA1; OsIDD14</i></a>
//                </td>
//                <td style='border-bottom: dotted silver 1px'>松散株型</td>
//                </tr>
                Elements cols = row.getElementsByTag("td");
                geneName.setEnName(cols.first().text());
                geneName.setZhName(cols.get(1).text());
                geneName.setUrl("http://www.ricedata.cn" + row.getElementsByTag("a").first().attr("href").replace("..", ""));
                nameList.add(geneName);
            }
            page.setNameList(nameList);
            return page;
        }
    }

    private final static Pattern NUM_PATTER = Pattern.compile("(\\d+)");

    private static Pair<Integer, String> positionInfo(CloseableHttpClient httpClient, String url, String refer) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", UA);
        httpGet.setHeader("Referer", refer);
        try (CloseableHttpResponse chromosomeRes = httpClient.execute(httpGet)) {
            Document chromosomeDoc = Jsoup.parse(EntityUtils.toString(chromosomeRes.getEntity(), "gb2312"));
            Elements rows = chromosomeDoc.getElementsByTag("tr");
            Element chromosomeRow = null;
            String kw = "所在染色体";
            for (Element row : rows) {
                if (row.text().contains(kw)) {
                    chromosomeRow = row;
                    break;
                }
            }
            int chromosome = -1;
            if (null != chromosomeRow) {
                String chromosomeText = chromosomeRow.getElementsByTag("td").first().text();
                Matcher matcher = NUM_PATTER.matcher(chromosomeText);
                if (matcher.find()) {
                    chromosome = Integer.parseInt(matcher.group(1));
                }
            }
            Elements as = chromosomeDoc.getElementsByTag("a");
            String position = "无";
            for (Element a : as) {
                if (a.text().contains("本地")) {
                    String positionUrl = "http://www.ricedata.cn/gene/" + a.attr("href").replace("..", "");
                    HttpGet posGet = new HttpGet(positionUrl);
                    posGet.setHeader("User-Agent", UA);
                    posGet.setHeader("Referer", url);
                    try (CloseableHttpResponse posRes = httpClient.execute(posGet)) {
                        Document posDoc = Jsoup.parse(EntityUtils.toString(posRes.getEntity(), "gb2312"));
                        Elements posRows = posDoc.getElementsByTag("tr");
                        for (Element posRow : posRows) {
                            if (posRow.text().contains("基因位置")) {
                                position = posRow.getElementsByTag("td").get(1).text();
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            return new Pair<>(chromosome, position);
        }
    }
}
