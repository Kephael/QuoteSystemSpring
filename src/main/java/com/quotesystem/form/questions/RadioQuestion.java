package com.quotesystem.form.questions;

import java.util.List;

/*
 * RadioQuestion accepts a user input denoting the selection of a list of SelectionOption, i.e. 1 for SelectionOption 2 or 0 for option 1.
 * This allows for a question composed of SelectionOptions (String selections) 
 */
public class RadioQuestion extends AbstractQuestion<Integer, List<SelectionOption>> {

	boolean radioMode; // true indicates the front end client should display with radio buttons, false indicates drop down menu selection
	
	/**
	 * @return the radioMode
	 */
	public boolean isRadioMode() {
		return radioMode;
	}

	/**
	 * @param radioMode the radioMode to set
	 */
	public void setRadioMode(boolean radioMode) {
		this.radioMode = radioMode;
	}

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
