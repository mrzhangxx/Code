package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/15 下午4:14
 */
public interface SysDicthinaryRepository extends MongoRepository<SysDictionary,String> {
    /**
     * 查询数据字典
     */
    public SysDictionary getByDType(String type);
}
