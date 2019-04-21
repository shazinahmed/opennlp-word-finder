package com.demo.rep.application;

import java.util.List;

import com.demo.rep.entity.Company;
import com.demo.rep.entity.NewsArticle;

public interface CompanyDataGenerator {
	
	List<String> generateData(List<Company> companies, List<NewsArticle> articles);

}
