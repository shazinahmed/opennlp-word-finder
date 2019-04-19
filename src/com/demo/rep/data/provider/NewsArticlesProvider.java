package com.demo.rep.data.provider;

import java.util.List;

import com.demo.rep.entity.NewsArticle;

public interface NewsArticlesProvider {

	List<NewsArticle> getNewsArticles(String path);
}
