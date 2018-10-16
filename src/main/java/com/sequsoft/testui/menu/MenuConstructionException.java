package com.sequsoft.testui.menu;

public class MenuConstructionException extends RuntimeException {
    public MenuConstructionException(String message) {
        super(message);
    }

    public MenuConstructionException(String message, Throwable cause) {
        super(message, cause);
    }
}
