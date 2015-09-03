package com.eti.backend;

import com.eti.util.BackendException;
import java.util.List;


/**
 *
 * @author nicolas
 */
public interface CRUDBackend < T extends Object > {
    
    T update(T object) throws BackendException;
    void create(T object) throws BackendException;
    void remove(T object) throws BackendException;
    T read(String identifier) throws BackendException;
    List<T> list() throws BackendException;
    
    
    
}
