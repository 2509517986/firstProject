package com.senxu.omserver.service.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.MultiValueMap;

public interface DyLoginService {

    public String  doPost(String url, MultiValueMap<String, Object> params);

    public String  doGet(String url, MultiValueMap<String, Object> params);

    public String  doPut(String url, MultiValueMap<String, Object> params);

    public String  doDel(String url, MultiValueMap<String, Object> params);

    public void getImg(String url, MultiValueMap<String, Object> params);

}
