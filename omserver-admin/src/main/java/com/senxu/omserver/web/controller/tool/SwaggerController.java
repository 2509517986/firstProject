package com.senxu.omserver.web.controller.tool;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.senxu.omserver.common.core.controller.BaseController;

/**
 * swagger 接口
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController
{
    @PreAuthorize("@ss.hasPermi('tool:swagger:view')")
    @GetMapping()
    public String index()
    {//用http://localhost:8080/swagger-ui.html访问
        return redirect("/swagger-ui.html");
    }
}
