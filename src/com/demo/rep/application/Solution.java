package com.demo.rep.application;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import com.demo.rep.data.provider.CompanyListProvider;
import com.demo.rep.data.provider.DefaultCompanyListProvider;
import com.demo.rep.data.provider.DefaultNewsArticlesProvider;
import com.demo.rep.data.provider.NewsArticlesProvider;
import com.demo.rep.entity.Company;
import com.demo.rep.entity.NewsArticle;

public class Solution {

	private static final Logger LOGGER = Logger.getLogger(Solution.class.getName());

	public static void main(String[] args) throws JAXBException, IllegalStateException, FileNotFoundException {
		//createTrainedModel();

		String path = "D:\\personal\\job\\RepRisk";
		List<Company> companies = getCompanies(path);
		List<NewsArticle> articles = getNewsArticles(path);
		printCompanyData(companies, articles);
	}
	
	private static List<Company> getCompanies(String path)
	{
		Instant start = Instant.now();
		LOGGER.info("Starting company data extraction from CSV");
		
		CompanyListProvider companyListProvider = new DefaultCompanyListProvider();
		List<Company> companies = companyListProvider.getCompanies(path);
		
		Instant end = Instant.now();
		LOGGER.info(new StringBuilder("Company data extraction from CSV completed in ").append(Duration.between(start, end).getSeconds()).append(" seconds").toString());
		return companies;
	}
	
	private static List<NewsArticle> getNewsArticles(String path)
	{
		Instant start = Instant.now();
		LOGGER.info("Starting News article extraction from XML");
		
		NewsArticlesProvider newsArticlesProvider = new DefaultNewsArticlesProvider();
		List<NewsArticle> articles = newsArticlesProvider.getNewsArticles(path);
		
		Instant end = Instant.now();
		LOGGER.info(new StringBuilder("News Article extraction completed in ").append(Duration.between(start, end).getSeconds()).append(" seconds").toString());
		return articles;
	}
	
	private static void printCompanyData(List<Company> companies, List<NewsArticle> articles)
	{
		Instant start = Instant.now();
		LOGGER.info("Calculating number of references");
		
		CompanyDataGenerator companyDataGenerator = new DefaultCompanyDataGenerator();
		List<String> companiesMentioned = companyDataGenerator.generateData(companies, articles);
		companiesMentioned.stream().forEach(company -> System.out.println(new StringBuilder(company).append(System.lineSeparator())));
		
		Instant end = Instant.now();
		LOGGER.info(new StringBuilder("Reference calculation completed in ").append(Duration.between(start, end).getSeconds()).append(" seconds").toString());
	}

	private static void createTrainedModel() {
		CompanyDataTrainer companyDataTrainer = new DefaultCompanyDataTrainer();
		companyDataTrainer.trainModel();
	}
}
