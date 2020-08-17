package com.task.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	private String name;

    private String description;

    private Date created_at;

    private Integer id;

    private String[] tags;

    private String status;
}
