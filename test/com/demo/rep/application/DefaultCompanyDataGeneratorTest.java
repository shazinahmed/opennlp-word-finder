package com.demo.rep.application;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.demo.rep.csv.parser.DefaultCSVParser;
import com.demo.rep.entity.Company;
import com.demo.rep.entity.NewsArticle;
import com.demo.rep.xml.parser.DefaultXmlListToObjectListParser;

public class DefaultCompanyDataGeneratorTest {
	
	private DefaultCompanyDataGenerator underTest;
	private List<Company> companies;
	private List<NewsArticle> articles;
	private List<String> expectedResult = new ArrayList<String>();
	private static final String CSV_TEST = "sample\\csv_for_test.csv";
	private static final String XML_FOLDER = "sample";

	@Before
	public void setUp() throws Exception {
		underTest = new DefaultCompanyDataGenerator();
		loadTestData();
		companies = new DefaultCSVParser<Company>(Company.class).getObjects(CSV_TEST);
		articles = new DefaultXmlListToObjectListParser<NewsArticle>(NewsArticle.class).getObjects(XML_FOLDER);
	}

	@Test
	public void testGenerateData() {
		List<String> actualResult = underTest.generateData(companies, articles);
		Collections.sort(actualResult);
		assertEquals(expectedResult, actualResult);
	}
	
	private void loadTestData()
	{
		expectedResult.add("Homestake Mining Company");
		expectedResult.add("Rio Tinto Group (Rio Tinto)");
		expectedResult.add("Glencore International AG");
		Collections.sort(expectedResult);
	}

}
