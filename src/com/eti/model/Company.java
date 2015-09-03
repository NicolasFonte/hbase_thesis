package com.eti.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicolas
 */
public class Company {

    public Company() {
    }
    
    private String cnpj;
    private String name;
    private String address;
    private String city;
    private List<Department> departments;

    public List<Department> getDepartments() {
        if (departments == null){
            departments = new ArrayList<Department>();
        }
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}
