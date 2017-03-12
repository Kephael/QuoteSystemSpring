package com.quotesystem.form.questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = RadioQuestion.class), @Type(value = BooleanQuestion.class),
		@Type(value = LongResponseQuestion.class), })
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

	public E getResponse();

	public void setResponse(E response);

	/*
	 * implementation defines how response is priced
	 * @return pricing of QuoteEntry
	 */
	public double getServiceCost();

	public QuestionType getType();

	public void setType(QuestionType type);

	public void setRequired(boolean required);

}
