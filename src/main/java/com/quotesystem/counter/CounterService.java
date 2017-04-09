/*
 * As described at https://gerrytan.wordpress.com/2013/05/16/auto-incrementing-field-on-spring-data-mongodb/
 */

package com.quotesystem.counter;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
	
	@Autowired
	private MongoOperations mongo;

	public Long getNextSequence(String collectionName) {

		Counter counter = mongo.findAndModify(query(where("_id").is(collectionName)), new Update().inc("seq", 1),
				options().returnNew(true), Counter.class);
		if (counter == null){ // counter returned nothing, must be new counter
			counter = new Counter();
			counter.setId(collectionName);
			counter.setSeq(0L);
			mongo.insert(counter);
			return 0L;
		}
		return counter.getSeq();
	}

}