package com.cst438.domain;

public record AssignmentDTO(int id, String assignmentName, String dueDate, String courseTitle, int courseId) {

	public int id() {
		return id;
	}

	public String assignmentName() {
		return assignmentName;
	}

	public String dueDate() {
		return dueDate;
	}

	public String courseTitle() {
		return courseTitle;
	}

	public int courseId() {
		return courseId;
	}
	
	
	
	
}

