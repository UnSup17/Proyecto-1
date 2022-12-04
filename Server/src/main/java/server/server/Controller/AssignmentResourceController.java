/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Controller;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.server.Model.Domain.AssignmentResource;
import server.server.Model.Services.IAssignmentResourceService;
import server.server.utilities.Labels;

/**
 *
 * @author anmon
 */
@RestController
@RequestMapping("/AssignmentResource")
public class AssignmentResourceController {

    @Autowired
    private IAssignmentResourceService asigResService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/assign")
    public ResponseEntity<AssignmentResource> AssignmentResourceMethod(@RequestParam("facultyId") long facultyId,
            @RequestParam("environmentId") long environmentId,
            @RequestParam("resourceId") long resourceId) {
        Map<Labels, Object> returns = asigResService.save(facultyId, environmentId, resourceId);
        ArrayList<String> errors = (ArrayList<String>) returns.get(Labels.errors);
        AssignmentResource fr = (AssignmentResource) returns.get(Labels.objectReturn);

        if (!errors.isEmpty() || fr == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(Labels.errors.name(), errors.toString());
            return (new ResponseEntity<>(null, headers, HttpStatus.NOT_MODIFIED));
        }

        return (new ResponseEntity<>(fr, null, HttpStatus.ACCEPTED));
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/unassign")
    public ResponseEntity<AssignmentResource> UnassignmentResourceMethod(@RequestParam("facultyId") long facultyId,
            @RequestParam("environmentId") long environmentId,
            @RequestParam("resourceId") long resourceId) {
        Map<Labels, Object> returns = asigResService.detach(facultyId, environmentId, resourceId);
        ArrayList<String> errors = (ArrayList<String>) returns.get(Labels.errors);
        AssignmentResource fr = (AssignmentResource) returns.get(Labels.objectReturn);

        if (!errors.isEmpty() || fr == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(Labels.errors.name(), errors.toString());
            return (new ResponseEntity<>(null, headers, HttpStatus.NOT_MODIFIED));
        }

        return (new ResponseEntity<>(fr, null, HttpStatus.ACCEPTED));
    }

}
