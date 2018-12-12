package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/5 下午12:37
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
