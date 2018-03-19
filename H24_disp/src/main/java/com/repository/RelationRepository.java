package com.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.entity.CaseRelation;



public interface RelationRepository extends CrudRepository<CaseRelation, Long> {

	//1
	@Query(nativeQuery = true, value="select count(*) from case_relation where helpful = 'helpful' AND add_time between ?1 AND ?2")
	public Long helpfulCountByConditions(String StartDate, String EndDate);
	
}

