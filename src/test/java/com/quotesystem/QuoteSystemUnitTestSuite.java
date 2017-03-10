/*
 * Unit Testing Suite for entire project
 */
package com.quotesystem;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.quotesystem.form.questions.BooleanQuestionTest;
import com.quotesystem.form.questions.RadioQuestionTest;
import com.quotesystem.datacontrollers.QuoteRestControllerTest;
import com.quotesystem.form.QuoteTest;

@RunWith(Suite.class)
@SuiteClasses({ BooleanQuestionTest.class, QuoteTest.class, RadioQuestionTest.class, QuoteRestControllerTest.class })
public class QuoteSystemUnitTestSuite {

}
