package com.eti.backend.hbase;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;

/**
 *
 * @author nicolas
 */
public class HBaseBackend {

    Configuration config;
    private HTable companyTable;
    private HTable employeeTable;
    private HTable departmentTable;

    public HBaseBackend() {
    
        config = HBaseConfiguration.create();
    }

    public Configuration getConfig() {
        return config;
    }

    public HTable getCompanyTable() throws IOException {
        
        if (companyTable == null){
            companyTable = new HTable(getConfig(), "company");
        }        
        return companyTable;
    }
    
    public HTable getEmployeeTable() throws IOException {
        
        if (employeeTable == null){
            employeeTable = new HTable(getConfig(), "employee");
        }        
        return employeeTable;
    }
    
    public HTable getDepartmentTable() throws IOException {
        
        if (departmentTable == null){
            departmentTable = new HTable(getConfig(), "department");
        }        
        return departmentTable;
    }
    
    
}
