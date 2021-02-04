package com.ourblog.et.service.impl;

import com.ourblog.common.bean.et.ExternalToolMap;
import com.ourblog.common.dto.et.ETSearchDto;
import com.ourblog.common.model.response.PageResult;
import com.ourblog.et.repository.ETRepository;
import com.ourblog.et.service.ETService;
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
 * @ClassName ExternalToolMapServiceImpl
 * @Description 文章标签服务实现类
 * @Author Yudachi
 * @Date 2021/2/3 11:14
 * @Version 1.0
 */
@Service
public class ETServiceImpl implements ETService {
    @Autowired
    private ETRepository etRepository;
    @Override
    public List<ExternalToolMap> findAll(String name) {
        if (StringUtils.isEmpty(name)){
            return etRepository.findAll();
        }else{
            return etRepository.findExternalToolMapByNameLike(name);
        }
    }

    @Override
    public PageResult<ExternalToolMap> findAllByPage(ETSearchDto etSearchDto) {
        PageResult<ExternalToolMap> pageResult = new PageResult<>();
        if (StringUtils.isEmpty(etSearchDto.getQuery())){
            Page<ExternalToolMap> ExternalToolMapPage = etRepository.findAll(PageRequest.of(etSearchDto.getPage(), etSearchDto.getSize()));
            pageResult.setRows(ExternalToolMapPage.getContent());
            pageResult.setTotal(ExternalToolMapPage.getTotalElements());
            return pageResult;
        }else{
            ExternalToolMap externalToolMap = new ExternalToolMap();
            externalToolMap.setName(etSearchDto.getQuery());
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("name" ,ExampleMatcher.GenericPropertyMatchers.contains());
            Example<ExternalToolMap> example = Example.of(externalToolMap ,matcher);
            Page<ExternalToolMap> ExternalToolMapPage = etRepository.findAll(example, PageRequest.of(etSearchDto.getPage(), etSearchDto.getSize()));
            pageResult.setRows(ExternalToolMapPage.getContent());
            pageResult.setTotal(ExternalToolMapPage.getTotalElements());
            return pageResult;
        }
    }

    @Override
    public ExternalToolMap add(ExternalToolMap externalToolMap) {
        return etRepository.save(externalToolMap);
    }

    @Override
    public void delete(List<Long> ids) {
        List<ExternalToolMap> dbExternalToolMapList = etRepository.findAllById(ids);
        List<ExternalToolMap> updateList = dbExternalToolMapList.stream().filter(item -> item.getIsDelete() == 0).map(item -> {
            item.setIsDelete(1);
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
        etRepository.saveAll(updateList);
    }

    @Override
    public ExternalToolMap update(ExternalToolMap externalToolMap) {
        ExternalToolMap dbExternalToolMap = etRepository.findById(externalToolMap.getId()).get();
        if (dbExternalToolMap == null){
            return null;
        }else{
            dbExternalToolMap.setName(externalToolMap.getName());
            dbExternalToolMap.setUrl(externalToolMap.getUrl());
            dbExternalToolMap.setUpdateTime(new Date());
        }
        return etRepository.save(dbExternalToolMap);
    }
}
