package com.ourblog.et.controller;

import com.ourblog.common.bean.article.Tag;
import com.ourblog.common.bean.et.ExternalToolMap;
import com.ourblog.common.dto.et.ETSearchDto;
import com.ourblog.common.model.response.PageResult;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.etCode.ETCode;
import com.ourblog.et.service.ETService;
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
@RequestMapping("/et")
public class ETController {

    @Autowired
    private ETService etService;

    @GetMapping("/findAll")
    public Result findAll(@RequestParam(value = "query", required = false) String name) {
        List<ExternalToolMap> dbEtList = etService.findAll(name);
        if (dbEtList != null && dbEtList.size() > 0) {
            return new Result(ETCode.SEARCH_SUCCESS, dbEtList);
        } else {
            return new Result(ETCode.SEARCH_FAIL);
        }
    }

    @PostMapping("/findAllByPage")
    public Result findAllByPage(@RequestBody ETSearchDto eTSearchDto) {
        PageResult<ExternalToolMap> pageResult = etService.findAllByPage(eTSearchDto);
        if (pageResult != null && pageResult.getRows() != null && pageResult.getRows().size() > 0) {
            return new Result(ETCode.SEARCH_SUCCESS, pageResult);
        } else {
            return new Result(ETCode.SEARCH_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody ExternalToolMap externalToolMap) {
        ExternalToolMap saveExternalToolMap = etService.add(externalToolMap);
        if (saveExternalToolMap == null) {
            return new Result(ETCode.SAVE_FAIL);
        } else {
            return new Result(ETCode.SAVE_SUCCESS, saveExternalToolMap);
        }
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody List<Long> ids) {
        etService.delete(ids);
        return new Result(ETCode.DELETE_SUCCESS);
    }

    @PutMapping("/update")
    public Result update(@RequestBody ExternalToolMap externalToolMap) {
        ExternalToolMap updateExternalToolMap = etService.update(externalToolMap);
        if (updateExternalToolMap == null) {
            return new Result(ETCode.UPDATE_FAIL);
        } else {
            return new Result(ETCode.UPDATE_SUCCESS, updateExternalToolMap);
        }
    }
}
