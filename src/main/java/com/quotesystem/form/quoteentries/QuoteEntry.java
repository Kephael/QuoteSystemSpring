package com.quotesystem.form.quoteentries;

import com.quotesystem.form.QuestionType;
/*
 * E is user submission type
 * V is server-side submission evaluation format
 */
public interface QuoteEntry<E, V> {
	public boolean isRequired();

	public String getPrompt();

	public void setPrompt(String prompt);

	public QuestionType getQuestionType();

	public void setQuestionType(QuestionType questionType);

	public V getValue();

	public void setValue(V value);

	public double getValueWeight();

	public void setValueWeight(double valueWeight);
	
	public <E> E getResponse();
	
	public void setResponse(E response);

	/*
	 * implementation defines how response is priced
	 */
	public double getCost();

}
