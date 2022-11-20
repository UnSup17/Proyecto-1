/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Model.Services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import server.server.Model.Access.DAOFaculty;
import server.server.Model.Domain.Faculty;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anmon
 */
@Service 
@EnableTransactionManagement
public class FacultyService implements IFacultyService {
    
    @Autowired 
    private DAOFaculty facultyRepo; 

    @Override
    @Transactional(value="DataTransactionManager", readOnly=true)
    public Faculty find(Faculty faculty) {        
        return facultyRepo.findById(faculty.getFacultyId()).orElse(null); 
    }

    @Override 
    @Transactional(value="DataTransactionManager")
    public Faculty save(Faculty faculty){
        Faculty entitySaved = facultyRepo.save(faculty);  
        return entitySaved;  
    }

    @Override
    @Transactional(value="DataTransactionManager", readOnly=true)
    public ArrayList<Faculty> getAll() {
        return (ArrayList<Faculty>) facultyRepo.findAll();  
    }

    @Override
    @Transactional(value="DataTransactionManager")
    public Faculty update(Faculty faculty) {
        Faculty old = this.find(faculty);  
        if (old == null){
            return null;  
        }    
        else{   
            return facultyRepo.save(faculty);  
        }
    }

    @Override
    @Transactional(value="DataTransactionManager")
    public Faculty delete(Long FacultyId) {
        
        Faculty old = facultyRepo.findById(FacultyId).orElse(null);  
        if (old == null){
            return null;  
        }    
        else{   
            facultyRepo.delete(old);
            return old; 
        }
    }
}
