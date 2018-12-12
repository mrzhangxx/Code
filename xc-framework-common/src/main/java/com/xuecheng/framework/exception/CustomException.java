package com.xuecheng.framework.exception;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/9 上午11:08
 */

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException {
    //异常代码和信息
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        //异常信息为错误代码+异常信息
        super("错误代码 :"+resultCode.code()+"错误信息 :"+resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
