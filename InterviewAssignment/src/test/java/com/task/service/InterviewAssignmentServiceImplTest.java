package com.task.service;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.task.constants.Status;
import com.task.controller.InterviewAssignmentController;
import com.task.entity.InterviewAssignmentRequest;
import com.task.entity.InterviewAssignmentResponse;
import com.task.entity.Result;
import com.task.repository.InterviewAssignmentRepository;

public class InterviewAssignmentServiceImplTest {

	@InjectMocks
	private InterviewAssignmentServiceImpl service;

	@Mock
	private InterviewAssignmentRepository repository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createTask_success() throws Exception {
		List<InterviewAssignmentRequest> request = new ArrayList<InterviewAssignmentRequest>();
		List<InterviewAssignmentResponse> responseList = new ArrayList<InterviewAssignmentResponse>();
		InterviewAssignmentResponse response = new InterviewAssignmentResponse();
		response.setId("1");
		response.setStatus(Status.done.toString());
		List<Result> ResultList = new ArrayList<Result>();
		Result result = new Result();
		result.setDescription("good");
		result.setCreated_at(Date.valueOf("2020-11-11"));
		result.setId(12);
		result.setName("santosh");
		result.setStatus(Status.done.toString());
		ResultList.add(result);
		response.setResult(result);
		responseList.add(response);
		when(repository.save(Mockito.any())).thenReturn(responseList);
		List<InterviewAssignmentResponse> i =service.createTask(request);
	}
}
