/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Model.Services;

import java.util.ArrayList;
import server.server.Model.Domain.Department;

/**
 *
 * @author Fernando
 */
public interface IDepartmentService {
    /**
     * Find an department
     * @param department
     * @return Department 
     */
    public Department find(Department department); 
    
    /**
     * save a department 
     * @param department
     * @return Department
     */
    public Department save(Department department); 

    /**
     * list all departments 
     * @return ArrayList
     */
    public ArrayList<Department> getAll();  
    
    
    /**
     * Updates an department
     * @param department
     * @return Department
     */
    public Department update(Department department);  
    
    
    /**
     * Deactivates an Department
     * @param departmentId
     * @return Department
     */
    public Department delete(Long departmentId);
    
}
