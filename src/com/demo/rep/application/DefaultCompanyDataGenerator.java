package com.demo.rep.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import com.demo.rep.entity.Company;
import com.demo.rep.entity.NewsArticle;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class DefaultCompanyDataGenerator implements CompanyDataGenerator {

	private static final String TOKENIZER_MODEL = "en-token.bin";
	private static final String CUSTOM_TRAINED_COMPANY_MODEL = "en-ner-company.bin";

	@Override
	public List<String> generateData(List<Company> companies, List<NewsArticle> articles) {

		List<String> companiesMentioned = new ArrayList<String>();
		try (InputStream inputStreamTokenizer = new FileInputStream(TOKENIZER_MODEL);
				InputStream inputStreamCompany = new FileInputStream(CUSTOM_TRAINED_COMPANY_MODEL);) {

			Set<String> probableCompaniesAlreadyCheckedList = new HashSet<String>();

			Trie<String, String> companyTrie = getCompanyTrie(companies);
			TokenizerME tokenizer = getTokenizer(inputStreamTokenizer);
			NameFinderME companyNameFinder = getCompanyNameFinder(inputStreamCompany);

			for (NewsArticle article : articles) {
				String[] tokens = tokenizer.tokenize(article.getText());
				Span[] probableCompanyNameIndexSpans = companyNameFinder.find(tokens);
				for (Span probableCompanyNameIndexSpan : probableCompanyNameIndexSpans) {
					StringBuilder builder = new StringBuilder();
					for (int i = probableCompanyNameIndexSpan.getStart(); i < probableCompanyNameIndexSpan
							.getEnd(); i++) {
						builder.append(tokens[i]);
						builder.append(" ");
					}
					String probableCompanyName = builder.toString();
					if (!probableCompaniesAlreadyCheckedList.contains(probableCompanyName)) {
						SortedMap<String, String> probableNamesFromTheTrie = companyTrie.prefixMap(probableCompanyName);
						if (probableNamesFromTheTrie.size() > 0) {
							//This is to prevent duplicate checks
							probableCompaniesAlreadyCheckedList.add(probableCompanyName);
							//TODO: We are picking up the first match, which will not be true in many cases.
							companiesMentioned.add(probableNamesFromTheTrie.firstKey());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return companiesMentioned;
	}

	private TokenizerME getTokenizer(InputStream inputStream) throws IOException {
		TokenizerModel tokenizerModel = new TokenizerModel(inputStream);
		return new TokenizerME(tokenizerModel);

	}

	private NameFinderME getCompanyNameFinder(InputStream inputStream) throws IOException {
		TokenNameFinderModel companyFinderModel = new TokenNameFinderModel(inputStream);
		return new NameFinderME(companyFinderModel);

	}

	private Trie<String, String> getCompanyTrie(List<Company> companies) {
		Trie<String, String> companyTrie = new PatriciaTrie<>();
		companies.stream().forEach(company -> companyTrie.put(company.getName(), null));
		return companyTrie;
	}
}
