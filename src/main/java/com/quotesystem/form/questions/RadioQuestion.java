package com.quotesystem.form.questions;

import java.util.List;

/*
 * RadioQuestions accept a user input denoting the selection within a list of QuestionImpl, i.e. 1 for RadioOption 2.
 * This allows for a question of mixed types, i.e. 1. Numeric 2. Boolean 3. Free Response
 */
public class RadioQuestion extends QuestionImpl<Integer, List<QuestionImpl>> {

	public RadioQuestion() {
		super();
		this.setType(QuestionType.RADIO);
	}

	@Override
	public double getServiceCost() {
		if (this.getValue() != null && this.getResponse() != null) {
			return this.getValue().get(this.getResponse()).getServiceCost();
		}
		return 0.0;
	}

}
