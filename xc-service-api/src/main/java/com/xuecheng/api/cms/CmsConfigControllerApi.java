package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/10 下午7:02
 */
@Api(value="dataURL接口",description = "实现页面静态化")
public interface CmsConfigControllerApi {

    /**
     * 获取dataURL地址
     * @param id
     * @return
     */
    @ApiOperation("根据id查询页面")
    public CmsConfig findById(String id);


}
