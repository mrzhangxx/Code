package com.xuecheng.api.cms;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/5 上午11:59
 */

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 *定义一个CmsPage控制层接口类
 *   为前端提供规范的后端接口
 **/
@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    /**
     *根据条件分页查询CmsPage页面信息集合
     **/
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,
                    paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,
                    paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 添加
     */
    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("根据id查询页面")
    public CmsPageResult findById(String Id);

    @ApiOperation("修改页面")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "Id值", required = true,
                    paramType = "path", dataType = "String")
    )
    public CmsPageResult edit(String id,CmsPage cmsPage);


    /**
     * 根据id删除页面
     * @param id
     * @return
     */
    @ApiOperation("删除页面")
    public ResponseResult delete(String id);

    @ApiOperation("发布页面")
    public ResponseResult post(String pageId);
}