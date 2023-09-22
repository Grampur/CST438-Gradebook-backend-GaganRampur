package com.cst438.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;

@RestController
@CrossOrigin 
public class AssignmentController {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@GetMapping("/assignment")
	public AssignmentDTO[] getAllAssignmentsForInstructor() {
		// get all assignments for this instructor
		String instructorEmail = "cchou@csumb.edu";  // user name (should be instructor's email) 
		List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);
		AssignmentDTO[] result = new AssignmentDTO[assignments.size()];
		for (int i=0; i<assignments.size(); i++) {
			Assignment as = assignments.get(i);
			AssignmentDTO dto = new AssignmentDTO(
					as.getId(), 
					as.getName(), 
					as.getDueDate().toString(), 
					as.getCourse().getTitle(), 
					as.getCourse().getCourse_id());
			result[i]=dto;
		}
		return result;
	}
	
	// TODO create CRUD methods for Assignment
	@PostMapping("/assignmentCreate")
	public AssignmentDTO create(@RequestBody AssignmentDTO assignmentDTO) {
		
		System.out.println(assignmentDTO);
		
		Course course = courseRepository.findBytitle(assignmentDTO.courseTitle());
		Date date = Date.valueOf(assignmentDTO.dueDate());
		
		
		
		Assignment assignment = new Assignment();
		assignment.setId(assignmentDTO.id());
		assignment.setName(assignmentDTO.assignmentName());
		assignment.setDueDate(date);
		assignment.setCourse(course);
		
		
		System.out.println(assignment);
		assignmentRepository.save(assignment);
		
		return assignmentDTO;
		
	}
	
	//
	@PostMapping("/assignmentDelete")
	public void delete(@RequestBody Integer assignmentID, @RequestParam("force") Optional<String> force) throws Exception {
		
		
        // Check if the assignment with the given ID exists
        Assignment assignment = assignmentRepository.findById(assignmentID).orElse(null);
        if (assignment == null) {
            throw new Exception ("Assignment not found");
        }
        
        // Check if there are associated grades
        if (assignmentRepository.findByid(assignmentID) == null) {
            // If the "force" parameter is provided and equals "true," delete regardless of grades
            if (force != null && force.equals("true")) {
                assignmentRepository.deleteById(assignmentID);
                return;
            } else {
                throw new Exception ("Assignment has grades");
            }
        } else {
            // No associated grades, delete the assignment
            assignmentRepository.deleteById(assignmentID);
        }
				
		
	}
	
	
	@PostMapping("/assignmentUpdate")
	public void updateAssignment(@RequestBody AssignmentDTO assignmentDTO) throws Exception {
		
            // Check if the assignment with the given ID exists
            Assignment existingAssignment = assignmentRepository.findById(assignmentDTO.id()).orElse(null);
            if (existingAssignment == null) {
            	throw new Exception("Assignment does not exist");
            	

            }

            // Update the assignment fields from the DTO
            existingAssignment.setName(assignmentDTO.assignmentName());
            existingAssignment.setDueDate(Date.valueOf(assignmentDTO.dueDate()));


            // Save the updated assignment
            assignmentRepository.save(existingAssignment);
            
	        
		
	}
	
	@GetMapping("/assignmentGet/{id}")
	public AssignmentDTO getAssignment(@PathVariable("id") int assignmentID) throws Exception {
		
		System.out.println(assignmentID);
		
        Assignment existingAssignment = assignmentRepository.findById(assignmentID).orElse(null);

		if (existingAssignment == null) {
			throw new Exception ("Assignment does not exist");

		}
		
		
		AssignmentDTO assignmentDTO = new AssignmentDTO(existingAssignment.getId(), existingAssignment.getName(), existingAssignment.getDueDate().toString(), 
				existingAssignment.getCourse().getTitle(), existingAssignment.getCourse().getCourse_id());
		return assignmentDTO;
	}
	
	
	
	
	
	
}
