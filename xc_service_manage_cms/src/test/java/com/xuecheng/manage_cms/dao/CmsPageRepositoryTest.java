package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/5 下午12:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    /*
    查询所有cmsPage数据
     */
    @Test
    public void testFindAll(){
        List<CmsPage> cmsPages = cmsPageRepository.findAll();
        System.out.println(cmsPages);
    }

    /**
     * 分页测试
     */
    @Test
    public void testFindByPage(){
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);
        System.out.println(cmsPages);
    }

    /**
     * 添加
     */
    @Test
    public void testAdd(){
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        cmsPage.setPageAliase("hahhaha");
        cmsPage.setPageCreateTime(new Date());
        CmsPage insert = cmsPageRepository.insert(cmsPage);
        System.out.println(insert);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("5bdfccf9e380a734334cad04");
    }
    
    @Test
    public void testUpdate(){
        Optional<CmsPage> optional = cmsPageRepository.findById("5bdfcf43e380a73f46e83de7");
        if (optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("调试页面");
            cmsPageRepository.save(cmsPage);
        }
    }
    
    @Test
    public void testfindAll(){
        //1.创建查询条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching();
        //给页面别名属性添加模糊匹配
        matcher = matcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //2.创建查询条件实体对象
        CmsPage page = new CmsPage();
        //站点ID
        page.setSiteId("5a751fab6abb5044e0d19ea1");
        //模板ID
        page.setTemplateId("5a962bf8b00ffc514038fafa");
        //页面别名
        page.setPageAliase("轮播");

        //3.根据匹配器和查询条件对象创建出条件查询对象
        Example<CmsPage> example= Example.of(page,matcher);

        //4.创建分页对象
        Pageable pageable = PageRequest.of(0,10);
        //5.使用条件查询对象和分页对象查询出条件分页数据
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageable);
        System.out.println(cmsPages.getContent());
    }
}
