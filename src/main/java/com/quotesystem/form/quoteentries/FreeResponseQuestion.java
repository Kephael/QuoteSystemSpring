package com.quotesystem.form.quoteentries;

/*
 * Free Response questions are questions where users enter text and no pricing is associated with this response
 */
public class FreeResponseQuestion extends QuoteEntryImpl<String, String> {

	@Override
	public double getCost() {
		return 0;
	}

}
