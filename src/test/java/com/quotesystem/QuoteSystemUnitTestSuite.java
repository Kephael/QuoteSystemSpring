/*
 * Unit Testing Suite for entire project
 */
package com.quotesystem;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.quotesystem.auth.AuthenticationControllerTest;
import com.quotesystem.datacontrollers.QuoteRestControllerTest;
import com.quotesystem.datacontrollers.TemplateRestControllerTest;
import com.quotesystem.form.QuoteTest;
import com.quotesystem.form.TemplateTest;
import com.quotesystem.form.questions.BooleanQuestionTest;
import com.quotesystem.form.questions.LongResponseQuestionTest;
import com.quotesystem.form.questions.CheckboxQuestionTest;

@RunWith(Suite.class)
@SuiteClasses({ BooleanQuestionTest.class, QuoteTest.class, CheckboxQuestionTest.class, QuoteRestControllerTest.class,
		TemplateRestControllerTest.class, TemplateTest.class, LongResponseQuestionTest.class, AuthenticationControllerTest.class })
public class QuoteSystemUnitTestSuite {

}
