package com.demo.rep.csv.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.opencsv.bean.CsvToBeanBuilder;

public class DefaultCSVParser<T> implements CSVParser<T> {

	private static final Logger LOGGER = Logger.getLogger(DefaultCSVParser.class.getName());
	private static final char SEPERATOR = ';';

	private Class<T> objectClassType;

	public DefaultCSVParser(Class<T> pObjectClassType) {
		this.objectClassType = pObjectClassType;
	}

	@Override
	public List<T> getObjects(String path) {
		List<T> objects = Collections.emptyList();
		try (FileReader fileReader = new FileReader(path)) {
			objects = new CsvToBeanBuilder<T>(fileReader).withType(objectClassType).withSeparator(SEPERATOR).build()
					.parse();
		} catch (IllegalStateException | IOException e) {
			LOGGER.severe(
					"CSV parsing was unsuccessful, either due to incorrect path or incorrectly formatted CSV file. Please check the exception log for more details");
			LOGGER.severe(e.getMessage());
		}
		return objects;
	}

}
