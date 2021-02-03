package com.ourblog.article.utils;


import com.alibaba.fastjson.JSON;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.util.Base64;
import java.util.HashMap;
import java.util.*;

@Component
public class EsUtils {
    @Autowired
    private RestTemplate restTemplate;
    private final String ES_HTTP_URL = "http://8.129.233.53:9200";
    private final String ARTICLE_INDEX = "article";

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        httpHeaders.setContentType(type);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        httpHeaders.set("authorization", "Basic " + Base64.getEncoder().encodeToString("elastic:cppcyylsq123456".getBytes()));
        return httpHeaders;
    }

    /**
     * 按照关键词检索
     * @param type  1:文章检索   2：标签检索  3：作者名字检索
     * @param keyword
     * @param size
     * @param page
     * @return
     */
    public List searchByKeyword(int type,String keyword,int size,int page){
        String body = null;
        switch (type){
            case 1:
                body=getKeywordSearchDslForArticle(keyword, size, page);
                break;
            case 2:
                body=getKeywordSearchDslForTags(keyword,size,page);
                break;
            case 3:
                body=getKeywordSearchDslForAuthorName(keyword,size,page);
                break;
        }
        return searchByKeywordForArticle(body);
    }


    /**
     * 增加文章内容
     * @param article
     * @param content
     * @param authorName
     * @return
     */
    public boolean bulkAddDoc(Article article, ArticleContent content,String authorName){
        HttpHeaders httpHeaders = getHttpHeaders();
        String body=getBulkInsertDsl(article,content,authorName);
        HttpEntity<String> formEntity = new HttpEntity<String>(body, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(ES_HTTP_URL  + "/" + "_bulk", HttpMethod.POST, formEntity, String.class);
        HttpStatus statusCode = exchange.getStatusCode();

        if(statusCode == HttpStatus.OK){
            HashMap hashMap = JSON.parseObject(exchange.getBody(), HashMap.class);
            return (Boolean) hashMap.get("errors");
        }

        return false;
    }


    private List searchByKeywordForArticle(String body) {
        if(body==null)
            return null;
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity<String> formEntity = new HttpEntity<String>(body, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(ES_HTTP_URL + "/" + ARTICLE_INDEX + "/" + "_search", HttpMethod.POST, formEntity, String.class);
        HttpStatus statusCode = exchange.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            String result = exchange.getBody();
            return getSearchResult(result);
        }
        return null;
    }


    private List getSearchResult(String body) {
        HashMap hashMap = JSON.parseObject(body, HashMap.class);
        HashMap map = JSON.parseObject(hashMap.get("hits").toString(), HashMap.class);
        ArrayList result = JSON.parseObject(map.get("hits").toString(), ArrayList.class);
        ArrayList list = new ArrayList();
        result.forEach(e -> list.add(JSON.parseObject(e.toString(), HashMap.class)));
        String s = JSON.parseObject(hashMap.get("aggregations").toString(), HashMap.class).get("total_size").toString();
        List list1 = parseResult(list);
        list1.add(JSON.parseObject(s, HashMap.class).get("value"));
        return list1;
    }

    private List parseResult(List<HashMap<String, Object>> list) {
        List<Map<String, Object>> result = new ArrayList<>();

        list.forEach(e -> {
            HashMap highlight =e.get("highlight")!=null? JSON.parseObject(e.get("highlight").toString(), HashMap.class):null;
            HashMap source = JSON.parseObject(e.get("_source").toString(), HashMap.class);
            if(highlight==null){
                source.put("content",StringUtils.substring(source.get("content").toString(),0,20));
                result.add(source);
                return;
            }
            String titleIk = highlight.get("title.ik") != null ? ((List) highlight.get("title.ik")).get(0).toString() : null;
            String titlePy = highlight.get("title.pinyin") != null ? ((List) highlight.get("title.pinyin")).get(0).toString() : null;
            String contentIk = highlight.get("content.ik") != null ? ((List) highlight.get("content.ik")).get(0).toString() : null;
            String contentPy = highlight.get("content.pinyin") != null ? ((List) highlight.get("content.pinyin")).get(0).toString() : null;
            String tagsComma = highlight.get("tags.comma") != null ? ((List) highlight.get("tags.comma")).get(0).toString() : null;
            String tags = highlight.get("tags") != null ? ((List) highlight.get("tags")).get(0).toString() : null;
            String authorNameIK=highlight.get("authorName.ik")!=null ?((List)highlight.get("authorName.ik")).get(0).toString():null;
            String authorNamePy=highlight.get("authorName.pinyin")!=null ?((List)highlight.get("authorName.pinyin")).get(0).toString():null;
            if (titleIk != null || titlePy != null)
                source.put("title", titleIk != null ? titleIk : titlePy);
            if (contentIk != null || contentPy != null) {
                String content = contentIk != null ? contentIk : contentPy;
                Long size = (long) content.length();
                if (size > 20) {
                    int i = content.indexOf("<");
                    int i1 = content.lastIndexOf(">");
                    String substring = StringUtils.substring(content, i > 10 ? i - 10 : 0, Math.toIntExact(i1 + 10 > size ? size : i1 + 10)+1);
                    source.put("content", substring);
                }
            }
            if (tagsComma != null || tags != null)
                source.put("tags", tagsComma != null ? tagsComma : tags);
            if(authorNameIK!=null||authorNamePy!=null)
                source.put("authorName",authorNameIK!=null?authorNameIK:authorNamePy);
            result.add(source);
        });
        return result;
    }


    private String getKeywordSearchDslForArticle(String keyword, int size, int page) {
        String dsl = "{\n" +
                "  \"query\":{\n" +
                "    \"bool\": {\n" +
                "      \"filter\": {\n" +
                "        \"term\": {\n" +
                "          \"isDelete\": \"0\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"bool\": {\n" +
                "            \"should\": [\n" +
                "              {\n" +
                "                \"match\": {\n" +
                "                  \"content.ik\": \"" + keyword + "\"\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"match\": {\n" +
                "                  \"content.pinyin\": \"" + keyword + "\"\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"match\": {\n" +
                "                  \"title.ik\": \"" + keyword + "\"\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"match\": {\n" +
                "                  \"title.pinyin\": \"" + keyword + "\"\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"collapse\":{\n" +
                "    \"field\":\"id\"\n" +
                "  },\n" +
                "  \"aggs\":{\n" +
                "    \"total_size\":{\n" +
                "      \"cardinality\":{\n" +
                "        \"field\":\"id\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"sort\":[\n" +
                "    {\n" +
                "      \"updateTime\":{\n" +
                "        \"order\":\"desc\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"size\":" + size + ",\n" +
                "  \"from\":" + page + ",\n" +
                "  \"highlight\":{\n" +
                "     \"pre_tags\": \"<p class='key' style='color:red'>\",    \n" +
                "    \"post_tags\": \"</p>\",\n" +
                "    \"fields\": {\n" +
                "      \"content.ik\": {},\n" +
                "      \"content.pinyin\": {},\n" +
                "      \"title.ik\": {},\n" +
                "      \"title.pinyin\": {}\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
        return dsl;
    }
    private String getKeywordSearchDslForTags(String keyword,int size,int page){
        String dsl="{\n" +
                "  \"query\":{\n" +
                "    \"bool\": {\n" +
                "      \"filter\": {\n" +
                "        \"term\": {\n" +
                "          \"isDelete\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"bool\": {\n" +
                "            \"should\": [\n" +
                "              {\n" +
                "                \"match\": {\n" +
                "                  \"tags.comma\": \""+keyword+"\"\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"highlight\":{\n" +
                "     \"pre_tags\": \"<p class='key' style='color:red'>\",    \n" +
                "    \"post_tags\": \"</p>\",\n" +
                "    \"fields\": {\n" +
                "      \"content.ik\": {},\n" +
                "      \"content.pinyin\": {},\n" +
                "      \"title.ik\": {},\n" +
                "      \"title.pinyin\": {},\n" +
                "      \"tags.comma\": {},\n" +
                "      \"tags\": {}\n" +
                "    }\n" +
                "  },\n" +
                "    \"collapse\":{\n" +
                "    \"field\":\"id\"\n" +
                "  },\n" +
                "  \"aggs\":{\n" +
                "    \"total_size\":{\n" +
                "      \"cardinality\":{\n" +
                "        \"field\":\"id\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"sort\":[\n" +
                "    {\n" +
                "      \"updateTime\":{\n" +
                "        \"order\":\"desc\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"size\":"+size+",\n" +
                "  \"from\":"+page+"\n" +
                "}";
        return dsl;
    }
    private String getKeywordSearchDslForAuthorName(String keyword,int size,int page){
        String dsl="{\n" +
                "  \"query\":{\n" +
                "    \"bool\": {\n" +
                "      \"filter\": {\n" +
                "        \"term\": {\n" +
                "          \"isDelete\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"bool\": {\n" +
                "            \"should\": [\n" +
                "              {\n" +
                "                \"match\": {\n" +
                "                  \"authorName.ik\": \""+keyword+"\"\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"match\": {\n" +
                "                  \"authorName.pinyin\": \""+keyword+"\"\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"highlight\":{\n" +
                "     \"pre_tags\": \"<p class='key' style='color:red'>\",    \n" +
                "    \"post_tags\": \"</p>\",\n" +
                "    \"fields\": {\n" +
                "      \"content.ik\": {},\n" +
                "      \"content.pinyin\": {},\n" +
                "      \"title.ik\": {},\n" +
                "      \"title.pinyin\": {},\n" +
                "      \"tags.comma\": {},\n" +
                "      \"tags\": {},\n" +
                "      \"authorName.ik\": {},\n" +
                "      \"authorName.pinyin\": {}\n" +
                "    }\n" +
                "  },\n" +
                "    \"collapse\":{\n" +
                "    \"field\":\"id\"\n" +
                "  },\n" +
                "  \"aggs\":{\n" +
                "    \"total_size\":{\n" +
                "      \"cardinality\":{\n" +
                "        \"field\":\"id\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"sort\":[\n" +
                "    {\n" +
                "      \"updateTime\":{\n" +
                "        \"order\":\"desc\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"size\":"+size+",\n" +
                "  \"from\":"+page+"\n" +
                "}";
        return dsl;
    }
    private String getBulkInsertDsl(Article article, ArticleContent content,String authorName){
        return "{\"index\":{\"_index\":\""+ARTICLE_INDEX+"\",\"_type\":\"_doc\",\"_id\":\""+article.getId()+"\"}}\n" +
                "{\"id\":"+article.getId()+",\"authorId\":\""+article.getuId()+"\",\"authorName\":\""+authorName+"\",\"content\":\""+content.getContent()+"\"," +
                "\"title\":\""+article.getTitle()+"\",\"isDelete\":\""+article.getIsDelete()+"\",\"tags\":\""+article.getTags()+"\",\"watch\":\""+article.getWatch()+"\",\"updateTime\":\""+article.getUpdateTime()+"\"}\n";
    }
}
