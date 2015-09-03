package com.eti.util;

/**
 *
 * @author nicolas
 */
public class BackendException extends Exception {

    public BackendException(String message) {
        super(message);
    }   
    
    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
    
        
    
}
