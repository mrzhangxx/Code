package com.xuecheng.test.freemarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/10 下午7:56
 */
@Controller
@RequestMapping("/freemarker")
public class TestFreemarker {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/banner")
    public String index_banner(Map<String,Object> map){
        String data = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(data, Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);
        return "index_banner";
    }

}
