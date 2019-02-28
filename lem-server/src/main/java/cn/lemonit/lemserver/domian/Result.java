package cn.lemonit.lemserver.domian;

import org.springframework.stereotype.Component;

@Component
public class Result {
    private boolean success;
    private String message;
    private Object data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
