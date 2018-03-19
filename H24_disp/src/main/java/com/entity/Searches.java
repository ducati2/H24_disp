package com.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/*
From the documentation :
      Type | Maximum length
-----------+-------------------------------------
   VARCHAR |  1 ~ 65,535 bytes
  TINYTEXT |           255 (2 8−1) bytes
      TEXT |        65,535 (216−1) bytes = 64 KiB
MEDIUMTEXT |    16,777,215 (224−1) bytes = 16 MiB
  LONGTEXT | 4,294,967,295 (232−1) bytes =  4 GiB
Note that the number of characters that can be stored in your column will depend on the character encoding.
 */
@Entity(name="searches")
public class Searches {

	@GeneratedValue
	@Id
	private long searchId;
	
	@OneToMany(mappedBy="search")
	private List<CaseRelation> caseRelations = new ArrayList<CaseRelation>();
	
	private Timestamp searchTime = new Timestamp(System.currentTimeMillis());
	
	@Column(length=100)
	private String userName;
	
	@Column(length=20)
	private String caseNumber;
	
	private int edited = 0;
	
	private int resultCount = 0;
	
	@Column(length=2000)
	private String resultCaseNumbers;

	@Column(length=500)
	private String subject;

	@Column(length=5500)
	private String description;
	
	@Column(length=255)
	private String product;
	
	@Column(length=255)
	private String version;
	
	@Column(length=255)
	private String component;
	
	@Column(length=255)
	private String source;
	
	@Column(length=5500)
	private String queryJson;
		
	public String getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(String queryJson) {
		this.queryJson = queryJson;
	}

	public Timestamp getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(Timestamp searchTime) {
		this.searchTime = searchTime;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public int getEdited() {
		return edited;
	}

	public void setEdited(int edited) {
		this.edited = edited;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public String getResultCaseNumbers() {
		return resultCaseNumbers;
	}

	public void setResultCaseNumbers(String resultCaseNumbers) {
		this.resultCaseNumbers = resultCaseNumbers;
	}

	public long getSearchId() {
		return searchId;
	}

	public void setSearchId(long searchId) {
		this.searchId = searchId;
	}

	public List<CaseRelation> getCaseRelations() {
		return caseRelations;
	}

	public void setCaseRelations(List<CaseRelation> caseRelations) {
		this.caseRelations = caseRelations;
	}
	
}
