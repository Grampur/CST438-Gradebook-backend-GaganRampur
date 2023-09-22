package com.cst438.domain;																																									

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository <Course, Integer> {
	
	//add method that takes in courseTitle and returns a course
	@Query("select c from Course c where c.title = :title")
	Course findBytitle(@Param("title") String title);
	
	
}
