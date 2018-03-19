package com.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="case_relation",indexes={@Index(name="user_sim_case_idx",columnList="userName,caseNumber,similarCase",unique=true)})
public class CaseRelation {

	public enum UserRating{
		Unrated("Unrated"),
		Helpful("Helpful"),
		NotHelpful("NotHelpful"),
		Irrelevant("Irrelevant");
		
		private String name;
		private UserRating(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}
	}
	
	@GeneratedValue
	@Id
	private long Id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="searchId")
	private Searches search;
	
	private Timestamp addTime = new Timestamp(System.currentTimeMillis());
	
	@Column(length=100)
	private String userName;
	
	@Column(length=20)
	private String caseNumber;

	@Column(length=20)
	private String similarCase;
	
	@Enumerated(EnumType.STRING)
	private UserRating helpful = UserRating.Unrated;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Searches getSearch() {
		return search;
	}

	public void setSearch(Searches search) {
		this.search = search;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getSimilarCase() {
		return similarCase;
	}

	public void setSimilarCase(String similarCase) {
		this.similarCase = similarCase;
	}

	public UserRating getHelpful() {
		return helpful;
	}

	public void setHelpful(UserRating helpful) {
		this.helpful = helpful;
	}
	
	
}
