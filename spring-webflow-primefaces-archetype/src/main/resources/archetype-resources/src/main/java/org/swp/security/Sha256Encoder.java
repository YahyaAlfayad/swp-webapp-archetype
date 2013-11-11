#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class Sha256Encoder implements Encoder {
	private ShaPasswordEncoder encoder;

	@Override
	public String encode(String password, String salt) {
		return encoder.encodePassword(password, salt);
	}

	/**
	 * 
	 */
	public Sha256Encoder() {
		super();
		this.encoder = new ShaPasswordEncoder(256);
	}

}
