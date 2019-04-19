package com.demo.rep.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import javax.xml.bind.JAXBException;

import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class Solution {

	public static void main(String[] args) throws JAXBException, IllegalStateException, FileNotFoundException {
		long startTime = System.nanoTime();
		//createTrainedModel();
		
		String path = "D:\\personal\\job\\RepRisk";
		CompanyDataGenerator companyDataGenerator = new DefaultCompanyDataGenerator(path);
		companyDataGenerator.generateData();
		long endTime = System.nanoTime();
		System.out.println((endTime - startTime) / 1_000_000_000.0);
	}
	
	private static void createTrainedModel()
	{
		try {
			createModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void createModel() throws IOException {
		String path = "D:\\personal\\job\\RepRisk\\training_1.TXT";
		InputStreamFactory inputStreamFactory = null;
		inputStreamFactory = new MarkableFileInputStreamFactory(new File(path));
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, charset);
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

		TokenNameFinderModel tokenNameFinderModel = NameFinderME.train("en", "company", sampleStream, TrainingParameters.defaultParams(),
				TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		
		File output = new File("en-ner-company-c.bin");
		FileOutputStream fileOutputStream = new FileOutputStream(output);
		tokenNameFinderModel.serialize(fileOutputStream);
	}

}
