/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Controller;

import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.server.Controller.Utilities.Utility;
import server.server.Model.Domain.AcademicPeriod;
import server.server.Model.Services.IAcademicPeriodService;

/**
 *
 * @author Fernando
 */
@RestController
@RequestMapping("/academicperiod")
public class AcademicPeriodController {
    @Autowired
    public IAcademicPeriodService deptService;

    @GetMapping(value = "/all")
    public ArrayList<AcademicPeriod> all() {
        return deptService.getAll();
    }

    @GetMapping(value = "/{academicPeriodId}")
    @ResponseBody
    public AcademicPeriod get(@PathVariable Long academicPeriodId) {
        AcademicPeriod academicPeriod = new AcademicPeriod();
        academicPeriod.setAcademicPeriodID(academicPeriodId);
        return deptService.find(academicPeriod);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademicPeriod> add(@RequestBody @Valid AcademicPeriod academicPeriod, Errors errors) {
        if (errors.hasErrors()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Errors", Utility.setErrors(errors).toString());
            return new ResponseEntity<>(academicPeriod, headers, HttpStatus.NOT_MODIFIED);
        }

        academicPeriod = deptService.save(academicPeriod);
        if (academicPeriod != null) {
            return new ResponseEntity<>(academicPeriod, null, HttpStatus.ACCEPTED);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error", "found an instance");
            return new ResponseEntity<>(academicPeriod, headers, HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping
    @RequestMapping("/update")
    public ResponseEntity<AcademicPeriod> update(@RequestBody @Valid AcademicPeriod academicPeriod, Errors errors) {
        
        if (errors.hasErrors()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Errors", Utility.setErrors(errors).toString());
            return new ResponseEntity<>(academicPeriod, headers, HttpStatus.NOT_MODIFIED);
        }
        
        academicPeriod = deptService.update(academicPeriod);
        if (academicPeriod != null) {
            return new ResponseEntity<>(academicPeriod, null, HttpStatus.ACCEPTED);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error", "Not found");
            return new ResponseEntity<>(academicPeriod, headers, HttpStatus.NOT_MODIFIED);
        }
        
    }

    @DeleteMapping
    @RequestMapping("/delete/{academicPeriodId}")
    public ResponseEntity<AcademicPeriod> delete(@PathVariable Long academicPeriodId) {

        AcademicPeriod academicPeriod = deptService.delete(academicPeriodId);
        if (academicPeriod != null) {
            return new ResponseEntity<>(academicPeriod, null, HttpStatus.ACCEPTED);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error", "Not found");
            return (new ResponseEntity<>(academicPeriod, headers, HttpStatus.NOT_MODIFIED));
        }
    }
}
