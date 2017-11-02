package io.inabsentia.superhangman.data.dao;

public class DALException extends Exception {

    public DALException(String msg, Throwable e) {
        super(msg, e);
    }

    public DALException(String msg) {
        super(msg);
    }

}