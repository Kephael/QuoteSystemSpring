package com.quotesystem.form.questions;

/*
 * NumericQuestion takes the user input response and multiples it by the value weight
 */
public class NumericQuestion extends AbstractQuestion<Double, Double> {

	public NumericQuestion() {
		super();
		this.setType(QuestionType.NUMERIC);
	}

	@Override
	public double calculateServiceCost() {
		if (this.getResponse() != null) {
			return this.getResponse() * this.getValueWeight();
		}
		return 0;
	}

}
