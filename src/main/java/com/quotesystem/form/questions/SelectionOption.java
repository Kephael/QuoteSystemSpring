package com.quotesystem.form.questions;

/*
 * SelectionOption is used to populate CheckboxQuestion and RadioQuestions with String selections stored in the SelectionOption prompt.
 * These predefined selections have a service cost determined by the valueWeight and value.
 */
public class SelectionOption extends AbstractQuestion<String, Double> {

	@Override
	public double calculateServiceCost() {
		if (this.getValue() != null) {
			return this.getValueWeight() * this.getValue();
		}
		return 0.0;
	}

}
