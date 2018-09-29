package com.whsoul.jsch.exception;

public class JschLibaryException extends RuntimeException {

    public JschLibaryException(String msg){
        super(msg);
    };

    public JschLibaryException(String msg, Throwable t){
        super(msg, t);
    };
}
