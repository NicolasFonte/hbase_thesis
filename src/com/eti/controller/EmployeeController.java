package com.eti.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.eti.backend.hbase.HBaseDepartmentBackend;
import com.eti.backend.hbase.HBaseEmployeeBackend;
import com.eti.model.Department;
import com.eti.model.Employee;
import com.eti.util.BackendException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicolas
 */
@Resource
@Path("/employee")
public class EmployeeController {
    
    private HBaseEmployeeBackend backend;
    private HBaseDepartmentBackend departmentBackend;
    private final Result result;
    // private Validator validator;
    
    public EmployeeController(HBaseEmployeeBackend backend, Result result, Validator validator) {
        this.backend = backend;
        this.result = result;
        // this.validator = validator;
        this.departmentBackend = new HBaseDepartmentBackend();
    }
    
    public List<Employee> employees()
    {
        List<Employee> employees = new ArrayList<Employee>();
        List<Department> departments =  new ArrayList<Department>();;
        try {
            employees = backend.list();
            departments = departmentBackend.list();
            
        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "Exception: " + getClass().getCanonicalName(), ex);
        }
        result.include("employees", employees);
        result.include("departments", departments);
        return employees;
    }
    
        //this variable product should matches with the jsp element-name.
    public void added(final Employee employee)  
    {
        if (employee.getDepartmentName() == null ){
            employee.setDepartmentName("");
        }
        
        
//        validator.checking(new Validations() { {
//            that(!employee.getName().isEmpty(), "employee.nome", "nome.vazio");
//            that(employee.getEmail().isEmpty(), "employee.email", "email.vazio");
//        } });
//        validator.onErrorUsePageOf(EmployeeController.class).form();
//        
        
        try {
            backend.create(employee);
        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "problem on creation", ex);
        }
        //                                                          - use forward on get method.
        //redirect to the method listProducts,after show the result - use on methods post.
        result.redirectTo(EmployeeController.class).employees();
        
        //result.of redirect to the page,without passing by the method
    }
    
    public void remove(String email)  
    {
        
        try {
            Employee e = backend.read(email);
            backend.remove(e);
        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "problem when remove employee " + email, ex);
        }
        result.redirectTo(EmployeeController.class).employees();
        
    }
    
    
    public Employee updatePage(String email)  
    {
        Employee employee = null;
        List<Department> departments = null;
        
        try {
            employee = backend.read(email);            
            departments = departmentBackend.list();

        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "problem when update", ex);
        }
        result.include("employee", employee);
        result.include("departments", departments);
        
        return employee;
    }
    
    
    public void update(Employee employee)  
    {
        if (employee.getDepartmentName() == null ){
            employee.setDepartmentName("");
        }
        
        
        try {
            employee = backend.update(employee);            
            } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "problem when update", ex);
        }
        
        result.redirectTo(this).employees();
    }

    
    
    public void form()
    {
        
        List<Department> departments =  new ArrayList<Department>();
        try {            
            departments = departmentBackend.list();            
        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "Exception: " + getClass().getCanonicalName(), ex);
        }
        
        result.include("departments", departments);        
    }
    

    
    
}
