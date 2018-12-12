package com.xuecheng.test.freemarker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/10 下午7:56
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFreemarker {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Test
    public void testGirdFS() throws FileNotFoundException {
        //要存储的文件
        File file = new File("/Users/apple/Documents/index_banner.ftl");
        //定义输入流
        FileInputStream inputStream = new FileInputStream(file);
        //向GridFS存储文件
        Object objectId = gridFsTemplate.store(inputStream, "轮播图测试文件01");
        System.out.println(objectId);

    }


}
