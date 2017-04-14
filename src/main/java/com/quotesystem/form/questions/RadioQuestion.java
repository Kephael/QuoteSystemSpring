package com.quotesystem.form.questions;

import java.util.List;

/*
 * RadioQuestion accepts a user input denoting the selection of a list of SelectionOption, i.e. 1 for SelectionOption 2 or 0 for option 1.
 * This allows for a question composed of SelectionOptions (String selections) 
 */
public class RadioQuestion extends AbstractQuestion<Integer, List<SelectionOption>> {

	@Override
	public double calculateServiceCost() {
		double calculatedServiceCost = 0;
		if (this.getValue() != null && this.getResponse() != null ) {
				if (this.getValue().size() > this.getResponse()){ // handles invalid selection of SelectionOption from attempted to be calculated
					calculatedServiceCost += this.getValue().get(this.getResponse()).calculateServiceCost(); // use response integer to choose which selection option to evaluate
				}
		}
		return calculatedServiceCost;
	}

}
