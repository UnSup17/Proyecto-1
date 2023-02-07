/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Controller;

import java.util.ArrayList;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.server.Controller.Utilities.Utility;
import server.server.Model.Domain.Subject;
import server.server.Model.Services.IProgramService;
import server.server.Model.Services.ISubjectService;
import server.server.Model.Services.Impls.CustomUserDetails;
import server.server.auth.IAuthenticationFacade;
import server.server.utilities.Labels;
import server.server.utilities.errors.UserErrors;

/**
 *
 * @author Fernando
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IProgramService programServ;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all")
    public ArrayList<Subject> all() {
        return subjectService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHEDULEMANAGER')")
    @GetMapping(value = "/allByProgramId")
    public ResponseEntity<ArrayList<Subject>> allByProgramId(@RequestParam("programId") long programId) {
        CustomUserDetails user = (CustomUserDetails) authenticationFacade.getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        ArrayList<String> errors2 = new ArrayList();

        if (!programServ.validateUserProgram(programId, user)) {
            errors2.add(UserErrors.USR105.name());
            headers.add(Labels.errors.toString(), errors2.toString());
            return new ResponseEntity<>(null, headers, HttpStatus.UNAUTHORIZED);
        }

        Map<Labels, Object> returns = subjectService.getAllByProgramId(programId);
        ArrayList<Subject> ret = (ArrayList<Subject>) returns.get(Labels.objectReturn);
        return (new ResponseEntity<>(ret, null, HttpStatus.ACCEPTED));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHEDULEMANAGER')")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> add(@RequestBody @Valid Subject subject, Errors errors) {
        HttpHeaders headers = new HttpHeaders();
        ArrayList<String> errors2 = new ArrayList();
        CustomUserDetails user = (CustomUserDetails) authenticationFacade.getPrincipal();
        if (!programServ.validateUserProgram(subject.getProgram().getProgramId(), user)) {
            errors2.add(UserErrors.USR105.name());
            headers.add(Labels.errors.toString(), errors2.toString());
            return new ResponseEntity<>(null, headers, HttpStatus.UNAUTHORIZED);
        }

        if (errors.hasErrors()) {
            headers.add(Labels.errors.name(), Utility.setErrors(errors).toString());
            return new ResponseEntity<>(subject, headers, HttpStatus.NOT_MODIFIED);
        } else {
            Map<Labels, Object> returns = subjectService.save(subject);
            errors2 = (ArrayList<String>) returns.get(Labels.errors);
            Subject subj = (Subject) returns.get(Labels.objectReturn);
            if (!errors2.isEmpty() || subj == null) {
                headers.add(Labels.errors.name(), errors2.toString());
                return new ResponseEntity<>(subject, headers, HttpStatus.NOT_MODIFIED);
            } else {
                return new ResponseEntity<>(subject, null, HttpStatus.ACCEPTED);

            }
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHEDULEMANAGER')")
    @PutMapping
    @RequestMapping("/update")
    public ResponseEntity<Subject> update(@RequestBody Subject subject, Errors errors) {
        HttpHeaders headers = new HttpHeaders();
        ArrayList<String> errors2 = new ArrayList();
        CustomUserDetails user = (CustomUserDetails) authenticationFacade.getPrincipal();
        if (!subjectService.validateUserSubject(subject.getSubjectID(), user)) {
            errors2.add(UserErrors.USR105.name());
            headers.add(Labels.errors.toString(), errors2.toString());
            return new ResponseEntity<>(null, headers, HttpStatus.UNAUTHORIZED);
        }

        if (errors.hasErrors()) {
            ArrayList<String> setErrors = Utility.setErrors(errors);
            headers.add(Labels.errors.name(), setErrors.toString());
            return new ResponseEntity<>(subject, headers, HttpStatus.NOT_MODIFIED);
        } else {
            Map<Labels, Object> update = subjectService.update(subject);
            errors2 = (ArrayList<String>) update.get(Labels.errors);
            Subject subj = (Subject) update.get(Labels.objectReturn);
            if (!errors2.isEmpty() || subj == null) {
                headers.add(Labels.errors.name(), errors2.toString());
                return new ResponseEntity<>(subject, headers, HttpStatus.NOT_MODIFIED);
            }
            return new ResponseEntity<>(subject, null, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHEDULEMANAGER')")
    @RequestMapping("/delete/{SubjectId}")
    public ResponseEntity<Subject> delete(@PathVariable Long SubjectId
    ) {
        HttpHeaders headers = new HttpHeaders();
        ArrayList<String> errors2 = new ArrayList();
        CustomUserDetails user = (CustomUserDetails) authenticationFacade.getPrincipal();

        if (!subjectService.validateUserSubject(SubjectId, user)) {
            errors2.add(UserErrors.USR105.name());
            headers.add(Labels.errors.toString(), errors2.toString());
            return new ResponseEntity<>(null, headers, HttpStatus.UNAUTHORIZED);
        }

        Map<Labels, Object> delete = subjectService.delete(SubjectId);
        ArrayList<String> errors = (ArrayList<String>) delete.get(Labels.errors);
        Subject subj = (Subject) delete.get(Labels.objectReturn);
        if (subj != null) {
            return new ResponseEntity<>(subj, null, HttpStatus.ACCEPTED);
        } else {
            headers.add(Labels.errors.name(), errors.toString());
            return (new ResponseEntity<>(subj, headers, HttpStatus.NOT_MODIFIED));
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHEDULEMANAGER')")
    @GetMapping(value = "/{SubjectId}")
    @ResponseBody
    public Subject get(@PathVariable Long SubjectId) {
        Subject subject = new Subject();
        subject.setSubjectID(SubjectId);
        return subjectService.find(subject);
    }

}
