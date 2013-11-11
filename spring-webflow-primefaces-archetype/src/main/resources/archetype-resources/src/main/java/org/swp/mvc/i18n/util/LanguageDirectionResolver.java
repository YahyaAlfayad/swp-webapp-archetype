#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.mvc.i18n.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Responsible for resolving languages writing direction.
 * 
 * @author "Yahya Alfayad"
 * 
 */
public class LanguageDirectionResolver {
	private Map<String, LanguageDirection> langDirs;

	/**
	 * 
	 * @param locale
	 * @return
	 */
	public LanguageDirection resolve(Locale locale) {
		return langDirs.get(locale.getLanguage().substring(0, 2));
	}

	public LanguageDirectionResolver() {
		super();
		langDirs = new HashMap<String, LanguageDirection>();
		langDirs.put("en", LanguageDirection.LTR);
		langDirs.put("ar", LanguageDirection.RTL);
	}
}
