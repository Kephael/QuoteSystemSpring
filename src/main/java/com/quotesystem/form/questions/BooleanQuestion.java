
package com.quotesystem.form.questions;

/*
 * If the user response evaluates to true then the quote entry value and ValueWeight will be factored into total quote cost,
 * otherwise the quote price will remain unchanged
 */
public class BooleanQuestion extends QuestionImpl<Boolean, Double> {

	public BooleanQuestion() {
		super();
		this.setType(QuestionType.BOOLEAN);
	}
	
	@Override
	public double getServiceCost() {
		if (this.getResponse() != null && this.getResponse()) {
			return this.getValue() * this.getValueWeight();
		}
		return 0;
	}

}
