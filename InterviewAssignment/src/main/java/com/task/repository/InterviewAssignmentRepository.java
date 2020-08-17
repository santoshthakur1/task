package com.task.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.task.entity.InterviewAssignmentEntity;

@Repository
public interface InterviewAssignmentRepository extends JpaRepository<InterviewAssignmentEntity, Long> {

	public List<InterviewAssignmentEntity> findByNameLike(String value);

	public List<InterviewAssignmentEntity> findByDescriptionLike(String value);

	public List<InterviewAssignmentEntity> findByStatus(String value);

	@Query(value = "select *from task where created_at>=:date", nativeQuery = true)
	public List<InterviewAssignmentEntity> findByCreatedAt(@Param("date") Date date);

}
