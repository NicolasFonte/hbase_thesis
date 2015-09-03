package com.eti.backend.hbase;

import com.eti.backend.ProjectBackend;
import com.eti.model.Employee;
import com.eti.model.Project;
import com.eti.util.BackendException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * @author nicolas
 */
public class HBaseProjectBackend extends HBaseBackend implements ProjectBackend {

    private static final byte[] FAMILY_PROPERTIES = Bytes.toBytes("properties"); 
    private static final byte[] PROPERTIES_NAME = Bytes.toBytes("name"); 
    private static final byte[] PROPERTIES_IDENTIFIER = Bytes.toBytes("identifier");
    private static final byte[] PROPERTIES_AMOUNT = Bytes.toBytes("amount");     
    
    private static final byte[] FAMILY_EMPLOYEE = Bytes.toBytes("employee"); 
    private static final byte[] EMPLOYEE_NAME = Bytes.toBytes("employee_name"); 
    private static final byte[] EMPLOYEE_EMAIL = Bytes.toBytes("employee_email"); 
    
    @Override
    public Project update(Project object) throws BackendException {
        Project reloaded = read(object.getIdentifier());
       if (reloaded == null) {
           Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.INFO, "Department do not exists {0}", object.getIdentifier());
           return null;
       }
       create(object); 
       reloaded = read(object.getIdentifier());
       return reloaded;
    }

    @Override
    public void create(Project object) throws BackendException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Project object) throws BackendException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Project read(String identifier) throws BackendException {
        
        Get getProject = new Get(Bytes.toBytes(identifier));
        Result result = null;
        try {
             result = getEmployeeTable().get(getProject);
        } catch (IOException ex) {
            Logger.getLogger(HBaseEmployeeBackend.class.getName()).log(Level.SEVERE, "wow problem", ex);
        }
        
                
        byte[] nameRecovered = result.getValue(FAMILY_PROPERTIES, PROPERTIES_NAME);
        byte[] amountRecovered = result.getValue(FAMILY_PROPERTIES, PROPERTIES_AMOUNT);
        
        ArrayList<Employee> projectEmployees = new ArrayList<Employee>();
        NavigableMap<byte[], byte[]> employeesMap = result.getFamilyMap(FAMILY_EMPLOYEE);
        
        if ( employeesMap != null )
        {
            for ( Entry eachEmployee :  employeesMap.entrySet() ) {
                
                String empEmail = new String ( (byte[]) eachEmployee.getKey() );
                String empName = new String ( (byte[]) eachEmployee.getValue() );
                
                Employee projectEmployee = new Employee(empEmail,empName);
                projectEmployees.add(projectEmployee);
            }
        }
        
        
        Project projectRecovered = new Project( identifier ,new String(nameRecovered), Bytes.toDouble(amountRecovered), projectEmployees);
        
        
        return projectRecovered;
        
    }

    @Override
    public List<Project> list() throws BackendException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
