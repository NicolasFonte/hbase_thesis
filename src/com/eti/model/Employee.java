package com.eti.model;

import br.com.caelum.vraptor.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicolas
 */
@Resource
public class Employee {

    private String name;
    private String email;
    private String phoneNumber;
    private String departmentName;
    // private String companyName;
    private List<Project> belongProjects;
    
    public Employee() {
    
    }

    public Employee(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public List<Project> getBelongProjects() {
        if (belongProjects == null){
            belongProjects = new ArrayList<Project>();
        }
        return belongProjects;
    }

    public void setBelongProjects(List<Project> belongProjects) {
        this.belongProjects = belongProjects;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /*
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
     * 
     */

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
}
