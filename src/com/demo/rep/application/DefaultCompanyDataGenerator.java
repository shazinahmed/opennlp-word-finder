package com.demo.rep.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.demo.rep.data.provider.CompanyDataProvider;
import com.demo.rep.data.provider.DefaultCompanyDataProvider;
import com.demo.rep.data.provider.DefaultNewsArticlesProvider;
import com.demo.rep.data.provider.NewsArticlesProvider;
import com.demo.rep.entity.Company;
import com.demo.rep.entity.NewsArticle;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class DefaultCompanyDataGenerator implements CompanyDataGenerator {

	private CompanyDataProvider companyDataProvider;
	private NewsArticlesProvider newsArticlesProvider;
	private String path;
	
	private static final Logger LOGGER = Logger.getLogger(DefaultCompanyDataGenerator.class.getName());

	public DefaultCompanyDataGenerator(String pPath) {
		companyDataProvider = new DefaultCompanyDataProvider();
		newsArticlesProvider = new DefaultNewsArticlesProvider();
		this.path = pPath;
	}

	@Override
	public void generateData() {
		LOGGER.info("Starting in company data extraction from CSV");
		List<Company> companies = companyDataProvider.getCompanies(path);
		LOGGER.info("Company data extraction from CSV completed");
		
		LOGGER.info("Starting in News article extraction from XML");
		List<NewsArticle> articles = newsArticlesProvider.getNewsArticles(path);
		LOGGER.info("News Article extraction completed");
		
		LOGGER.info("Calculating number of references");
		generateD(companies, articles);
		//generateData(companies, articles);
		LOGGER.info("Reference calculation completed");
		
		
		//articles.stream().filter(article -> article.getCompanyReferences().size() > 0).forEach(article -> System.out.println(article));
	}
	
	private void generateD(List<Company> companies, List<NewsArticle> articles)
	{
		try {
			List<String> companiesReferred = new ArrayList();
			Set<String> companiesFound = new HashSet<String>();
			List<String> companyNames = getCompanyList(companies);
			
			InputStream inputStreamTokenizer = new FileInputStream("en-token.bin");
			InputStream inputStreamOrganization = new FileInputStream("en-ner-company-c.bin");
			
			TokenizerModel tokenizerModel = new TokenizerModel(inputStreamTokenizer);
			TokenizerME tokenizer = new TokenizerME(tokenizerModel);
			
			TokenNameFinderModel finderModel = new TokenNameFinderModel(inputStreamOrganization);
			NameFinderME nameFinder = new NameFinderME(finderModel);
			
			for (NewsArticle article: articles)
			{
				String[] tokens = tokenizer.tokenize(article.getText());
				Span[] organizations = nameFinder.find(tokens);
				for (Span span: organizations)
				{
					StringBuilder builder = new StringBuilder();
					for (int i=span.getStart();i<span.getEnd(); i++)
					{
						builder.append(tokens[i]);
						if (i != span.getEnd())
						{
							builder.append(" ");
						}
					}
					String organizationName = builder.toString();
					if (!companiesFound.contains(organizationName))
					{
						for (String companyName: companyNames)
						{
							if (companyName.startsWith(organizationName))
							{
								companiesFound.add(organizationName);
								//System.out.println(organizationName);
								//System.out.println(companyName);
								companiesReferred.add(companyName);
								break;
							}
						}
					}
				}
			}
			companiesReferred.stream().forEach(companyName -> System.out.println(companyName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getCompanyList(List<Company> companies)
	{
		List<String> companyNames = new ArrayList<String>();
		for (Company company: companies)
		{
			companyNames.add(company.getName());
		}
		//Collections.sort(companyNames);
		return companyNames;
	}

	private void generateData(List<Company> companies, List<NewsArticle> articles) {
		articles.parallelStream().forEach(article -> companies.stream().forEach(company -> updateReferenceCount(company, article)));
//		Integer count = 0;
//		for (NewsArticle article: articles)
//		{
//			for (Company company: companies)
//			{
//				count = getCompanyReferenceCount(company, article);
//				if (count > 0)
//				{
//					article.getCompanyReferences().put(article.getTitle(), count);
//				}
//			}
//		}
	}
	
	private void updateReferenceCount(Company company, NewsArticle article)
	{
		Integer count = getCompanyReferenceCount(company, article);
		if (count > 0)
		{
			article.getCompanyReferences().put(company.getName(), count);
		}
	}

	private Integer getCompanyReferenceCount(Company company, NewsArticle article) {
		Integer count = 0;
		StringBuilder builder;
		for (String alias : company.getAliases()) {
			builder = new StringBuilder(" ").append(alias).append(" ");
			count += StringUtils.countMatches(article.getText(), builder.toString());
		}
		return count;
	}
}
