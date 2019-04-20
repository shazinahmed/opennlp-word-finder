package com.demo.rep.xml.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.demo.rep.entity.NewsArticle;

public class DefaultXmlListToObjectListParserTest {
	
	private DefaultXmlListToObjectListParser<NewsArticle> underTest;
	private static final String path ="sample";

	@Before
	public void setUp() throws Exception {
		underTest = new DefaultXmlListToObjectListParser<NewsArticle>(NewsArticle.class);
	}

	@Test
	public void testGetObjects() {
		assertEquals(2, underTest.getObjects(path).size());
	}

}
