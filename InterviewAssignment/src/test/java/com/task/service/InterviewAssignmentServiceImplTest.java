package com.task.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import com.task.common.constants.Status;
import com.task.entity.InterviewAssignmentEntity;
import com.task.entity.InterviewAssignmentRequest;
import com.task.entity.InterviewAssignmentResponse;
import com.task.entity.PaginatedResponse;
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
	public void createTask_success() {
		List<InterviewAssignmentRequest> request = createRequest();
		InterviewAssignmentEntity response = createFirstEntity();
		Mockito.when(repository.save(Mockito.any())).thenReturn(response);
		List<InterviewAssignmentResponse> res = service.createTask(request);
		Assertions.assertEquals(res.size(), 1);
	}

	@Test
	public void getAllTask_success() {
		InterviewAssignmentEntity response = createFirstEntity();
		List<InterviewAssignmentEntity> entityList = new ArrayList<InterviewAssignmentEntity>();
		entityList.add(response);
		Mockito.when(repository.findAll()).thenReturn(entityList);
		PaginatedResponse paginatedResponse = service.getAllTask();
		Assertions.assertNotNull(paginatedResponse.getPage());
		Assertions.assertNotNull(paginatedResponse.getPage_size());
		Assertions.assertNotNull(paginatedResponse.getTotal_elements());
		Assertions.assertNotNull(paginatedResponse.getTotal_pages());
		Assertions.assertNotNull(paginatedResponse.getTasks());
		Assertions.assertEquals(paginatedResponse.getTasks().size(), 1);
	}

	@ParameterizedTest
	@MethodSource("createTaskArguments")
	public void getTask_success(Integer page, Integer page_size, String sort_by, String sort_mode, String query) {
		InterviewAssignmentEntity firstResponse = createFirstEntity();
		InterviewAssignmentEntity secondResponse = createSecondEntity();
		List<InterviewAssignmentEntity> entityList = new ArrayList<InterviewAssignmentEntity>();
		entityList.add(firstResponse);
		entityList.add(secondResponse);
		Mockito.when(repository.findByNameLike(Mockito.anyString())).thenReturn(entityList);
		Mockito.when(repository.findByDescriptionLike(Mockito.anyString())).thenReturn(entityList);
		Mockito.when(repository.findByStatus(Mockito.anyString())).thenReturn(entityList);
		Mockito.when(repository.findByCreatedAt(Mockito.any(Date.class))).thenReturn(entityList);
		PaginatedResponse paginatedResponse = service.getTask(page, page_size, sort_by, sort_mode, query);
		Assertions.assertNotNull(paginatedResponse.getPage());
		Assertions.assertNotNull(paginatedResponse.getPage_size());
		Assertions.assertNotNull(paginatedResponse.getTotal_elements());
		Assertions.assertNotNull(paginatedResponse.getTotal_pages());
		Assertions.assertNotNull(paginatedResponse.getTasks());
		Assertions.assertEquals(paginatedResponse.getTasks().size(), 2);
	}

	static Stream<Arguments> createTaskArguments() {
		return Stream.of(arguments(1, 22, "name", "DESC", "name:santosh"),
				arguments(1, 22, "id", "DESC", "description:good"),
				arguments(1, 22, "description", "DESC", "status:open"),
				arguments(1, 22, "status", "ASC", "created_after:2020-10-11"));
	}

	private InterviewAssignmentEntity createFirstEntity() {
		InterviewAssignmentEntity response = new InterviewAssignmentEntity();
		response.set_id("320");
		response.setStatus(Status.done.toString());
		response.setDescription("good");
		response.setCreated_at(Date.valueOf("2020-11-11"));
		response.setId(12);
		response.setName("santosh");
		String[] tags = { "GL" };
		response.setTags(tags);
		return response;
	}

	private InterviewAssignmentEntity createSecondEntity() {
		InterviewAssignmentEntity response = new InterviewAssignmentEntity();
		response.set_id("333");
		response.setStatus(Status.open.toString());
		response.setDescription("bad");
		response.setCreated_at(Date.valueOf("2020-10-21"));
		response.setId(23);
		response.setName("Thakur");
		String[] tags = { "GlobalLogic" };
		response.setTags(tags);
		return response;
	}

	private List<InterviewAssignmentRequest> createRequest() {
		List<InterviewAssignmentRequest> requestList = new ArrayList<InterviewAssignmentRequest>();
		InterviewAssignmentRequest request = new InterviewAssignmentRequest();
		request.set_id("320");
		request.setCreated_at("2020-11-11");
		request.setDescription("best");
		request.setId(2);
		request.setName("santosh");
		request.setStatus(Status.open.toString());
		String[] tags = { "GL" };
		request.setTags(tags);
		requestList.add(request);
		return requestList;
	}
}
