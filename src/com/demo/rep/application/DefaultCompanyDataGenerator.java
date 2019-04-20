package com.demo.rep.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	public void generateData(List<Company> companies, List<NewsArticle> articles) {

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
					for (int i = probableCompanyNameIndexSpan.getStart(); i < probableCompanyNameIndexSpan.getEnd(); i++) {
						builder.append(tokens[i]);
						if (i < probableCompanyNameIndexSpan.getEnd() - 1) {
							builder.append(" ");
						}
					}
					String probableCompanyName = builder.toString();
					if (!probableCompaniesAlreadyCheckedList.contains(probableCompanyName)) {
						SortedMap<String, String> probableNamesFromTheTrie = companyTrie.prefixMap(probableCompanyName);
						if (probableNamesFromTheTrie.size() > 0) {
							probableCompaniesAlreadyCheckedList.add(probableCompanyName);
							//TODO Rather than picking the first element, do a better matching
							logData(probableCompanyName, probableNamesFromTheTrie.firstKey());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private void logData(String probableCompanyName, String nameFound) {
		// TODO Do something better than printing in the console
		System.out.println(probableCompanyName);
		System.out.println(nameFound);
		System.out.println(System.getProperty("line.seperator"));
	}
}
