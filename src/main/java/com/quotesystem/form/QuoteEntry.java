package com.quotesystem.form;

interface QuoteEntry<E> {
	public boolean isRequired();

	public String getPrompt();

	public void setPrompt(String prompt);

	public QuestionType getQuestionType();

	public void setQuestionType(QuestionType questionType);

	public double getValue();

	public void setValue(double value);

	public double getValueWeight();

	public void setValueWeight(double valueWeight);
	
	public <E> E getResponse();
	
	public void setResponse(E response);

}
