#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.security;

public interface Encoder {
	public String encode(String password, String salt);
}
