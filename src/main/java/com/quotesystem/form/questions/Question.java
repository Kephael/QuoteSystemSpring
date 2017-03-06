package com.quotesystem.form.questions;


/*
 *@param <E>  user submission type
 *@param <V> is server-side submission evaluation format
 */
public interface Question<E, V> {
	public boolean isRequired();

	/*
	 * @return QuoteEntry prompt for user
	 */
	public String getPrompt();

	public void setPrompt(String prompt);

	/*
	 * @return Value stored in server memory once submitted to server
	 */
	public V getValue();

	public void setValue(V value);

	public double getValueWeight();

	public void setValueWeight(double valueWeight);

	public <E> E getResponse();

	public void setResponse(E response);

	/*
	 * implementation defines how response is priced
	 * @return pricing of QuoteEntry
	 */
	public double getCost();
	
	public QuestionType getType();
	
	public void setType(QuestionType type);
	
	public void setRequired(boolean required);
	
}
