package com.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.entity.Searches;



public interface DataRepository extends CrudRepository<Searches, Long> {

	@Query(nativeQuery = true, value="SELECT user_name FROM (select distinct DATE_FORMAT(search_time,'%Y-%m-%d'), result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) t GROUP BY user_name ORDER BY user_name ASC;")
	public List<String> getSearcherNameByConditions(String StartDate, String EndDate);
	
	@Query(nativeQuery = true, value="SELECT COUNT(*) FROM (select distinct DATE_FORMAT(search_time,'%Y-%m-%d'), result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) t GROUP BY user_name ORDER BY user_name ASC;")
	public List<BigInteger> getSearcherCountByConditions(String StartDate, String EndDate);
	
	// 1 By Conditions
	@Query(nativeQuery = true, value="select count(*) from (select distinct DATE_FORMAT(search_time,'%Y-%m-%d'), result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1")
	public Long totalSearchCountByConditions(String StartDate, String EndDate);
	
	// 2 By Conditions
	@Query(nativeQuery = true, value="select count(*) from (select distinct DATE_FORMAT(search_time,'%Y-%m-%d'), result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 where T1.edited = '1'")
	public Long editCountByConditions(String StartDate, String EndDate);
	
	@Query(nativeQuery = true, value="select count(*) from (select distinct DATE_FORMAT(search_time,'%Y-%m-%d'), result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 where T1.edited = '0'")
	public Long unEditCountByConditions(String StartDate, String EndDate);
	
	// 3
	// For Days By Conditions
	@Query(nativeQuery = true, value="select days from (select distinct DATE_FORMAT(search_time,'%Y-%m-%d') as days, result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 group by days")
	public List<String> getDayByConditions(String StartDate, String EndDate);
	
	@Query(nativeQuery = true, value="select count(days) from (select distinct DATE_FORMAT(search_time,'%Y-%m-%d') as days, result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 group by days")
	public List<Long> getDayCountByConditions(String StartDate, String EndDate);
	
	// For Weeks By Conditions
	@Query(nativeQuery = true, value="select weeks from (select distinct DATE_FORMAT(search_time,'%Y-%u') as weeks, result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 group by weeks")
	public List<String> getWeekByConditions(String StartDate, String EndDate);
	
	@Query(nativeQuery = true, value="select count(weeks) from (select distinct DATE_FORMAT(search_time,'%Y-%u') as weeks, result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 group by weeks")
	public List<Long> getWeekCountByConditions(String StartDate, String EndDate);
	
	// For Months By Conditions
	@Query(nativeQuery = true, value="select months from (select distinct DATE_FORMAT(search_time,'%Y-%m') as months, result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 group by months")
	public List<String> getMonthByConditions(String StartDate, String EndDate);
	
	@Query(nativeQuery = true, value="select count(months) from (select distinct DATE_FORMAT(search_time,'%Y-%m') as months, result_case_numbers,edited, subject, product, user_name from searches where search_time between ?1 AND ?2) T1 group by months")
	public List<Long> getMonthCountByConditions(String StartDate, String EndDate);
}
