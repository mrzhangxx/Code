package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/14 下午9:01
 */
@Mapper
public interface CategoryMapper {
    //查询分类
    public CategoryNode findList();
}
