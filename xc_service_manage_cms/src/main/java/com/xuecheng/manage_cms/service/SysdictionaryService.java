package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/15 下午4:18
 */
@Service
@Transactional
public class SysdictionaryService {

    @Autowired
    private SysDicthinaryRepository sysDicthinaryRepository;

    public SysDictionary getByType(String type){
      return   sysDicthinaryRepository.getByDType(type);
    }
}
