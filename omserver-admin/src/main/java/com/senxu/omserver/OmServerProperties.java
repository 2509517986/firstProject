package com.senxu.omserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @auther yudarr
 * @date 2021/3/8 8:38
 **/
@Component
@Data
@ConfigurationProperties(prefix = "omserver")
public class OmServerProperties {
    public  String capPicPath = "e://capPic";
    public  String shortVideoPath = "e://shortVideo";
    public  String mappingBasePath = "capPic";//"capPic";//profile已定义了免登陆
    public   String shortVideoMappingBasePath = "shortVideo";
    public int serverPort=9000;
    public String serverIp;
    public int videoPort=8010;
    public int hisVideoPort=8011;
    public int rtmpServerPort=1935;
    public String mediaServerIp;//设备端发送媒体的ip
    public String mediaServerIpOnInNet;//设备端发送媒体的ip
    public String mediaProtocol="tcp";//""udp";
    public String DYUserName;
    public String DYPassword;
    public String DYBaseUrl;
    /**
     * 首页是否显示多翼全部计划
     */
    public boolean DYShowAllPlanOnIndex=false;


}
