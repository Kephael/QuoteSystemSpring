package com.quotesystem.form;

interface QuoteEntry {
	public boolean isRequired();

	public String getPrompt();

	public void setPrompt(String prompt);

	public QuestionType getQuestionType();

	public void setQuestionType(QuestionType questionType);

	public double getValue();

	public void setValue(double value);

	public double getValueWeight();

	public void setValueWeight(double valueWeight);

}
