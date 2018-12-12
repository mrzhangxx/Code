package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/14 下午1:57
 */
@Mapper
public interface TeachPlanMapper {
    /**
     * 查询课程计划
     * @param courseId
     * @return
     */
    public TeachplanNode selectList(String courseId);


}
