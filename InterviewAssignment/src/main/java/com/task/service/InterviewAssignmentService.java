package com.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.task.entity.InterviewAssignmentRequest;
import com.task.entity.InterviewAssignmentResponse;
import com.task.entity.PaginatedResponse;

@Service
public interface InterviewAssignmentService {

	public List<InterviewAssignmentResponse> createTask(List<InterviewAssignmentRequest> assetRequest);
	
	public PaginatedResponse getAllTask();
	
	public PaginatedResponse getTask(Integer page, Integer page_size, String sort_by, String sort_mode, String query);
}
