package com.demo.rep.xml.parser;

import java.io.File;

import javax.xml.bind.JAXBException;

public interface XmlToObjectParser<T> {

	/**
	 * 
	 * @param file
	 * @return
	 * @throws JAXBException
	 */
	T getObject(File file);
}
