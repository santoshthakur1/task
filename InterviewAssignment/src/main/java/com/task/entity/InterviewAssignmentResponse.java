package com.task.entity;

import lombok.Data;

@Data
public class InterviewAssignmentResponse {

	private Result result;

	private ErrorMessage errorMessage;

	private String id;

	private String status;
}
