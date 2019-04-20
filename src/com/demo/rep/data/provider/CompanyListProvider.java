package com.demo.rep.data.provider;

import java.util.List;

import com.demo.rep.entity.Company;

public interface CompanyListProvider {

	List<Company> getCompanies(String path);
}
