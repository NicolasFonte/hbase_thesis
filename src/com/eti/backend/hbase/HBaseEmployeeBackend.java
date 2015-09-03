package com.eti.backend.hbase;

import br.com.caelum.vraptor.ioc.Component;
import com.eti.backend.EmployeeBackend;
import com.eti.model.Employee;
import com.eti.model.Project;
import com.eti.util.BackendException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * @author nicolas
 */
@Component
public class HBaseEmployeeBackend extends HBaseBackend implements EmployeeBackend {

    static final Logger loggerDefault = Logger.getLogger(HBaseEmployeeBackend.class.getName()); 
    static final org.apache.log4j.Logger logger4j = org.apache.log4j.Logger.getLogger(HBaseEmployeeBackend.class.getName()); 
    
    
    private static final byte[] FAMILY_PROPERTIES = Bytes.toBytes("properties"); 
                                                                   
    // Simple attributtes from the employee
    
    private static final byte[] PROPERTIES_NAME = Bytes.toBytes("name"); 
    private static final byte[] PROPERTIES_EMAIL= Bytes.toBytes("email");
    private static final byte[] PROPERTIES_DEPARTMENT= Bytes.toBytes("department");
    private static final byte[] PROPERTIES_PHONE_NUMBER = Bytes.toBytes("phone_number");
    
    // identifier:attribute -> attribute_value 
    private static final byte[] FAMILY_PROJECTS = Bytes.toBytes("project"); 
    
    private static final String PROJECT_IDENTIFIER = "project_identifier"; 
    private static final String PROJECT_NAME = "project_name"; 
    private static final String PROJECT_AMOUNT = "project_amount"; 
    
    
    public HBaseEmployeeBackend() throws IOException {
        
        

    }

    @Override
    public Employee update(Employee object) throws BackendException {
        Employee reloaded = read(object.getEmail());
       if (reloaded == null) {
           Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.INFO, "Employee do not exists {0}", object.getEmail());
           return null;
       }
       create(object); 
       reloaded = read(object.getEmail());
       return reloaded;
    }

    @Override
    public void create(Employee object) throws BackendException {
        
        String email = object.getEmail();
        String name = object.getName();
        String phoneNumber = object.getPhoneNumber();
        String departmentName = object.getDepartmentName();
        
        Put put = new Put(Bytes.toBytes(email));
        put.add(FAMILY_PROPERTIES, PROPERTIES_EMAIL , Bytes.toBytes(email));
        put.add(FAMILY_PROPERTIES, PROPERTIES_NAME , Bytes.toBytes(name));
        put.add(FAMILY_PROPERTIES, PROPERTIES_PHONE_NUMBER , Bytes.toBytes(phoneNumber));
        put.add(FAMILY_PROPERTIES, PROPERTIES_DEPARTMENT , Bytes.toBytes(departmentName));
        
        
        for (Project eachProject: object.getBelongProjects()){
            String preffix = String.valueOf(eachProject.getIdentifier());
        
            put.add(FAMILY_PROJECTS, buildProjectQualifier(preffix, PROJECT_IDENTIFIER ) , Bytes.toBytes(eachProject.getIdentifier()));
            put.add(FAMILY_PROJECTS, buildProjectQualifier(preffix, PROJECT_NAME ) , Bytes.toBytes(eachProject.getName()));
            put.add(FAMILY_PROJECTS, buildProjectQualifier(preffix, PROJECT_AMOUNT ) , Bytes.toBytes(eachProject.getAmount()));
        }
        
        try {
            getEmployeeTable().put(put);
        } catch (IOException ex) {
            throw new BackendException("Unable to add employee: " + name, ex);
        }
        
    }

    @Override
    public void remove(Employee object) throws BackendException {
        Delete deleteEmployee = new Delete(Bytes.toBytes(object.getEmail()));
        try {
            getEmployeeTable().delete(deleteEmployee);
        } catch (IOException ex) {
            Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.SEVERE, "problem deleting company: " + object.getEmail(), ex);
        }
    }

    @Override
    public List<Employee> list() throws BackendException {
        
        List<Employee> employees = new ArrayList<Employee>();        
        Scan scan = new Scan();
        scan.addFamily(FAMILY_PROPERTIES);
        //first 25 results
        scan.setFilter(new PageFilter(25));
        ResultScanner scanner = null;
        try {
            scanner = getEmployeeTable().getScanner(scan);
        } catch (IOException ex) {
            Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Result result : scanner ) {
            
            Employee each = new Employee();
            byte[] email =  result.getRow();
            byte[] name =  result.getValue(FAMILY_PROPERTIES, PROPERTIES_NAME );
            byte[] phoneNumber =  result.getValue(FAMILY_PROPERTIES, PROPERTIES_PHONE_NUMBER );
            byte[] departmentName =  result.getValue(FAMILY_PROPERTIES, PROPERTIES_DEPARTMENT );            
            each.setEmail(Bytes.toString(email));
            each.setName(Bytes.toString(name));
            each.setDepartmentName(Bytes.toString(departmentName));
            each.setPhoneNumber(Bytes.toString(phoneNumber));
            
            employees.add(each);
            
        }
        scanner.close();
        return employees;
    }

    @Override
    public Employee read(String identifier) throws BackendException {
        
        String email = identifier;
        
        Get getUser = new Get(Bytes.toBytes(email));
        Result result = null;
        try {
             result = getEmployeeTable().get(getUser);
        } catch (IOException ex) {
            Logger.getLogger(HBaseEmployeeBackend.class.getName()).log(Level.SEVERE, "wow problem", ex);
        }
        
        
        byte[] emailRecovered = result.getValue(FAMILY_PROPERTIES, PROPERTIES_EMAIL);
        byte[] nameRecovered = result.getValue(FAMILY_PROPERTIES, PROPERTIES_NAME);
        byte[] phoneNumberRecovered = result.getValue(FAMILY_PROPERTIES, PROPERTIES_PHONE_NUMBER);
        byte[] departmentRecovered = result.getValue(FAMILY_PROPERTIES, PROPERTIES_DEPARTMENT);
        
        Employee employeeRecovered = new Employee(new String(nameRecovered), new String(emailRecovered) );
        employeeRecovered.setPhoneNumber(new String(phoneNumberRecovered));
        employeeRecovered.setDepartmentName(new String(departmentRecovered));
        
        return employeeRecovered;
        
    }

    protected byte[] buildProjectQualifier(String preffix, String domain) {
        
        String qualifier = preffix + ":" + domain;
        return Bytes.toBytes(qualifier);
        
    }
    
}
