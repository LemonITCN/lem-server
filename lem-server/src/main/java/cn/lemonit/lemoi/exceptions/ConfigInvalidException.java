package cn.lemonit.lemoi.exceptions;

public class ConfigInvalidException extends Exception {

    public ConfigInvalidException() {
        super("The configuration object you provided is invalid for the current client.");
    }
}
