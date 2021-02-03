package com.ourblog.common.dto.invite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName InviteDto
 * @Description 邀请码传输类
 * @Author Yudachi
 * @Date 2021/2/3 10:26
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class InviteDto {
    private String username;
    private String code;
    private String type;
}
