/**
 * 
 */
package com.gdantas.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BuscaCepResourceTest {

	private ApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		if(context == null) {
			context = new ClassPathXmlApplicationContext("classpath:config/dev.context.xml");
		}
	}
	
	@Test
	private void testBuscaCep() {
		
	}
	
}
