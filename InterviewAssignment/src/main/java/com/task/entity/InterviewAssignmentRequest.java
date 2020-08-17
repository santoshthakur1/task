package com.task.entity;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.common.constants.IAConstants;

import lombok.Data;

@Data
public class InterviewAssignmentRequest {

	@Size(min = 1, max = 20, message = "Length of name should be between 1 to 20")
	private String name;

	@Size(max = 200, message = "Length of name should be less than 200")
    private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IAConstants.DATE_FORMAT)
    private String created_at;

    private Integer id;

    private String _id;

    @Size(min = 1, max = 20, message = "Length of tags should be between 1 to 20")
    private String[] tags;

    private String status;
    
}
