package com.demo.rep.data.provider;

import java.util.List;
import java.util.logging.Logger;

import com.demo.rep.csv.parser.CSVParser;
import com.demo.rep.csv.parser.DefaultCSVParser;
import com.demo.rep.data.extractor.CompanyAliasGenerator;
import com.demo.rep.data.extractor.DefaultCompanyAliasGenerator;
import com.demo.rep.entity.Company;

public class DefaultCompanyDataProvider implements CompanyDataProvider {

	private static final String CSV_NAME = "\\160408_company_list.csv";
	private static final Logger LOGGER = Logger.getLogger(DefaultCompanyDataProvider.class.getName());
	
	@Override
	public List<Company> getCompanies(String path)
	{
		CSVParser<Company> csvParser = new DefaultCSVParser<Company>(Company.class);
		List<Company> companies = csvParser.getObjects(new StringBuilder(path).append(CSV_NAME).toString());
		//LOGGER.info("Starting Dictionary generation");
		//addCompanyAlias(companies);
		//LOGGER.info("Dictionary generation complete");
		return companies;
	}
	
	private void addCompanyAlias(List<Company> companies)
	{
		CompanyAliasGenerator aliasGenerator = new DefaultCompanyAliasGenerator();
		companies.stream().forEach(u -> u.addAliases(aliasGenerator.getAliases(u.getName())));
	}

}
