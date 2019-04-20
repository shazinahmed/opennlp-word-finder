package com.demo.rep.data.provider;

import java.util.List;

import com.demo.rep.csv.parser.CSVParser;
import com.demo.rep.csv.parser.DefaultCSVParser;
import com.demo.rep.entity.Company;

public class DefaultCompanyListProvider implements CompanyListProvider {

	private static final String CSV_NAME = "\\160408_company_list.csv";
	
	@Override
	public List<Company> getCompanies(String path)
	{
		CSVParser<Company> csvParser = new DefaultCSVParser<Company>(Company.class);
		List<Company> companies = csvParser.getObjects(new StringBuilder(path).append(CSV_NAME).toString());
		return companies;
	}
}
