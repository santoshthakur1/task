package com.task.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedResponse {

	public PaginatedResponse() {
	}

	public PaginatedResponse(Integer page, Integer page_size, Integer total_elements, List<Result> tasks) {
		super();
		this.page = page;
		this.page_size = page_size;
		this.total_elements = total_elements;
		this.tasks = tasks;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer page;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer page_size;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer total_pages;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer total_elements;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<Result> tasks;
}
