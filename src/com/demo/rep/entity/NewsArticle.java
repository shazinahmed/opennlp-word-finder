package com.demo.rep.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.demo.rep.common.SpecialCharacters;
import com.demo.rep.xml.adapter.LocalDateAdapter;

@XmlRootElement(name = "news-item")
public class NewsArticle {

	@XmlAttribute
	private String id;

	@XmlElement
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	private LocalDate date;

	@XmlElement
	private String title;

	@XmlElement
	private String source;

	@XmlElement
	private String author;

	@XmlElement
	private String text;

	private Map<String, Integer> companyReferences = new HashMap<String, Integer>();

	public String getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getSource() {
		return source;
	}

	public String getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}

	public Map<String, Integer> getCompanyReferences() {
		return companyReferences;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getTitle());
		// TODO: better to move the string to some common file
		builder.append(System.getProperty("line.separator"));
		builder.append("Companies mentioned: ");
		companyReferences.forEach((name, count) -> builder.append(name)
				.append(SpecialCharacters.OPENING_PARANTHESES.toString()).append(count)
				.append(SpecialCharacters.CLOSING_PARANTHESES).append(SpecialCharacters.COMMA.toString()));
		builder.append(System.getProperty("line.separator"));
		return builder.toString();
	}
}
