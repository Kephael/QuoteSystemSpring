package com.quotesystem.form.questions;

import java.util.List;

/*
 * RadioQuestion accepts a user input denoting the selection of a single RadioOption, i.e. 1 for SelectionOption 2 or 5 for option 6 (array index position).
 * This allows for a question composed of SelectionOptions (String selections) 
 */
public class CheckboxQuestion extends AbstractQuestion<List<Integer>, List<SelectionOption>> {

	@Override
	public double calculateServiceCost() {
		double calculatedServiceCost = 0;
		if (this.getValue() != null && this.getResponse() != null ) {
			for (Integer userSelection : this.getResponse()) { // for each user selection
				if (this.getValue().size() > userSelection && this.getValue().get(userSelection) != null) { // prevents invalid selection of SelectionOption from attempted to be calculated
					calculatedServiceCost += this.getValue().get(userSelection).calculateServiceCost();
				}
			}
		}
		return calculatedServiceCost;
	}

}
