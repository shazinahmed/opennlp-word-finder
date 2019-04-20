package com.demo.rep.entity;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

public class Company {
	
	@CsvBindByName(column="RepRisk Company ID")
	private Long id;
	
	@CsvBindByName(column="Company Name")
	private String name;
	
	private List<String> aliases = new ArrayList<String>();;

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

	public List<String> getAliases() {
		return aliases;
	}

	public void addAlias(String alias)
	{
		aliases.add(alias);
	}
	
	public void addAliases(List<String> pAliases)
	{
		aliases.addAll(pAliases);
	}
}
