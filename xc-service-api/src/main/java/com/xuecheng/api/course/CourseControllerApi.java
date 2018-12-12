package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/14 下午7:50
 */
@Api(value = "我的课程",description = "课程分类管理",tags = {"课程分类管理"})
public interface CourseControllerApi {

    @ApiOperation("查询我的课程列表")
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);
}
