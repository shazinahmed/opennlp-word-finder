package com.demo.rep.csv.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.demo.rep.entity.Company;

public class DefaultCSVParserTest {

	private DefaultCSVParser<Company> underTest;
	private static final String CSV_TEST = "sample\\csv_for_test.csv";
	
	@Before
	public void setUp() throws Exception {
		underTest = new DefaultCSVParser<Company>(Company.class);
	}

	@Test
	public void testGetObjects() {
		assertEquals(6, underTest.getObjects(CSV_TEST).size());
	}

}
