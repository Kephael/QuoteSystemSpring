package com.quotesystem.form.questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = CheckboxQuestion.class), @Type(value = BooleanQuestion.class),
		@Type(value = LongResponseQuestion.class), @Type(value = RadioQuestion.class),
		@Type(value = NumericQuestion.class), @Type(value = ShortResponseQuestion.class),
		@Type(value = SelectionOption.class), @Type(value = ImageQuestion.class)})
/*
 *@param <E>  user submission type
 *@param <V> is server-side submission evaluation format, this parameter is sometimes used to store data such as a description and other times it is the value weight (cost/hour)
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

	public void setResponse(E entry);

	/*
	 * implementation defines how response is priced
	 * @return pricing of QuoteEntry
	 */
	public double calculateServiceCost();

	public void setRequired(boolean required);

	public void setCategory(String category);

	public String getCategory();

}
