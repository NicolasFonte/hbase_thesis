package com.eti.util;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 *
 * @author nicolas
 */
public class TableOperations {
    
    static public boolean dropTable(Configuration conf, String tableName) throws MasterNotRunningException, ZooKeeperConnectionException, IOException{
        HBaseAdmin admin = new HBaseAdmin(conf);
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
        
        return !admin.isTableAvailable(tableName);
        
    }
    
     public static boolean  initEmployeeTable(Configuration conf) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        
        HBaseAdmin admin = new HBaseAdmin(conf);
        
        HTableDescriptor tableDescriptor = new HTableDescriptor("employee");
        tableDescriptor.addFamily(new HColumnDescriptor("properties"));
        tableDescriptor.addFamily(new HColumnDescriptor("projects"));
        
        admin.createTable(tableDescriptor);
        boolean tableAvailable = admin.isTableAvailable("employee");
        System.out.println("employee available = " + tableAvailable);
        return tableAvailable;
        
    }
     
     public static boolean  initCompanyTable(Configuration conf) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor tableDescriptor = new HTableDescriptor("company");
        tableDescriptor.addFamily(new HColumnDescriptor("properties"));
        tableDescriptor.addFamily(new HColumnDescriptor("departments"));
        
        admin.createTable(tableDescriptor);
        boolean tableAvailable = admin.isTableAvailable("company");
        System.out.println("company available = " + tableAvailable);
        return tableAvailable;
        
    }
     
     public static boolean  initDepartmentTable(Configuration conf) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor tableDescriptor = new HTableDescriptor("department");
        tableDescriptor.addFamily(new HColumnDescriptor("properties"));
        
        admin.createTable(tableDescriptor);
        boolean tableAvailable = admin.isTableAvailable("department");
        System.out.println("department available = " + tableAvailable);
        return tableAvailable;
        
    }
    
    
}
