package com.quotesystem.form.questions;

/*
 * Free Response questions are questions where users enter text and no pricing is associated with this response
 */
public class LongResponseQuestion extends AbstractQuestion<String, String> {

	public LongResponseQuestion() {
		super();
		this.setType(QuestionType.LONG_RESPONSE);
	}

	@Override
	public double calculateServiceCost() {
		return 0;
	}

}
