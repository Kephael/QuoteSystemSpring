
package com.quotesystem.form.quoteentries;

/*
 * If the user response evaluates to true then the quote entry value and ValueWeight will be factored into total quote cost,
 * otherwise the quote price will remain unchanged
 */
public class BooleanQuestion extends QuoteEntryImpl<Boolean, Double> {

	@Override
	public double getCost() {
		if (this.getResponse()) {
			return this.getValue() * this.getValueWeight();
		}
		return 0;
	}

}
