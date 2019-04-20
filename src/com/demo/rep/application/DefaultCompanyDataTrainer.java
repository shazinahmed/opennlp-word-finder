package com.demo.rep.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

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

public class DefaultCompanyDataTrainer implements CompanyDataTrainer {

	//TODO Handle exceptions
	@Override
	public void trainModel() {
		String trainingData = "training.TXT";
		InputStreamFactory inputStreamFactory;
		try {
			inputStreamFactory = new MarkableFileInputStreamFactory(new File(trainingData));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream;
		try {
			lineStream = new PlainTextByLineStream(inputStreamFactory, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

		TokenNameFinderModel tokenNameFinderModel = null;
		try {
			tokenNameFinderModel = NameFinderME.train("en", "company", sampleStream,
					TrainingParameters.defaultParams(),
					TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		File output = new File("en-ner-company.bin");
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			tokenNameFinderModel.serialize(fileOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
