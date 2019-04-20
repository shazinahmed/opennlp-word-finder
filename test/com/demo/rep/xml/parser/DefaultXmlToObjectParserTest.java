package com.demo.rep.xml.parser;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.demo.rep.entity.NewsArticle;

public class DefaultXmlToObjectParserTest {

	private DefaultXmlToObjectParser<NewsArticle> underTest;
	private File file;
	private static final String XML_FILE = "sample\\xml_for_test.xml";
	private static final String ARTICLE_TEXT = "A number of Chelsea residents at the meeting said they came to express their concern";
	
	@Before
	public void setUp() throws Exception {
		underTest = new DefaultXmlToObjectParser<NewsArticle>(NewsArticle.class);
		file = new File(XML_FILE);
	}

	@Test
	public void testGetObject() {
		assertEquals(ARTICLE_TEXT, underTest.getObject(file).getText());;
	}

}
