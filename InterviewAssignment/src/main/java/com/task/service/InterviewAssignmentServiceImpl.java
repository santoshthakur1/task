package com.task.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.common.constants.IAConstants;
import com.task.common.constants.Status;
import com.task.common.exception.InvalidFIlter;
import com.task.common.exception.SearchNotSupported;
import com.task.common.exception.SortingNotSupported;
import com.task.common.exception.StatusNotValid;
import com.task.entity.ErrorMessage;
import com.task.entity.InterviewAssignmentEntity;
import com.task.entity.InterviewAssignmentRequest;
import com.task.entity.InterviewAssignmentResponse;
import com.task.entity.PaginatedResponse;
import com.task.entity.Result;
import com.task.repository.InterviewAssignmentRepository;

@Service
public class InterviewAssignmentServiceImpl implements InterviewAssignmentService {

	@Autowired
	private InterviewAssignmentRepository repository;

	private final List<String> validSortBy = List.of("id", "name", "description", "status", "created_at");

	@Override
	public List<InterviewAssignmentResponse> createTask(List<InterviewAssignmentRequest> assetRequest) {
		List<InterviewAssignmentEntity> entites = convertRequestToEntity(assetRequest);
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<CompletableFuture<InterviewAssignmentEntity>> futureEntitesRes = new ArrayList<>();
		entites.forEach(i -> {
			CompletableFuture<InterviewAssignmentEntity> completableFuture = CompletableFuture
					.supplyAsync(() -> repository.save(i), executorService);
			futureEntitesRes.add(completableFuture);
		});

		return convertEntitesToResponse(futureEntitesRes);
	}

	@Override
	public PaginatedResponse getAllTask() {
		List<InterviewAssignmentEntity> entites = repository.findAll();
		ObjectMapper mapper = new ObjectMapper();
		List<Result> results = new ArrayList<>();
		entites.forEach(entity -> {
			Result result = mapper.convertValue(entity, Result.class);
			results.add(result);
		});
		return buildPaginatedResponse(results, 1, 50);
	}

	@Override
	public PaginatedResponse getTask(Integer page, Integer page_size, String sort_by, String sort_mode, String query) {
		List<InterviewAssignmentEntity> entites = new ArrayList<InterviewAssignmentEntity>();
		PaginatedResponse paginatedResponse = new PaginatedResponse(0, 50, 0, new ArrayList<>());

		if (!query.contains(":")) {
			throw new InvalidFIlter(IAConstants.INVALID_FILTER);
		}
		String name = query.substring(0, query.indexOf(':'));
		String value = query.substring(query.indexOf(':') + 1, query.length());

		if (!validSortBy.contains(sort_by)) {
			new SortingNotSupported(IAConstants.SORTING_NOT_SUPPORTED + sort_by);
		}
		if ("name".equalsIgnoreCase(name)) {
			entites = repository.findByNameLike("%" + value + "%");
		} else if ("description".equalsIgnoreCase(name)) {
			entites = repository.findByDescriptionLike("%" + value + "%");
		} else if ("status".equalsIgnoreCase(name)) {
			entites = repository.findByStatus(value);
		} else if ("created_after".equalsIgnoreCase(name)) {
			Date date = Date.valueOf(value);
			entites = repository.findByCreatedAt(date);
		} else {
			new SearchNotSupported(IAConstants.SEARCHING_NOT_SUPPORTED + query);
		}
		if (entites != null && !entites.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			List<Result> results = new ArrayList<>();
			sortList(entites, sort_mode, sort_by);
			entites.forEach(entity -> {
				Result result = mapper.convertValue(entity, Result.class);
				results.add(result);
			});
			paginatedResponse = buildPaginatedResponse(results, page, page_size);
		}
		return paginatedResponse;
	}

	private void sortList(List<InterviewAssignmentEntity> entites, String sort_mode, String sort_by) {

		if (sort_mode.equalsIgnoreCase("DESC")) {
			entites.sort((a, b) -> {
				return sortByParameter(sort_by, b, a);
			});
		} else {
			entites.sort((a, b) -> {
				return sortByParameter(sort_by, a, b);
			});
		}
	}

	private int sortByParameter(String sort_by, InterviewAssignmentEntity a, InterviewAssignmentEntity b) {
		if (sort_by.equalsIgnoreCase("id")) {
			return a.getId().compareTo(b.getId());
		} else if (sort_by.equalsIgnoreCase("name")) {
			return a.getName().compareTo(b.getName());
		} else if (sort_by.equalsIgnoreCase("description")) {
			return a.getDescription().compareTo(b.getDescription());
		} else if (sort_by.equalsIgnoreCase("status")) {
			return a.getStatus().compareTo(b.getStatus());
		} else {
			return a.getCreated_at().compareTo(b.getCreated_at());
		}
	}

	private List<InterviewAssignmentResponse> convertEntitesToResponse(
			List<CompletableFuture<InterviewAssignmentEntity>> futureEntitesRes) {
		ObjectMapper mapper = new ObjectMapper();
		List<InterviewAssignmentResponse> response = new ArrayList<InterviewAssignmentResponse>();

		futureEntitesRes.parallelStream().forEach(futureEntites -> {
			InterviewAssignmentResponse res = new InterviewAssignmentResponse();
			ErrorMessage message = new ErrorMessage();
			try {
				InterviewAssignmentEntity entity = Optional.ofNullable(futureEntites.get()).get();
				Result result = mapper.convertValue(entity, Result.class);
				res.setId(entity.get_id());
				res.setStatus(entity.getStatus());
				res.setResult(result);
			} catch (Exception ex) {
				message.setMessage(ex.getMessage());
				res.setErrorMessage(message);
				ex.printStackTrace();
			}
			response.add(res);
		});
		return response;
	}

	private List<InterviewAssignmentEntity> convertRequestToEntity(List<InterviewAssignmentRequest> assetRequest) {
		List<InterviewAssignmentEntity> entites = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		assetRequest.parallelStream().forEach(request -> {
			InterviewAssignmentEntity entity = mapper.convertValue(request, InterviewAssignmentEntity.class);
			Optional.ofNullable(entity.getStatus()).ifPresentOrElse(status -> entity.setStatus(compareStatus(status)),
					() -> entity.setStatus(Status.open.toString()));
			entites.add(entity);
		});
		return entites;
	}

	private String compareStatus(String status) {
		if (!EnumUtils.isValidEnum(Status.class, status)) {
			throw new StatusNotValid(IAConstants.STATUS_TYPE_NOT_VALID + status);
		}
		return status;
	}

	private PaginatedResponse buildPaginatedResponse(List<Result> results, Integer currentPage, Integer pageSize) {
		PaginatedResponse paginatedResponse = new PaginatedResponse();
		Integer totalRecords = results.size();
		Integer totalPages = totalRecords % pageSize == 0 ? totalRecords / pageSize : (totalRecords / pageSize) + 1;
		paginatedResponse.setTasks(results);
		paginatedResponse.setPage(currentPage);
		paginatedResponse.setPage_size(pageSize);
		paginatedResponse.setTotal_elements(totalRecords);
		paginatedResponse.setTotal_pages(totalPages);
		return paginatedResponse;
	}
}
