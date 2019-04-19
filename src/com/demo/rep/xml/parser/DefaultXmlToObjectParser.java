package com.demo.rep.xml.parser;

import java.io.File;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class DefaultXmlToObjectParser<T> implements XmlToObjectParser<T> {

	private static final Logger LOGGER = Logger.getLogger(DefaultXmlToObjectParser.class.getName());
	private Class<T> objectClassType;

	public DefaultXmlToObjectParser(Class<T> pObjectClassType) {
		this.objectClassType = pObjectClassType;
	}

	@Override
	public T getObject(File file) {
		T t = null;
		try 
		{
			JAXBContext context = JAXBContext.newInstance(objectClassType);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(file);
		} catch (JAXBException e) 
		{
			//Ideally we shouldn't use String concatenation. This a temporary solution to handle errors in XML file.
			LOGGER.warning("Unmarshalling failed for "+file.getName()+" , probably due to incorrectly formatted XML file");
		}
		return t;
	}

}
