package cn.lemonit.lemoi.exceptions;

/**
 * 要操作的目标找不到异常
 *
 * @author liuri
 */
public class TargetNotExistsException extends Exception {

    public TargetNotExistsException(String target) {
        super("The target you want to operate does not exist: " + target);
    }
}
