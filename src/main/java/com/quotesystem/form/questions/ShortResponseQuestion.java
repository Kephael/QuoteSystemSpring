package com.quotesystem.form.questions;

/*
 * Short Response questions are questions where users enter text and no pricing is associated with this response
 */
public class ShortResponseQuestion extends AbstractQuestion<String, String> {

	@Override
	public double calculateServiceCost() {
		return 0;
	}

}
