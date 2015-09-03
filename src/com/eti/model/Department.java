package com.eti.model;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author nicolas
 */
public class Department {

    public Department() {
    }
    
    private String identifier;
    private String name;
    // private String companyName;

    private List<Employee> employees;

    public List<Employee> getEmployees() {
        if (employees == null){
            employees = new ArrayList<Employee>();
        }
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }
    /**
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    */ 
}
