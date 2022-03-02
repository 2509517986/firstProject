package com.senxu.omserver.common.utils.ftl;

import java.net.URLEncoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author linyh
 * @email 1503386669@qq.com
 * @date 2020/7/27 14:55
 */
public class ExportWord {

    private static Logger logger = LoggerFactory.getLogger(ExportWord.class);
    private Configuration configuration;
    private String encoding;
    private String exportPath = "D:\\test\\";

    /**
     * 构造函数
     * 配置模板路径
     * @param encoding
     */
    public ExportWord(String encoding) {
        this.encoding = encoding;
        configuration = new Configuration();
        configuration.setDefaultEncoding(encoding);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    /**
     * 获取模板
     * @param name
     * @return
     * @throws Exception
     */
    public Template getTemplate(String name) throws Exception {
        return configuration.getTemplate(name);
    }

    /**
     * 导出word文档到指定目录
     * @param fileName
     * @param tplName
     * @param data
     * @throws Exception
     */
    public void exportDocFile(String fileName, String tplName, Map<String, Object> data) throws Exception {
        logger.debug("导出word到D:\test");
        //如果目录不存在，则创建目录
        File exportDirs = new File(exportPath);
        if (!exportDirs.exists()) {
            exportDirs.mkdirs();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(exportPath + fileName), encoding));
        getTemplate(tplName).process(data, writer);
    }

    /**
     * 导出word文档到客户端
     * @param response
     * @param fileName
     * @param tplName
     * @param data
     * @throws Exception
     */
    public void exportDoc(HttpServletResponse response, String fileName, String tplName, Map<String, Object> data) throws Exception {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msword");
        response.setHeader("Content-Disposition", "attachment; filename=" +  URLEncoder.encode(fileName , "UTF-8"));
        // 把本地文件发送给客户端
        Writer out = response.getWriter();
        Template template = getTemplate(tplName);
        template.process(data, out);
        out.close();
    }
}

