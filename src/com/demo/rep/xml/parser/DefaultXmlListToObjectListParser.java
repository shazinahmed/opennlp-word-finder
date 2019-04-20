package com.demo.rep.xml.parser;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class DefaultXmlListToObjectListParser<T> implements XmlListToObjectListParser<T> {

	private XmlToObjectParser<T> xmlToObjectParser;

	private static final String XML_EXTENSION = ".xml";

	public DefaultXmlListToObjectListParser(Class<T> pObjectClassType) {
		xmlToObjectParser = new DefaultXmlToObjectParser<T>(pObjectClassType);
	}

	@Override
	public List<T> getObjects(String path) {
		T t;
		List<T> objects = new ArrayList<T>();
		File[] files = getFileList(path);
		for (File file : files) {
			t = xmlToObjectParser.getObject(file);
			if (t != null) {
				objects.add(t);
			}
		}
		return objects;
	}

	private File[] getFileList(String path) {
		File dir = new File(path);
		FileFilter filter = (file) -> file.isFile() && file.getName().endsWith(XML_EXTENSION);
		return dir.listFiles(filter);
	}

}
