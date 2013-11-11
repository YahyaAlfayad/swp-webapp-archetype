#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.mvc.i18n.util;

import java.util.Locale;

import javax.faces.context.FacesContext;

public class LangUtils {
	public Locale getClientLocale() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestLocale();
	}

	/**
	 * the language direction
	 * 
	 * @return RTL or LTR.
	 */
	public String getLangDir() {
		return LangDirectionResolverSingleton
				.resolveDirection(getClientLocale()).name();
	}
}
