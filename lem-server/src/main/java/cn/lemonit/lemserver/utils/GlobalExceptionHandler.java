package cn.lemonit.lemserver.utils;

import cn.lemonit.lemserver.domian.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *@description 处理所有不可知的异常(系统异常)
     */

    @ExceptionHandler({Exception.class})
    public Result systemException(Exception e){
        e.printStackTrace();
        return ResultUtil.error(ErrorMsg.common_server_internal_error.toString());
    }

    //service层检查参数抛出异常
    @ExceptionHandler({BaseBusinessException.class})
    public Result businessException(BaseBusinessException e) {
        e.printStackTrace();
        return ResultUtil.error(e.getErrMsg());
    }
}

