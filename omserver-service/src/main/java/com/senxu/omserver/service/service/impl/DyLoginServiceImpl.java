package com.senxu.omserver.service.service.impl;

import com.senxu.omserver.common.constant.DyUrlConstants;
import com.senxu.omserver.common.core.redis.RedisCache;
import com.senxu.omserver.service.service.DyLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class DyLoginServiceImpl implements DyLoginService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 多翼POST方法
     *
     * @param url
     * @param params
     * @return
     */
    @Override
    public String doPost(String url, MultiValueMap<String, Object> params) {
        try {
            RestTemplate client = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            HttpMethod method = HttpMethod.POST;
            // 以表单的方式提交
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String token = redisCache.getCacheObject("token");
            if(StringUtils.isEmpty(token)) {
                Thread.sleep(1000);
                token = redisCache.getCacheObject("token");
            }
            headers.set("Authorization", token);
            // 将请求头部和参数合成一个请求
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            // 执行HTTP请求，将返回的结构使用String类格式化
            ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "调用多翼接口doPost失败，请联系管理员";
        }

    }

    /**
     * 多翼GET方法
     *
     * @param url
     * @param params
     * @return
     */
    @Override
    public String doGet(String url, MultiValueMap<String, Object> params) {
        try {
            RestTemplate client = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            HttpMethod method = HttpMethod.GET;
            // 以表单的方式提交
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String token = redisCache.getCacheObject("token");
            if(StringUtils.isEmpty(token)) {
                Thread.sleep(1000);
                token = redisCache.getCacheObject("token");
            }
            headers.set("Authorization", token);
            // 将请求头部和参数合成一个请求
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            // 执行HTTP请求，将返回的结构使用String类格式化
            ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "调用多翼接口doGet失败，请联系管理员";
        }
    }

    /**
     * 多翼PUT方法
     *
     * @param url
     * @param params
     * @return
     */
    @Override
    public String doPut(String url, MultiValueMap<String, Object> params) {
        try {
            RestTemplate client = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            HttpMethod method = HttpMethod.PUT;
            // 以表单的方式提交
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String token = redisCache.getCacheObject("token");
            if(StringUtils.isEmpty(token)) {
                Thread.sleep(1000);
                token = redisCache.getCacheObject("token");
            }
            headers.set("Authorization", token);
            // 将请求头部和参数合成一个请求
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            // 执行HTTP请求，将返回的结构使用String类格式化
            ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "调用多翼接口doPost失败，请联系管理员";
        }

    }

    /**
     * 多翼DELETE方法
     *
     * @param url
     * @param params
     * @return
     */
    @Override
    public String doDel(String url, MultiValueMap<String, Object> params) {
        try {
            RestTemplate client = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            HttpMethod method = HttpMethod.DELETE;
            // 以表单的方式提交
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String token = redisCache.getCacheObject("token");
            if(StringUtils.isEmpty(token)) {
                Thread.sleep(1000);
                token = redisCache.getCacheObject("token");
            }
            headers.set("Authorization", token);
            // 将请求头部和参数合成一个请求
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            // 执行HTTP请求，将返回的结构使用String类格式化
            ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "调用多翼接口doPost失败，请联系管理员";
        }

    }

    /**
     * 获取多翼无人机图片接口
     *
     * @param url
     * @param params
     */
    @Override
    public void getImg(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;

        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        // 执行HTTP请求，将返回的结构使用String类格式化
        ResponseEntity<byte[]> response = client.exchange(url, method, requestEntity, byte[].class);
        byte[] img = response.getBody();
        File file = new File(DyUrlConstants.filepath);
        if (!file.exists()) {
            System.out.println("路径不存在");
            file.mkdir();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(DyUrlConstants.filepath+params.get("imageName").get(0).toString()+".jpg");
            fos.write(img, 0, img.length);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
