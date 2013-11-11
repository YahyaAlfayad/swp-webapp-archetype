#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package test.logger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {

	@Test
	public void test() {
		Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
		logger.info("this in info message!");
		logger.debug("this is debug message!");
		logger.error("this is error message");
		logger.warn("this is warning");
	}

}
