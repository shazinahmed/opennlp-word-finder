package com.demo.rep.xml.parser;

import java.util.List;

public interface XmlListToObjectListParser<T> {

	List<T> getObjects(String path);
}
