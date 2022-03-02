package com.senxu.omserver;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.senxu.omserver.common.constant.DyUrlConstants;
import com.senxu.omserver.common.core.redis.RedisCache;
import com.senxu.omserver.service.service.DyLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class OmServerGetDyToken implements ApplicationRunner {

    @Autowired
    private RedisCache redisCache;

    @Autowired(required = false)
    private DyLoginService dyLoginService;

    @Autowired
    private OmServerProperties omServerProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();;
        String username=DyUrlConstants.userName;
        String password=DyUrlConstants.password;
        if(!StringUtil.isEmpty(omServerProperties.getDYUserName())){
            username=omServerProperties.getDYUserName();
        }
        if(!StringUtil.isEmpty(omServerProperties.getDYPassword())){
            password=omServerProperties.getDYPassword();
        }

        if(!StringUtil.isEmpty(omServerProperties.getDYBaseUrl())){
            DyUrlConstants.header=omServerProperties.getDYBaseUrl();
        }
        params.add("username",username);
        params.add("password",password);
        String dyToken = dyLoginService.doPost(DyUrlConstants.header+DyUrlConstants.loginV2,params);
        if(!dyToken.startsWith("{")){
             log.error(dyToken);
            return;
        }
        JSONObject object = JSONObject.parseObject(dyToken);
        JSONObject result = object.getJSONObject("result");
        redisCache.setCacheObject("token",result.getString("token"),28, TimeUnit.MINUTES);
        redisCache.setCacheObject("refreshToken",result.getString("refreshToken"),7, TimeUnit.DAYS);
    }
}
