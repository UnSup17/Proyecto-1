/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Model.Services.Impls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import server.server.Model.Access.DAOProgram;
import server.server.Model.Domain.Program;
import server.server.Model.Services.IDepartmentService;
import server.server.Model.Services.IProgramService;
import server.server.Model.Services.ILocationService;
import server.server.utilities.Labels;
import server.server.utilities.errors.ProgErrors;
import server.server.utilities.errors.LocationErrors;

/**
 *
 * @author Fernando
 */
@Service
@EnableTransactionManagement
public class ProgramService implements IProgramService {

    @Autowired
    private DAOProgram programRepo;

    @Autowired
    private IDepartmentService deptService;

    @Autowired
    private ILocationService locService;

    @Override
    @Transactional(value = "DataTransactionManager", readOnly = true)
    public Program find(Program program) {
        return programRepo.findById(program.getProgramId()).orElse(null);
    }

    @Override
    @Transactional(value = "DataTransactionManager")
    public Map<Labels, Object> save(Program program) {
        Map<Labels, Object> returns = new HashMap();
        ArrayList errors = new ArrayList();
        if (this.find(program) != null) {
            errors.add(ProgErrors.PRG102.name());
            returns.put(Labels.objectReturn, null);
        } else {
            if (deptService.find(program.getDepartment()) != null) {
                if (programRepo.findByCode(program.getCode()) != null) {
                    errors.add(ProgErrors.PRG108.name());
                } else if (program.getLocation() == null) {
                    errors.add(ProgErrors.PRG105.name());
                } else if (locService.find(program.getLocation().getLocationId()) == null) {
                    errors.add(LocationErrors.LOC101.name());
                } else if (errors.isEmpty()) {
                    program = programRepo.save(program);
                }
            } else {
                errors.add(ProgErrors.PRG101.name());
            }

        }
        returns.put(Labels.errors, errors);
        returns.put(Labels.objectReturn, program);
        return returns;
    }

    @Override
    @Transactional(value = "DataTransactionManager", readOnly = true)
    public ArrayList<Program> getAll() {
        return (ArrayList<Program>) programRepo.findAll();
    }

    @Override
    @Transactional(value = "DataTransactionManager")
    public Map<Labels, Object> update(Program program) {
        Map<Labels, Object> returns = new HashMap();
        ArrayList<String> errors = new ArrayList();
        Program old = this.find(program);
        if (old == null) {
            errors.add(ProgErrors.PRG101.name());
        } else {
            old = programRepo.save(program);
        }
        program.setCode(program.getCode().toUpperCase());
        Program r2 = programRepo.findByCode(program.getCode());
        if (r2 != null && r2.getProgramId() != program.getProgramId()) {
            errors.add(ProgErrors.PRG108.name());
        }
        if (errors.isEmpty()) {
            old = programRepo.save(program);
        }
        returns.put(Labels.errors, errors);
        returns.put(Labels.objectReturn, old);
        return returns;
    }

    @Override
    @Transactional(value = "DataTransactionManager")
    public Map<Labels, Object> delete(Long ProgramId) {
        Map<Labels, Object> returns = new HashMap();
        ArrayList<String> errors = new ArrayList();
        Program old = programRepo.findById(ProgramId).orElse(null);
        if (old != null) {
            programRepo.delete(old);
        } else {
            errors.add(ProgErrors.PRG101.name());
        }
        returns.put(Labels.errors, errors);
        returns.put(Labels.objectReturn, old);
        return returns;
    }

    @Override
    public Program findById(long program) {
        return programRepo.findById(program).orElse(null);
    }

    @Override
    public Program findByCode(String code) {
        return programRepo.findByCode(code);
    }

    @Override
    public Map<Labels, Object> getAll(long departmentId) {
        Map<Labels, Object> returns = new HashMap();
        List<Program> old = programRepo.findAllByDepartmentId(departmentId);
        returns.put(Labels.objectReturn, old);
        return returns;
    }

    @Override
    public boolean validateUserProgram(long programId, CustomUserDetails user) {
        Program findById = this.programRepo.findById(programId).orElse(null);
        boolean ban = false;
        if (findById != null) {
            ban = deptService.validateUserDepartment(findById.getDepartment().getDepartmentId(), user);
        }
        return ban;
    }

}
