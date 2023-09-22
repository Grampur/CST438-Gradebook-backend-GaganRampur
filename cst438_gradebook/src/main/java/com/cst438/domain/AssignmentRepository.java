package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends CrudRepository <Assignment, Integer> {

//	@Query(value = "select * from Assignment a where a.course.instructor= :email order by a.id", nativeQuery = true)
	@Query("select a from Assignment a where a.course.instructor= :email order by a.id")
	List<Assignment> findByEmail(@Param("email") String email);
	
	@Query("delete from Assignment a where a.id= :id")
	void deleteByid(@Param("id") int id);
	
	@Query("select a from Assignment a where a.id= :id")
	Assignment findByid(@Param("id") int id);
	
//	@Query("select a from Assignment a where a.id= :id")
//	Assignment findByid(@Param("id") int id);
}
