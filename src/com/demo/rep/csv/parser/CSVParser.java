package com.demo.rep.csv.parser;

import java.util.List;

public interface CSVParser<T> {

	List<T> getObjects(String path);
}
