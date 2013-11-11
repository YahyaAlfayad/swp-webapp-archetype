#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.security;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swp.security.PasswordUtil;

public class PasswordUtilTest {
	private static Logger logger = LoggerFactory
			.getLogger(PasswordUtilTest.class);

	@Test
	public void testEncodePassword() {
		String code = PasswordUtil.encodePassword("admin", "admin");
		logger.info("admin password is: " + code);
		assertEquals(
				"Failed to encode password",
				"a4a88c0872bf652bb9ed803ece5fd6e82354838a9bf59ab4babb1dab322154e1",
				code);
	}

}
