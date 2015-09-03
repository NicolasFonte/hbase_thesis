package com.eti.client;

import com.eti.util.BackendException;
import com.eti.util.TableOperations;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * @author nicolas
 */
public class TestUser {
   
    public static void main(String args[]) throws IOException, BackendException
    {
        //Usuario nicolas = new Usuario("nicolas", "nicolasfontenele@gmail.com");
        // HBaseEmployeeBackend backend = new HBaseEmployeeBackend();        
        //backend.create(nicolas);
        
        // Employee usuario = backend.read("nicolasfontenele@gmail.com");
        // System.out.println( "usuario: " + usuario.getName() + " email: " + usuario.getEmail() );
        Configuration config = HBaseConfiguration.create(); 
        TableOperations.initEmployeeTable(config);
        TableOperations.initDepartmentTable(config);
        TableOperations.initCompanyTable(config);
        
//        ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("1#"));
//        
//        Get get = new Get(Bytes.toBytes("123"));
//        // get.addColumn(Bytes.toBytes("properties"),Bytes.toBytes( "1#nome"));
//        get.setFilter(filter);
//        HTable employeeTable = new HTable(config, "employee");
//        
//        Result result = employeeTable.get(get);
//        byte[]  value = result.getValue(Bytes.toBytes("properties"), Bytes.toBytes("1#nome"));
//        System.out.println(Bytes.toString(value));
    }
    
    
    
    
}
