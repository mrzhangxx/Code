package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/5 下午12:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsConfigRepositoryTest {

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 根据url获取数据，并转为map格式。
     */
    @Test
    public void testRestTemplate(){
        ResponseEntity<Map> template = restTemplate.getForEntity("http://localhost:31001/cms/config/findById/5a791725dd573c3574ee333f", Map.class);
        System.out.println(template);
    }
}
