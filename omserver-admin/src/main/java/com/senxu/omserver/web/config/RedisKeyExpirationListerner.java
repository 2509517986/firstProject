package com.senxu.omserver.web.config;

import com.alibaba.fastjson.JSONObject;
import com.senxu.omserver.common.constant.DyUrlConstants;
import com.senxu.omserver.common.core.redis.RedisCache;
import com.senxu.omserver.service.service.DyLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.TimeUnit;

@Component
public class RedisKeyExpirationListerner extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisCache redisCache;
    @Autowired(required = false)
    private DyLoginService dyLoginService;

    public RedisKeyExpirationListerner(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 使用该方法监听 ,当我们的key失效的时候执行改方法
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String keyExpira = message.toString();
//        System.out.println("keyExpira======>>>>>>"+keyExpira);
        //拿到过期key ,处理业务逻辑
        if(keyExpira.equals("token")){
            if(redisCache.getCacheObject("refreshToken") != null){
//                System.out.println("refreshToken != null");
                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();;
                params.add("refreshToken",redisCache.getCacheObject("refreshToken"));
                String dyToken = dyLoginService.doPost(DyUrlConstants.header+DyUrlConstants.refreshToken,params);
                JSONObject object = JSONObject.parseObject(dyToken);
                JSONObject result = object.getJSONObject("result");
                redisCache.setCacheObject("token",result.getString("token"),28, TimeUnit.MINUTES);
            }else {
//                System.out.println("refreshToken == null");
                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
                params.add("username",DyUrlConstants.userName);
                params.add("password",DyUrlConstants.password);
                String dyToken = dyLoginService.doPost(DyUrlConstants.header+DyUrlConstants.loginV2,params);
                JSONObject object = JSONObject.parseObject(dyToken);
                JSONObject result = object.getJSONObject("result");
                redisCache.setCacheObject("token",result.getString("token"),28, TimeUnit.MINUTES);
                redisCache.setCacheObject("refreshToken",result.getString("refreshToken"),7, TimeUnit.DAYS);
            }

        }


    }
}