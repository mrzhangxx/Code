package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.course.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysdictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/15 下午4:19
 */
@RestController
@RequestMapping("/sys/dicthinary")
public class sysDicthinaryController implements SysDicthinaryControllerApi {

    @Autowired
    private SysdictionaryService sysdictionaryService;

    @Override
    @GetMapping("/get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type) {
        return sysdictionaryService.getByType(type);
    }
}
