package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.TeachPlanControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.TeachPlanRepository;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/14 下午3:36
 */
@RestController
@RequestMapping("/course/teachPlan")
public class TeachPlanController implements TeachPlanControllerApi {

    @Autowired
    private CourseService courseService;


    @Override
    @GetMapping("/findList/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }
}
