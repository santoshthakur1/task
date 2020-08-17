package com.task.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
public class InterviewAssignmentEntity {

	@Id  
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String name;

	@Column
	private String description;

	private Date created_at;

	@Column
	private String _id;

	@Column
	private String[] tags;

	@Column
	private String status;
}
