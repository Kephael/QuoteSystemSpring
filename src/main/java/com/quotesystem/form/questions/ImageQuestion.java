package com.quotesystem.form.questions;

/*
 * User response is a String which contains the image encoded in base64
 */
public class ImageQuestion extends AbstractQuestion<String, Double> {

	@Override
	public double calculateServiceCost() {
		return 0;
	}

}
