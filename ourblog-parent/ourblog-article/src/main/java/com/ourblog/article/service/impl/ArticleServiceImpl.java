package com.ourblog.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.ourblog.article.repository.ArticleContentRepository;
import com.ourblog.article.repository.ArticleRepository;
import com.ourblog.article.service.ArticleService;
import com.ourblog.article.service.feign.UserFeignService;
import com.ourblog.article.utils.EsUtils;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import com.ourblog.common.bean.user.User;
import com.ourblog.common.dto.article.*;
import com.ourblog.common.dto.article.ArticleDetailDto;
import com.ourblog.common.dto.article.ArticleEsSaveDto;
import com.ourblog.common.model.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleContentRepository articleContentRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserFeignService userFeignService;
    @Autowired
    EsUtils esUtils;

    @Override
    public List<Map<Article, ArticleContent>> findAll() {
        List<Article> all = articleRepository.findAll();
        List<Map<Article, ArticleContent>> list = new ArrayList<>();
       /* for(int i=0;i<all.size();i++){
            ArticleContent articleContent = articleContentRepository.findById(all.get(i).getCId()).get();
            Map<Article, ArticleContent> map= new HashMap<>();
            map.put(all.get(i),articleContent);
            list.add(map);
        }*/
        return list;
    }

    @Override
    public List<Article> deleteArticleByIds(List<Long> ids) {
        List<Article> articleList = articleRepository.findAllById(ids);
        List<Article> deleteArticleList = articleList.stream().map(article -> {
            article.setIsDelete(1);
            return article;
        }).collect(Collectors.toList());
        List<Article> saveAll = articleRepository.saveAll(deleteArticleList);
        return saveAll;
    }

    @Override
    public Article updateArticleById(ArticleDetailDto articleDetail) {
        //数据库的Article
        Article dbArticle = articleRepository.findById(articleDetail.getArticleId()).get();
        if (dbArticle == null) {
            return null;
        }
        // 更新后的Article
        dbArticle = articleDetailCopyPropertiesToArticle(dbArticle, articleDetail);
        // 数据库的ArticleContent
        ArticleContent dbArticleContent = articleContentRepository.findArticleContentByAid(articleDetail.getArticleId());
        // 更新ArticleContent
        if (StringUtils.isEmpty(articleDetail.getHtmlArticle())) {
            if (StringUtils.isEmpty(articleDetail.getMdArticle())) {
                dbArticleContent.setContent("");
            } else {
                dbArticleContent.setContent(articleDetail.getMdArticle());
            }
        } else {
            dbArticleContent.setContent(articleDetail.getHtmlArticle());
        }

        // 回存数据库
        Article save = articleRepository.save(dbArticle);
        articleContentRepository.save(dbArticleContent);

        return save;
    }

    @Override
    public List<ArticleIndexDto> getIndexArticle( int page) {

        List<Article> list = articleRepository.findArticleByIsDeleteOrderByUpdateTimeDesc( 0, PageRequest.of(page - 1, 10));

        List<ArticleIndexDto> dtos = articleIndexDto(list);
        Integer integer = articleRepository.countArticleByIsDelete(0);

        ArticleIndexDto dto = new ArticleIndexDto();
        dto.setTotalSize(integer);
        dtos.add(dto);
        return dtos;
    }

    @Override
    public List<ArticleIndexDto> getUserArticle(Long uid, int page) {
        if(uid==null||uid==0)
            return null;
        User user = JSON.parseObject(JSON.toJSONString(userFeignService.findUser(uid).getData()), User.class);
        List<Article> articles = articleRepository.findArticleByuIdAndIsDeleteOrderByUpdateTimeDesc(uid, 0, PageRequest.of(page - 1, 10));
        List<ArticleIndexDto> list = articleUserIndex(user, articles);
        Integer integer = articleRepository.countArticleByuIdAndIsDelete(uid, 0);
        ArticleIndexDto dto = new ArticleIndexDto();
        dto.setTotalSize(integer);
        list.add(dto);
        return  list;
    }

    @Override
    @Transactional
    public Article newArticle(ArticleDetailDto articleDetailDto) {
        Article article = new Article();
        ArticleData articleData = new ArticleData();
        articleData.setTime(new Date());
        articleDetailDto.setArticleData(articleData);
        articleDetailCopyPropertiesToArticle(article, articleDetailDto);
        //article.setTags(Arrays.toString(JSON.parseArray(article.getTags()).toArray()));
        article.setTags(JSON.toJSONString(articleDetailDto.getArticleTags()));
        article.setWatch(0);

        Article save = articleRepository.save(article);
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleDetailDto.getHtmlArticle() != null ? articleDetailDto.getHtmlArticle() : articleDetailDto.getMdArticle());
        articleContent.setAid(save.getId());
        articleContentRepository.save(articleContent);

        User user = JSON.parseObject(JSON.toJSONString(userFeignService.findUser(article.getuId()).getData()),User.class);
        //搜索标签和分类


        ArticleEsSaveDto build = ArticleEsSaveDto.builder().id(save.getId()).authorId(save.getuId())
                .authorName(articleDetailDto.getAuthorName())
                .content(articleContent.getContent())
                .title(article.getTitle())
                .updateTime(save.getUpdateTime())
                .watch(article.getWatch())
                .tags(StringUtils.join(articleDetailDto.getArticleTags().getTags(),","))
                .articleImg(article.getImage())
                .authorPic(user.getPic()==null?"":user.getPic())
                .isDelete(0).build();
        ArrayList<ArticleEsSaveDto> list = new ArrayList<>();
        list.add(build);
        esUtils.bulkAddDoc(list);

        return save;
    }

    @Override
    public List searchByKeyword(Map map) {
        List list = esUtils.searchByKeyword((int) map.get("type"), map.get("keyword").toString(), 10, ((int) map.get("page") - 1) * 10);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        Object remove = list.remove(list.size()-1);
        list.forEach(e->{
            //Map e1 = JSON.parseObject(JSON.toJSONString(e), Map.class);
            HashMap e1=(HashMap)e;
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id",e1.get("id"));
            map1.put("articleImg",e1.get("articleImg")==null?"":e1.get("articleImg"));
            map1.put("articleTitle",e1.get("title")==null?"":e1.get("title"));
            map1.put("articleContent",e1.get("content")==null?"":e1.get("content"));
            HashMap<String,Object> articleAuthor =new HashMap<>();
            articleAuthor.put("username",e1.get("authorName")==null?"":e1.get("authorName"));
            articleAuthor.put("authorPic",e1.get("authorPic")==null?"":e1.get("authorPic"));
            articleAuthor.put("authorId",e1.get("authorId")==null?"":e1.get("authorId"));
            map1.put("articleAuthor",articleAuthor);
            HashMap<String,Object> articleInfo=new HashMap<>();
            articleInfo.put("time",e1.get("updateTime")==null?"":e1.get("updateTime"));
            articleInfo.put("watch",e1.get("watch")==null?"0":e1.get("watch"));
            map1.put("articleInfo",articleInfo);
            maps.add(map1);
        });
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("totalSize",remove);
        maps.add(map1);
        return maps;
    }

    @Override
    public ArticleDetailDto getArticle(Long id) {
        if(id==null||id==0)
            return null;
        Optional<Article> byId = articleRepository.findById(id);
        if(byId.isPresent()){
            Article article = byId.get();
            User user = JSON.parseObject(JSON.toJSONString(userFeignService.findUser(article.getuId()).getData()),User.class);
            return getArticleDetailDto(user, article);
        }

        return null;
    }

    /**
     * @Author yudachi
     * @Description 文章详情属性填充到文章中
     * @Date 2021/1/29 16:16
     **/
    private Article articleDetailCopyPropertiesToArticle(Article article, ArticleDetailDto articleDetail) {
        article.setuId(articleDetail.getAuthorId());
        article.setImage(articleDetail.getArticleImg());
        article.setTitle(articleDetail.getArticleTitle());
        article.setTags(JSON.toJSONString(articleDetail.getArticleTags()));
        article.setUpdateTime(articleDetail.getArticleData().getTime());
        article.setWatch(articleDetail.getArticleData().getWatch());
        return article;
    }


    private List<ArticleIndexDto> articleIndexDto(List<Article> list){

        List<ArticleIndexDto> articleIndexDtoList=new ArrayList<>();
        list.forEach(e->{
            Long uid = e.getuId();
            User user = JSON.parseObject(JSON.toJSONString(userFeignService.findUser(uid).getData()),User.class);

            ArticleIndexDto dto = new ArticleIndexDto();
            dto.setId(e.getId());
            dto.setArticleImg(e.getImage());
            dto.setArticleTitle(e.getTitle());
            ArticleAuthorDto articleAuthorDto = new ArticleAuthorDto();
            articleAuthorDto.setAvatar(user.getPic()==null?"123":user.getPic());
            articleAuthorDto.setUsername(user.getUsername());
            articleAuthorDto.setAuthorId(user.getId());
            dto.setArticleAuthor(articleAuthorDto);
            ArticleInfoDto articleInfoDto = new ArticleInfoDto();
            articleInfoDto.setTime(e.getUpdateTime());
            articleInfoDto.setWatch(e.getWatch());
            dto.setArticleInfo(articleInfoDto);
            articleIndexDtoList.add(dto);
        });
        return articleIndexDtoList;
    }
    private List<ArticleIndexDto> articleUserIndex(User user,List<Article> list){

        List<ArticleIndexDto> articleIndexDtoList=new ArrayList<>();
        list.forEach(e->{
            ArticleIndexDto dto = new ArticleIndexDto();
            dto.setId(e.getId());
            dto.setArticleImg(e.getImage());
            dto.setArticleTitle(e.getTitle());
            ArticleAuthorDto articleAuthorDto = new ArticleAuthorDto();
            articleAuthorDto.setAvatar(user.getPic()==null?"123":user.getPic());
            articleAuthorDto.setUsername(user.getUsername());
            articleAuthorDto.setAuthorId(user.getId());
            dto.setArticleAuthor(articleAuthorDto);
            ArticleInfoDto articleInfoDto = new ArticleInfoDto();
            articleInfoDto.setTime(e.getUpdateTime());
            articleInfoDto.setWatch(e.getWatch());
            dto.setArticleInfo(articleInfoDto);
            articleIndexDtoList.add(dto);
        });
        return articleIndexDtoList;
    }
    private ArticleDetailDto getArticleDetailDto(User user,Article article){
        ArticleDetailDto dto = new ArticleDetailDto();
        dto.setAuthorId(Long.parseLong(user.getId().toString()));
        dto.setAuthorImg(user.getPic()==null?"":user.getPic());
        dto.setAuthorName(user.getUsername());
        dto.setArticleId(article.getId());
        dto.setArticleImg(article.getImage());
        dto.setArticleTitle(article.getTitle());
        dto.setArticleTags(JSON.parseObject(article.getTags(),ArticleTags.class));
        dto.setArticleData(new ArticleData(article.getCreateTime(),article.getWatch()));
        ArticleContent articleContent = articleContentRepository.findArticleContentByAid(article.getId());
        dto.setHtmlArticle(articleContent.getContent());
        return dto;
    }


}
