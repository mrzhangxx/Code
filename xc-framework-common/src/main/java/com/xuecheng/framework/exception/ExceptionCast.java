package com.xuecheng.framework.exception;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/9 上午11:12
 */

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 抛出异常工具类
 */
public class ExceptionCast {
    /**
     * 使用此静态方法抛出自定义异常
     * @param resultCode ResultCode 异常代码和异常信息
     */
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
