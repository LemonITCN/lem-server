package cn.lemonit.lemserver.utils;

import cn.lemonit.lemserver.domian.Result;

public class BaseBusinessException extends RuntimeException  {
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public BaseBusinessException(String errMsg) {
        this.errMsg = errMsg;
    }
}
