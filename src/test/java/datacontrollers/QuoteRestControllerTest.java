package datacontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.quotesystem.QuoteSystemSpring3Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuoteSystemSpring3Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuoteRestControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void loginTest() {
		String body = restTemplate.getForObject("/authenticate", String.class);
		assertThat(body).contains("Unauthorized");
	}
}
