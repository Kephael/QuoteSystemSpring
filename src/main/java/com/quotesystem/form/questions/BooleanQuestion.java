
package com.quotesystem.form.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * If the user response evaluates to true then the quote entry value and ValueWeight will be factored into total quote cost,
 * otherwise the quote price will remain unchanged
 */
public class BooleanQuestion extends AbstractQuestion<Boolean, Double> {

	public BooleanQuestion() {
		super();
		this.setType(QuestionType.BOOLEAN);
	}
	
	@Override
	public double calculateServiceCost() {
		if (this.getResponse() != null && this.getResponse()) {
			return this.getValue() * this.getValueWeight();
		}
		return 0;
	}

}
