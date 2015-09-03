package com.eti.backend.hbase;


import com.eti.backend.CompanyBackend;
import com.eti.model.Company;
import com.eti.model.Department;
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
public class HBaseCompanyBackend extends HBaseBackend implements CompanyBackend {

    
    private static final byte[] FAMILY_PROPERTIES = Bytes.toBytes("properties");
    private static final byte[] FAMILY_DEPARTMENTS = Bytes.toBytes("departments");
    private static final byte[] PROPERTIES_CNPJ = Bytes.toBytes("cnpj"); 
    private static final byte[] PROPERTIES_NAME = Bytes.toBytes("name"); 
    private static final byte[] PROPERTIES_ADDRESS = Bytes.toBytes("address"); 
    private static final byte[] PROPERTIES_CITY = Bytes.toBytes("city"); 
    private static final byte[] PROPERTIES_QUANTITY_DEPARTMENTS = Bytes.toBytes("quantity_departments");
    
    
    public HBaseCompanyBackend() {
    
        
    }
    
    
    @Override
    public Company update(Company object) throws BackendException {
        
       Company reloaded = read(object.getCnpj());
       if (reloaded == null) {
           Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.INFO, "Company do not exists {0}", object.getCnpj());
           return null;
       }
       create(object); 
       reloaded = read(object.getCnpj());
       return reloaded;
    }

    @Override
    public void create(Company object) throws BackendException {
        
        String address = object.getAddress();
        String city = object.getCity();
        String cnpj = object.getCnpj();
        String name = object.getName();
        
        Put companyPut = new Put(Bytes.toBytes(cnpj));
        companyPut.add(FAMILY_PROPERTIES, PROPERTIES_CNPJ , Bytes.toBytes(cnpj));
        companyPut.add(FAMILY_PROPERTIES, PROPERTIES_NAME , Bytes.toBytes(name));
        companyPut.add(FAMILY_PROPERTIES, PROPERTIES_CITY , Bytes.toBytes(city));
        companyPut.add(FAMILY_PROPERTIES, PROPERTIES_ADDRESS , Bytes.toBytes(address));
        companyPut.add(FAMILY_PROPERTIES, PROPERTIES_QUANTITY_DEPARTMENTS , Bytes.toBytes(object.getDepartments().size()));
        
        
        for ( Department each : object.getDepartments()) {
            
            companyPut.add(FAMILY_DEPARTMENTS, Bytes.toBytes(each.getIdentifier()), Bytes.toBytes(each.getName()));            
        } 
        
        try {
            getCompanyTable().put(companyPut);
        
        
        } catch (IOException ex) {
            throw new BackendException("Unable to add company: " + name, ex);
        }
    }

    @Override
    public void remove(Company object) throws BackendException {
        
        Delete deleteCompany = new Delete(Bytes.toBytes(object.getCnpj()));
        try {
            getCompanyTable().delete(deleteCompany);
        } catch (IOException ex) {
            Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.SEVERE, "problem deleting company: " + object.getCnpj(), ex);
        }
        
    }
            

    @Override
    public Company read(String identifier) throws BackendException {
        
        Company company = new Company();
        Get getCompany = new Get(Bytes.toBytes(identifier));
        Result result = null;
        try {
            result = getCompanyTable().get(getCompany);
        } catch (IOException ex) {
            Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.SEVERE, "Problem getting company: " + identifier, ex);
        }
        byte[] cnpj = result.getValue(FAMILY_PROPERTIES, PROPERTIES_CNPJ);
        byte[] name = result.getValue(FAMILY_PROPERTIES, PROPERTIES_NAME);
        byte[] address = result.getValue(FAMILY_PROPERTIES, PROPERTIES_ADDRESS);
        byte[] city = result.getValue(FAMILY_PROPERTIES, PROPERTIES_CITY);
        
        company.setAddress(new String(address));
        company.setName(new String(name));
        company.setCnpj(new String(cnpj));
        company.setCity(new String(city));
        
        return company;
        
        
        
    }

    @Override
    public List<Company> list() throws BackendException {
        
        List<Company> companies = new ArrayList<Company>();
        
        Scan scan = new Scan();
        scan.addFamily(FAMILY_PROPERTIES);
        //first 25 results
        scan.setFilter(new PageFilter(25));
        ResultScanner scanner = null;
        try {
            scanner = getCompanyTable().getScanner(scan);
        } catch (IOException ex) {
            Logger.getLogger(HBaseCompanyBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Result result : scanner) {
            
            Company each = new Company();
            byte[] cnpj =  result.getValue(FAMILY_PROPERTIES, Bytes.toBytes("cnpj") );
            byte[] name =  result.getValue(FAMILY_PROPERTIES, Bytes.toBytes("name") );
            byte[] address =  result.getValue(FAMILY_PROPERTIES, Bytes.toBytes("address") );
            byte[] city =  result.getValue(FAMILY_PROPERTIES, Bytes.toBytes("city") );
            
            each.setCnpj(new String(cnpj));
            each.setName(new String(name));
            each.setAddress(new String(address));
            each.setCity(new String(city));
            
            companies.add(each);
            
        }
        scanner.close();
        return companies;
    }

    
}
