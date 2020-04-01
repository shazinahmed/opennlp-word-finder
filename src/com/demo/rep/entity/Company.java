package com.demo.rep.entity;

import com.opencsv.bean.CsvBindByName;

public class Company {
	
	@CsvBindByName(column="Company ID")
	private Long id;
	
	@CsvBindByName(column="Company Name")
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
