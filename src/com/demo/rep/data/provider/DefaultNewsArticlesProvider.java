package com.demo.rep.data.provider;

import java.util.List;
import java.util.logging.Logger;

import com.demo.rep.entity.NewsArticle;
import com.demo.rep.xml.parser.DefaultXmlListToObjectListParser;
import com.demo.rep.xml.parser.XmlListToObjectListParser;

public class DefaultNewsArticlesProvider implements NewsArticlesProvider {

	private static final String XML_FOLDER = "\\data";
	
	@Override
	public List<NewsArticle> getNewsArticles(String path)
	{
		XmlListToObjectListParser<NewsArticle> xmlListToObjectListParser = new DefaultXmlListToObjectListParser<>(NewsArticle.class);
		return xmlListToObjectListParser.getObjects(new StringBuilder(path).append(XML_FOLDER).toString());
	}

}
