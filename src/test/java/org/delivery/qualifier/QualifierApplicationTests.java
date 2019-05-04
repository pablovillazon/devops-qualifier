package org.delivery.qualifier;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //components 
public class QualifierApplicationTests {

	@LocalServerPort
	int localPort;

	@Autowired
	TestRestTemplate restTemplate;

	@Value("${adjectives}")
    List<String> adjectives;

    @Value("${nouns}")
    List<String> nouns;

	@Test
	public void testRootIndexContent() {
		String content = restTemplate.getForObject("http://localhost:" + localPort + "/", String.class);
		assertTrue(content.contains("Spring boot"));
	}

	@Test
	public void testQualifier() {
		String name = "roberto";
		String content = restTemplate.getForObject("http://localhost:" + localPort 
		+ "/qualify/" + name, String.class);	
		assertTrue(content.startsWith(name));
	}

	@Test
	public void testQualifierNouns() {
		String name = "roberto";
		String content = restTemplate.getForObject("http://localhost:" + localPort 
		+ "/qualify/" + name, String.class);	
		assertTrue(nouns.stream()
			.anyMatch(noun -> content.contains(noun.toString())));
	}

	@Test
	public void testQualifierAdjectives() {
		String name = "roberto";
		String content = restTemplate.getForObject("http://localhost:" + localPort 
		+ "/qualify/" + name, String.class);	
		assertTrue(adjectives.stream()
			.anyMatch(adj -> content.contains(adj.toString())));
	}

}

