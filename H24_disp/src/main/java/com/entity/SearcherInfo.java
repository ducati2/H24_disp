package com.entity;

public class SearcherInfo {
	String name;
	Long count;
	
	public SearcherInfo(String name, Long count) {
		super();
		this.name = name;
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}
