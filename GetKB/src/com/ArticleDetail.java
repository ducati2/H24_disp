package com;

public class ArticleDetail {

	private String articleName;

	private String subject;

	private String symptom;

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public ArticleDetail() {
		super();
	}

	@Override
	public String toString() {
		return "ArticleDetail [articleName=" + articleName + ", subject="
				+ subject + ", symptom=" + symptom + "]";
	}
	
	

}
