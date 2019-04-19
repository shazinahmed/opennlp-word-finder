package com.demo.rep.common;

public enum SpecialCharacters {

	OPENING_PARANTHESES("("),
	CLOSING_PARANTHESES(")"),
	COMMA(","),
	SPACE(" "),
	BACKSLASH("\\");
	
	private String value;
	
	private SpecialCharacters(String pValue)
	{
		this.value = pValue;
	}
	
	public String toString()
	{
		return value;
	}
	
}
