package com.quotesystem.form.questions;

import com.mongodb.gridfs.GridFS;

public class ImageQuestion extends AbstractQuestion<GridFS, Double> {

	@Override
	public double calculateServiceCost() {
		return 0;
	}

}
