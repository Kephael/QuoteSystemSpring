package com.quotesystem.form.questions;

/*
 * Free Response questions are questions where users enter text and no pricing is associated with this response
 */
public class FreeResponseQuestion extends QuestionImpl<String, String> {

	@Override
	public double getCost() {
		return 0;
	}

}
