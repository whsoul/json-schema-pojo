package com.whsoul.jsch.exception;

public class JschNotSupportException extends RuntimeException {

    public JschNotSupportException(String msg){
        super(msg);
    };

    public JschNotSupportException(String msg, Throwable e){
        super(msg, e);
    };

}
