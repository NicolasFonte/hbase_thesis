package com.eti.backend.hbase;

import br.com.caelum.vraptor.ioc.Component;
import com.eti.backend.DepartmentBackend;
import com.eti.model.Department;
import com.eti.model.Employee;
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
public class HBaseDepartmentBackend extends HBaseBackend implements DepartmentBackend {

    private static final byte[] FAMILY_PROPERTIES = Bytes.toBytes("properties"); 
    private static final byte[] PROPERTIES_NAME = Bytes.toBytes("name"); 
    private static final byte[] PROPERTIES_IDENTIFIER = Bytes.toBytes("identifier");
    private static final byte[] PROPERTIES_QUANTITY_EMPLOYEES = Bytes.toBytes("quantity_employees");

    private static final byte[] FAMILY_EMPLOYEE = Bytes.toBytes("employee"); 
    private static final byte[] EMPLOYEE_NAME = Bytes.toBytes("employee_name"); 
    private static final byte[] EMPLOYEE_EMAIL = Bytes.toBytes("employee_identifier"); 
    
    
    
    public HBaseDepartmentBackend() {
    }

    
    
    @Override
    public Department update(Department object) throws BackendException {
        Department reloaded = read(object.getIdentifier());
       if (reloaded == null) {
           Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.INFO, "Department do not exists {0}", object.getIdentifier());
           return null;
       }
       create(object); 
       reloaded = read(object.getIdentifier());
       return reloaded;
    }

    @Override
    public void create(Department object) throws BackendException {
        
        String identifier = object.getIdentifier();
        String name = object.getName();
        
        Put departmentPut = new Put(Bytes.toBytes(identifier));
        departmentPut.add(FAMILY_PROPERTIES, PROPERTIES_NAME , Bytes.toBytes(name));
        departmentPut.add(FAMILY_PROPERTIES, PROPERTIES_IDENTIFIER , Bytes.toBytes(object.getIdentifier()));
        // departmentPut.add(FAMILY_PROPERTIES, PROPERTIES_COMPANY , Bytes.toBytes(object.getCompanyName()));
        departmentPut.add(FAMILY_PROPERTIES, PROPERTIES_QUANTITY_EMPLOYEES , Bytes.toBytes(object.getEmployees().size()));
        
        for ( Employee each : object.getEmployees() ){
            
            departmentPut.add(FAMILY_EMPLOYEE, EMPLOYEE_EMAIL, Bytes.toBytes(each.getEmail()));
            departmentPut.add(FAMILY_EMPLOYEE, EMPLOYEE_NAME, Bytes.toBytes(each.getName()));
            
        }
        
        try {
            getDepartmentTable().put(departmentPut);
        
        } catch (IOException ex) {
            throw new BackendException("Unable to add department: " + name, ex);
        }
    }

    @Override
    public void remove(Department object) throws BackendException {
        Delete deleteDepartment = new Delete(Bytes.toBytes(object.getIdentifier()));
        try {
            getDepartmentTable().delete(deleteDepartment);
        } catch (IOException ex) {
            Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.SEVERE, "problem deleting deparment: " + object.getIdentifier(), ex);
        }
    }

    @Override
    public Department read(String identifier) throws BackendException {
        Department department = new Department();
        Get getDepartment = new Get(Bytes.toBytes(identifier));
        Result result = null;
        try {
            result = getDepartmentTable().get(getDepartment);
        } catch (IOException ex) {
            Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.SEVERE, "Problem getting department: " + identifier, ex);
        }
        byte[] identifierReloaded = result.getValue(FAMILY_PROPERTIES, PROPERTIES_IDENTIFIER);
        byte[] name = result.getValue(FAMILY_PROPERTIES, PROPERTIES_NAME);
        // byte[] companyName = result.getValue(FAMILY_PROPERTIES, PROPERTIES_COMPANY);
        
        department.setIdentifier(new String(identifierReloaded));
        department.setName(new String(name));
        // department.setCompanyName(new String(companyName));
        
        // TODO retrieve employess
        
        
        return department;
    }

    @Override
    public List<Department> list() throws BackendException {
        List<Department> departments = new ArrayList<Department>();
        
        Scan scan = new Scan();
        scan.addFamily(FAMILY_PROPERTIES);
        //first 25 results
        scan.setFilter(new PageFilter(25));
        ResultScanner scanner = null;
        try {
            scanner = getDepartmentTable().getScanner(scan);
        } catch (IOException ex) {
            Logger.getLogger(HBaseDepartmentBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Result result : scanner) {
            
            Department each = new Department();
            byte[] identifier =  result.getValue(FAMILY_PROPERTIES, Bytes.toBytes("identifier") );
            byte[] name =  result.getValue(FAMILY_PROPERTIES, Bytes.toBytes("name") );
            // byte[] companyName =  result.getValue(FAMILY_PROPERTIES, Bytes.toBytes("company") );
             
            
            each.setIdentifier(new String(identifier));
            each.setName(new String(name));
            // each.setCompanyName(new String(companyName));
            
            departments.add(each);
            
        }
        scanner.close();
        return departments;
    }
    
}
