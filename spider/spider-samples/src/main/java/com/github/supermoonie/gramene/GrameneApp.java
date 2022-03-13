package com.github.supermoonie.gramene;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.supermoonie.HttpClientUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author supermoonie
 * @date 2020-10-31
 * <p>
 * https://archive.gramene.org/db/qtl/qtl_display?query=leaf+width&vocabulary=qtl&search_box_name=query&search_box_id=qtl_search_for&search_field=&species=Oryza+sativa&x=17&y=14
 */
public class GrameneApp {

    private final static String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36 Edg/84.0.522.40";

    private final static String SEARCH_URL = "https://archive.gramene.org/db/qtl/qtl_display?query=%s&vocabulary=qtl&search_box_name=query&search_box_id=qtl_search_for&search_field=&species=Oryza+sativa&x=17&y=14&page_no=%d";

    private final static String DETAIL_URL = "https://archive.gramene.org/db/qtl/qtl_display?qtl_accession_id=%S";

    private final static BasicCookieStore BASIC_COOKIE_STORE = new BasicCookieStore();

    private static final List<String> KW_LIST = new ArrayList<>();

    static {
        KW_LIST.add("grain+width");
        KW_LIST.add("seed+width");
        KW_LIST.add("grain+length");
        KW_LIST.add("seed+length");
        KW_LIST.add("grain+length+to+width+ratio");
        KW_LIST.add("seed+length+to+width+ratio");
        KW_LIST.add("seed+shape");
    }

    public static void main(String[] args) {
        HttpClientBuilder httpClientBuilder = HttpClientUtils.createTrustAllHttpClientBuilder();
        httpClientBuilder.setDefaultCookieStore(BASIC_COOKIE_STORE);
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        httpClientBuilder.setProxy(new HttpHost("127.0.0.1", 7890));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            // step 1
//            getAllDetailUrl(httpClient);
            // step 2
            getDetail(httpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void getDetail(CloseableHttpClient httpClient) {
        ExcelReader reader = ExcelUtil.getReader(new File("C:\\Users\\super_w\\Desktop\\data_1.xlsx"));
        int rowCount = reader.getRowCount();
        List<GrameneInfo> infoList = new ArrayList<>();
        for (int i = 0; i < rowCount; i ++) {
            String url = reader.readRow(i).get(0).toString();
            System.out.println((i + 1) + " " + url);
            try {
                HttpGet get = new HttpGet(url);
                get.setHeader("User-Agent", UA);
                try (CloseableHttpResponse res = httpClient.execute(get)) {
                    String raw = EntityUtils.toString(res.getEntity());
                    Document doc = Jsoup.parse(raw);
                    GrameneInfo info = new GrameneInfo();
                    info.setUrl(url);
                    Elements tab = doc.select(".data-table");
                    Elements rows = tab.first().select("tr");
                    for (Element row : rows) {
                        if (null == row.select("th") || 0 == row.select("th").size() || null == row.select("th").first()) {
                            continue;
                        }
                        String key = row.select("th").first().text();
                        String value = row.select("td").first().text();
                        if (key.contains("ID")) {
                            info.setAccessionId(value);
                        } else if (key.contains("Trait") && key.contains("Name")) {
                            info.setTraitName(value);
                        } else if (key.contains("Published") && key.contains("Symbol")) {
                            info.setPublishedSymbol(value);
                        } else if (key.contains("Chromosome")) {
                            info.setChromosome(value);
                        }
                    }
                    String position = tab.get(1).select("tr").get(1).select("td").get(4).text();
                    info.setPosition(position);
                    infoList.add(info);
                }
            } catch (Exception e) {
                System.out.println("error: " + url);
                e.printStackTrace();
            }
        }
        BigExcelWriter bigWriter = ExcelUtil.getBigWriter(new File("C:\\Users\\super_w\\Desktop\\data_2.xlsx"));
        bigWriter.write(infoList);
        bigWriter.close();
    }

    private static void getAllDetailUrl(CloseableHttpClient httpClient) {
        List<String> detailUrlList = new ArrayList<>();
        for (String kw : KW_LIST) {
            int index = 1;
            boolean next = true;
            while (next) {
                String url = String.format(SEARCH_URL, kw, index);
                System.out.println("begin " + url);
                HttpGet get = new HttpGet(url);
                get.setHeader("User-Agent", UA);
                try (CloseableHttpResponse res = httpClient.execute(get)) {
                    String rawSearchPage = EntityUtils.toString(res.getEntity());
                    Document docSearchPage = Jsoup.parse(rawSearchPage);
                    List<String> urlList = parseSearchPage(docSearchPage);
                    detailUrlList.addAll(urlList);
                    next = hasNext(docSearchPage);
                    index = index + 1;
                    System.out.println("end " + url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        BigExcelWriter bigWriter = ExcelUtil.getBigWriter(new File("C:\\Users\\super_w\\Desktop\\data_1.xlsx"));
        bigWriter.write(detailUrlList);
        bigWriter.close();
    }

    private static boolean hasNext(Document doc) {
        Elements tab = doc.select(".data-table");
        return tab.select("form").text().contains("Next");
    }

    private static List<String> parseSearchPage(Document doc) {
        Elements tab = doc.select(".data-table");
        Elements rows = tab.select("tr");
        List<String> list = new ArrayList<>();
        for (int i = 2; i < rows.size() - 1; i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            list.add(String.format(DETAIL_URL, cols.first().text()));
        }
        return list;
    }
}
