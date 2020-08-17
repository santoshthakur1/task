package com.task.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.entity.InterviewAssignmentRequest;
import com.task.entity.InterviewAssignmentResponse;
import com.task.entity.PaginatedResponse;
import com.task.service.InterviewAssignmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/tasks")
@AllArgsConstructor
public class InterviewAssignmentController {

	@Autowired
	InterviewAssignmentService service;

	@PostMapping
	public ResponseEntity<List<InterviewAssignmentResponse>> createTask(
			@Valid @RequestBody List<InterviewAssignmentRequest> request) {
		List<InterviewAssignmentResponse> response = service.createTask(request);
		return new ResponseEntity<>(response, HttpStatus.MULTI_STATUS);
	}

	@GetMapping("/")
	public ResponseEntity<PaginatedResponse> getAllTask() {
		PaginatedResponse response = service.getAllTask();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<PaginatedResponse> getTask(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "page_size", defaultValue = "50") Integer page_size,
			@RequestParam(value = "sort_by", defaultValue = "id") String sort_by,
			@RequestParam(value = "sort_mode", defaultValue = "ASC") String sort_mode,
			@RequestParam("query") String query) {
		PaginatedResponse response = service.getTask(page, page_size, sort_by, sort_mode, query);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
