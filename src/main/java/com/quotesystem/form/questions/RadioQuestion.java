package com.quotesystem.form.questions;

import java.util.List;

/*
 * CheckboxQuestion accepts a user input denoting the selection of a list of SelectionOption, i.e. 1 for SelectionOption 2 or [1, 2] for options 2 and 3.
 * This allows for a question composed of SelectionOptions (String selections) 
 */
public class RadioQuestion extends AbstractQuestion<List<Integer>, List<SelectionOption>> {

	public RadioQuestion() {
		super();
		this.setType(QuestionType.RADIO);
	}

	@Override
	public double calculateServiceCost() {
		double calculatedServiceCost = 0;
		if (this.getValue() != null && this.getResponse() != null && !this.getResponse().isEmpty()
				&& !this.getResponse().isEmpty()) {
			for (Integer userSelection : this.getResponse()) { // for each user selection
				if (this.getValue().size() > userSelection && this.getValue().get(userSelection) != null){ // handles invalid selection of SelectionOption from attempted to be calculated
					calculatedServiceCost += this.getValue().get(userSelection).calculateServiceCost();
				}
			}
		}
		return calculatedServiceCost;
	}

}
