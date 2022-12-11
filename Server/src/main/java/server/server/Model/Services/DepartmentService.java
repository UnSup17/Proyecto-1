/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Model.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import server.server.Model.Access.DAODepartment;
import server.server.Model.Domain.Department;
import server.server.utilities.Labels;
import server.server.utilities.errors.DeptErrors;
import server.server.utilities.errors.FacErrors;

/**
 *
 * @author Fernando
 */
@Service
@EnableTransactionManagement
public class DepartmentService implements IDepartmentService {

    @Autowired
    private DAODepartment deptRepo;

    @Autowired
    private IFacultyService facService;

    @Override
    @Transactional(value = "DataTransactionManager", readOnly = true)
    public Department find(Department department) {
        return deptRepo.findById(department.getDepartmentId()).orElse(null);
    }

    @Override
    @Transactional(value = "DataTransactionManager")
    public Map<Labels, Object> save(Department department) {
        Map<Labels, Object> returns = new HashMap();
        ArrayList errors = new ArrayList();
        if (this.find(department) != null) {
            errors.add(DeptErrors.DEPT102.name());
            returns.put(Labels.objectReturn, null);
        } else {
            if (facService.find(department.getFacultad()) != null) {
                Department entitySaved = deptRepo.save(department);
                returns.put(Labels.objectReturn, entitySaved);
            } else {
                errors.add(FacErrors.FAC101.name());
            }

        }
        returns.put(Labels.errors, errors);
        return returns;
    }

    @Override
    @Transactional(value = "DataTransactionManager", readOnly = true)
    public ArrayList<Department> getAll() {
        return (ArrayList<Department>) deptRepo.findAll();
    }

    @Override
    @Transactional(value = "DataTransactionManager")
    public Map<Labels, Object> update(Department department) {
        Map<Labels, Object> returns = new HashMap();
        ArrayList<String> errors = new ArrayList();
        Department old = this.find(department);
        if (old == null) {
            errors.add(DeptErrors.DEPT101.name());
        } else {
            if (facService.find(department.getFacultad()) != null) {
                Department entitySaved = deptRepo.save(department);
                old = deptRepo.save(department);

            } else {
                errors.add(FacErrors.FAC101.name());
            }

        }
        returns.put(Labels.errors, errors);
        returns.put(Labels.objectReturn, old);
        return returns;
    }

    @Override
    @Transactional(value = "DataTransactionManager")
    public Map<Labels, Object> delete(Long DepartmentId) {
        Map<Labels, Object> returns = new HashMap();
        ArrayList<String> errors = new ArrayList();
        Department old = deptRepo.findById(DepartmentId).orElse(null);
        if (old != null) {
            deptRepo.delete(old);
        } else {
            errors.add(DeptErrors.DEPT101.name());
        }
        returns.put(Labels.errors, errors);
        returns.put(Labels.objectReturn, old);
        return returns;
    }

    @Override
    public Department findById(long department) {
        return deptRepo.findById(department).orElse(null);
    }
}
