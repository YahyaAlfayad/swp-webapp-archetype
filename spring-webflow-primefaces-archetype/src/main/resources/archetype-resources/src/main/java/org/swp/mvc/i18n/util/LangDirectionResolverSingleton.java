#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.mvc.i18n.util;

import java.util.Locale;

/**
 * A singleton to retrieve <code>LanguageDirectionResolver</code> object.
 * 
 * @author "Yahya Alfayad"
 * @see LanguageDirectionResolver
 * 
 */
public class LangDirectionResolverSingleton {
	private static LanguageDirectionResolver resolver;

	/**
	 * Loads new <code>LanguageDirectionResolver</code> object to be used by
	 * this singleton clients.
	 */
	private static void loadDirectionResolver() {
		resolver = new LanguageDirectionResolver();
	}

	/**
	 * Resolves the specified <code>locale</code>'s language writing direction.
	 * 
	 * @param locale
	 *            to resolve it's language writing direction.
	 * @return specified locale's language direction.
	 * @see LanguageDirectionResolver${symbol_pound}resolve(Locale)
	 */
	public static LanguageDirection resolveDirection(Locale locale) {
		if (resolver == null) {
			loadDirectionResolver();
		}
		return resolver.resolve(locale);
	}
}
