package com.ourblog.article.controller;

import com.ourblog.article.service.TagService;
import com.ourblog.common.bean.article.Tag;
import com.ourblog.common.dto.article.TagSaveDto;
import com.ourblog.common.dto.article.TagSearchDto;
import com.ourblog.common.model.response.PageResult;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.articleCode.ArticleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TagController
 * @Description 文章标签控制层
 * @Author Yudachi
 * @Date 2021/2/3 11:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/findAll")
    public Result findAll(@RequestParam(value = "query", required = false) String tag) {
        List<Tag> dbTagList = tagService.findAll(tag);
        if (dbTagList != null && dbTagList.size() > 0) {
            return new Result(ArticleCode.SEARCH_TAG_SUCCESS, dbTagList);
        } else {
            return new Result(ArticleCode.SEARCH_TAG_FAIL);
        }
    }

    @PostMapping("/findAllByPage")
    public Result findAllByPage(@RequestBody TagSearchDto tagSearchDto) {
        PageResult<Tag> pageResult = tagService.findAllByPage(tagSearchDto);
        if (pageResult != null && pageResult.getRows() != null && pageResult.getRows().size() > 0) {
            return new Result(ArticleCode.SEARCH_TAG_SUCCESS, pageResult);
        } else {
            return new Result(ArticleCode.SEARCH_TAG_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody TagSaveDto tagSaveDto) {
        Tag saveTag = tagService.add(tagSaveDto);
        if (saveTag == null) {
            return new Result(ArticleCode.SAVE_TAG_FAIL);
        } else {
            return new Result(ArticleCode.SAVE_TAG_SUCCESS, saveTag);
        }
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody List<Long> ids) {
        tagService.delete(ids);
        return new Result(ArticleCode.DELETE_TAG_SUCCESS);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Tag tag) {
        Tag updateTag = tagService.update(tag);
        if (updateTag == null) {
            return new Result(ArticleCode.UPDATE_TAG_FAIL);
        } else {
            return new Result(ArticleCode.UPDATE_TAG_SUCCESS, updateTag);
        }
    }
}
