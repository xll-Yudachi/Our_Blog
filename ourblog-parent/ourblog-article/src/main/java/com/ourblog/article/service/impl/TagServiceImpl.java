package com.ourblog.article.service.impl;

import com.ourblog.article.repository.TagRepository;
import com.ourblog.article.service.TagService;
import com.ourblog.common.bean.article.Tag;
import com.ourblog.common.constant.CTConstant;
import com.ourblog.common.dto.article.TagSaveDto;
import com.ourblog.common.dto.article.TagSearchDto;
import com.ourblog.common.model.response.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TagServiceImpl
 * @Description 文章标签服务实现类
 * @Author Yudachi
 * @Date 2021/2/3 11:14
 * @Version 1.0
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Override
    public List<Tag> findAll(String tag) {
        if (StringUtils.isEmpty(tag)){
            return tagRepository.findAll();
        }else{
            return tagRepository.findTagsByTagLike(tag);
        }
    }

    @Override
    public PageResult<Tag> findAllByPage(TagSearchDto tagSearchDto) {
        PageResult<Tag> pageResult = new PageResult<>();
        if (StringUtils.isEmpty(tagSearchDto.getQuery())){
            Page<Tag> tagPage = tagRepository.findAll(PageRequest.of(tagSearchDto.getPage(), tagSearchDto.getSize()));
            pageResult.setRows(tagPage.getContent());
            pageResult.setTotal(tagPage.getTotalElements());
            return pageResult;
        }else{
            Tag tag = new Tag();
            tag.setTag(tagSearchDto.getQuery());
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("tag" ,ExampleMatcher.GenericPropertyMatchers.contains());
            Example<Tag> example = Example.of(tag ,matcher);
            Page<Tag> tagPage = tagRepository.findAll(example, PageRequest.of(tagSearchDto.getPage(), tagSearchDto.getSize()));
            pageResult.setRows(tagPage.getContent());
            pageResult.setTotal(tagPage.getTotalElements());
            return pageResult;
        }
    }

    @Override
    public Tag add(TagSaveDto tagSaveDto) {
        String type = StringUtils.upperCase(tagSaveDto.getType());
        Long pid = tagSaveDto.getTag().getPid();
        if (pid == null && CTConstant.CATEGORY.equals(type)){
            Tag tag = tagSaveDto.getTag();
            tag.setPid(0L);
            return tagRepository.save(tag);
        }else if (pid != null && CTConstant.TAG.equals(type)){
            Tag tag = tagSaveDto.getTag();
            tag.setPid(pid);
            return tagRepository.save(tag);
        }
        return null;
    }

    @Override
    public void delete(List<Long> ids) {
        List<Tag> dbTagList = tagRepository.findAllById(ids);
        List<Tag> updateList = dbTagList.stream().filter(item -> item.getIsDelete() == 0).map(item -> {
            item.setIsDelete(1);
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
        tagRepository.saveAll(updateList);
    }

    @Override
    public Tag update(Tag tag) {
        Tag dbTag = tagRepository.findById(tag.getId()).get();
        if (dbTag == null){
            return null;
        }else{
            dbTag.setTag(tag.getTag());
            dbTag.setUpdateTime(new Date());
        }
        return tagRepository.save(dbTag);
    }
}
