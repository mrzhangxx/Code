package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/11 下午10:32
 */
@Controller
@RequestMapping("/cms")
public class CmsPagePreviewController extends BaseController {

    @Autowired
    private PageService pageService;

    @GetMapping("/preview/{pageId}")
    public void preview(@PathVariable("pageId") String pageId){
        String html = pageService.getPageHtml(pageId);
        if(StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(html.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
