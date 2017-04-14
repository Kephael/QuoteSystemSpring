package com.quotesystem.form.questions;

/*
 * Long Response questions are questions where users enter text and no pricing is associated with this response
 */
public class LongResponseQuestion extends AbstractQuestion<String, String> {

	@Override
	public double calculateServiceCost() {
		return 0;
	}

}
