package cn.lemonit.lemserver.utils;

import cn.lemonit.lemserver.domian.Result;
import java.util.*;

public class ResultUtil {
    public static Result success(Object object){
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("");
        if(object==null||object.equals("null")){
            result.setData(new HashMap<>());
        }else {
            result.setData(object);
        }
        return result;
    }

    public static Result error(String message){
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(message);
        result.setData(new HashMap());
        return result;
    }
}
