package com.ourblog.common.dto.article;

import com.ourblog.common.bean.article.Tag;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TagSearchDto
 * @Description 文章标签分类保存类
 * @Author Yudachi
 * @Date 2021/2/3 11:25
 * @Version 1.0
 */
@Data
public class TagSaveDto implements Serializable {
    private Tag tag;
    private String type;
}
