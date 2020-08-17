package com.task.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.task.entity.InterviewAssignmentRequest;
import com.task.entity.InterviewAssignmentResponse;
import com.task.entity.PaginatedResponse;
import com.task.entity.Result;
import com.task.service.InterviewAssignmentService;

public class InterviewAssignmentControllerTest {

	@InjectMocks
	private InterviewAssignmentController controller;

	@Mock
	private InterviewAssignmentService service;

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
		when(service.createTask(Mockito.anyList())).thenReturn(responseList);
		ResponseEntity<List<InterviewAssignmentResponse>> res = controller.createTask(request);
		res.getBody().stream().forEach(i -> {
			assertNotNull(i.getId());
			assertNotNull(i.getStatus());
			assertNotNull(i.getResult());
		});
	}

	@Test
	public void getAllTask_success() throws Exception {
		PaginatedResponse response = new PaginatedResponse();
		response.setPage(1);
		response.setPage_size(50);
		response.setTotal_pages(3);
		response.setTotal_elements(1);
		List<Result> ResultList = new ArrayList<Result>();
		Result result = new Result();
		result.setDescription("good");
		result.setCreated_at(Date.valueOf("2020-11-11"));
		result.setId(12);
		result.setName("santosh");
		result.setStatus(Status.done.toString());
		ResultList.add(result);
		response.setTasks(ResultList);
		when(service.getAllTask()).thenReturn(response);
		ResponseEntity<PaginatedResponse> res = controller.getAllTask();
		assertNotNull(res.getBody().getPage());
		assertNotNull(res.getBody().getPage_size());
		assertNotNull(res.getBody().getTotal_pages());
		assertNotNull(res.getBody().getTotal_elements());
		assertNotNull(res.getBody().getTasks());
	}

	@Test
	public void getTask_success() throws Exception {
		PaginatedResponse response = new PaginatedResponse();
		List<Result> ResultList = new ArrayList<Result>();
		Result result = new Result();
		result.setDescription("good");
		result.setCreated_at(Date.valueOf("2020-11-11"));
		result.setId(12);
		result.setName("santosh");
		result.setStatus(Status.done.toString());
		ResultList.add(result);

		response.setPage(1);
		response.setPage_size(50);
		response.setTotal_pages(3);
		response.setTotal_elements(1);
		response.setTasks(ResultList);
		when(service.getTask(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(response);
		ResponseEntity<PaginatedResponse> res = controller.getTask(1, 23, "id", "desc", "santosh");
		assertNotNull(res.getBody().getPage_size());
		assertNotNull(res.getBody().getTotal_pages());
		assertNotNull(res.getBody().getTotal_elements());
		assertNotNull(res.getBody().getTasks());
	}
}
