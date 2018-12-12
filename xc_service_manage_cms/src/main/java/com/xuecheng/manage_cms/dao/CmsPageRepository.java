package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/5 下午12:37
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据页面名称、站点id、页面访问路径查询
    CmsPage findByPageNameAndPageWebPathAndSiteId(String pageName,String PageWebPath,String siteId);
}
