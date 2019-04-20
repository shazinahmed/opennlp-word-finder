package com.demo.rep.entity;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
}
