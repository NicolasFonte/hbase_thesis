package com.eti.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.eti.backend.hbase.HBaseDepartmentBackend;
import com.eti.backend.hbase.HBaseEmployeeBackend;
import com.eti.model.Department;
import com.eti.util.BackendException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 *
 * @author nicolas
 */
@Resource
@Path("/department")
public class DepartmentController {
    
    private HBaseDepartmentBackend departmentBackend;
    private HBaseEmployeeBackend employeeBackend;
    private final Result result;
    private Validator validator;
    
    public DepartmentController(HBaseDepartmentBackend departmentBackend, Result result, Validator validator) {
        this.departmentBackend = departmentBackend;
        this.result = result;
        this.validator = validator;
        // this.departmentBackend = new HBaseDepartmentBackend();
    }
    
    public List<Department> departments()
    {
        List<Department> departments = null;
        try {
            departments = departmentBackend.list();
        } catch (BackendException ex) {
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, "Exception: " + getClass().getCanonicalName(), ex);
        }
        result.include("departments", departments);
        return departments;
    }
    
        //this variable product should matches with the jsp element-name.
    public void added(final Department department)  
    {
       
        
        
//        validator.checking(new Validations() { {
//            that(!employee.getName().isEmpty(), "employee.nome", "nome.vazio");
//            that(employee.getEmail().isEmpty(), "employee.email", "email.vazio");
//        } });
//        validator.onErrorUsePageOf(EmployeeController.class).form();
//        
        
        try {
            departmentBackend.create(department);
        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "problem on creation", ex);
        }
        //                                                          - use forward on get method.
        //redirect to the method listProducts,after show the result - use on methods post.
        result.redirectTo(DepartmentController.class).departments();
        
        //result.of redirect to the page,without passing by the method
    }
    
    public void remove(String identifier)  
    {
        
        try {
            Department d = departmentBackend.read(identifier);
            departmentBackend.remove(d);
        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "problem when remove employee " + identifier, ex);
        }
        result.redirectTo(EmployeeController.class).employees();
        
    }
    
    
    public Department updatePage(String identifier)  
    {
        Department department = null;
        List<Department> departments = null;
        
        try {
            department = departmentBackend.read(identifier);
            // departmentBackend.update(department);
            departments = departmentBackend.list();

        } catch (BackendException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, "problem when update", ex);
        }
        // result.include("employee", department);
        result.include("departments", departments);
        
        return department;
    }
    
    
    public void update(Department department)  
    {
        
        
        try {
            department = departmentBackend.update(department);            
            } catch (BackendException ex) {
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, "problem when update", ex);
        }
        
        result.redirectTo(this).departments();
    }

    
    
    public void form()
    {
        
    }

}
