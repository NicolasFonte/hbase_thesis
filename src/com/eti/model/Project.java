package com.eti.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicolas
 */
public class Project {
    
    private String identifier;
    private String name;
    private double amount;
    private List<Employee> employees;

    public Project() {
    }
    
    public Project(String identifier, String name, double amount) {
        this.identifier = identifier;
        this.name = name;
        this.amount = amount;
        
    }

    
    public Project(String identifier, String name, double amount, List<Employee> employees) {
        this.identifier = identifier;
        this.name = name;
        this.amount = amount;
        this.employees = employees;
    }
    
    
    public List<Employee> getEmployees() {
        if (employees == null){
            employees = new ArrayList<Employee>();
        }
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public void setName(String name) {
        this.name = name;
    }
    
    
}
