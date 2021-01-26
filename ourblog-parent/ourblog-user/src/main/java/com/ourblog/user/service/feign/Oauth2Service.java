package com.ourblog.user.service.feign;

import com.ourblog.common.model.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Component
@FeignClient("ourblog-oauth2-auth")
public interface Oauth2Service {
    @RequestMapping(value = "/oauth/token",method = RequestMethod.POST)
     Result postAccessToken( @RequestParam Map<String, Object> parameters);
}
