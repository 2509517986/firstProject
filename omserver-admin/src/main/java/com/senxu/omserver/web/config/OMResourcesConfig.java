package com.senxu.omserver.web.config;

import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.common.config.RuoYiConfig;
import com.senxu.omserver.common.constant.Constants;
import com.senxu.omserver.framework.interceptor.RepeatSubmitInterceptor;
/*import com.senxu.omserver.web.controller.service.NWOMServer;*/
import com.senxu.omserver.web.controller.service.NWOMServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 通用配置
 * 若已用apache映射或nigix映射，这里失效
 * @author ruoyi
 */
@Configuration("OMResourcesConfig")//避免与框架的ResourcesConfig同名
public class OMResourcesConfig extends WebMvcConfigurerAdapter {

    @Autowired
    public   OmServerProperties omServerProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
      //  registry.addResourceHandler("/"+ NWOMServer.shortVideoMappingBasePath+ "/**").addResourceLocations("file:" + NWOMServer.shortVideoPath + "/");
        registry.addResourceHandler("/"+ omServerProperties.shortVideoMappingBasePath+ "/**").addResourceLocations("file:" + omServerProperties.shortVideoPath + "/");

        registry.addResourceHandler("/"+ omServerProperties.mappingBasePath+ "/**").addResourceLocations("file:" + omServerProperties.capPicPath + "/");
         super.addResourceHandlers(registry);
    }


}