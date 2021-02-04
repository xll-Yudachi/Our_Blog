package com.ourblog.common.dto.et;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TagSearchDto
 * @Description 外部工具查询传输类
 * @Author Yudachi
 * @Date 2021/2/3 11:25
 * @Version 1.0
 */
@Data
public class ETSearchDto implements Serializable {
    private Integer page;
    private Integer size;
    private String query;
}
