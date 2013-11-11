#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.mvc.login;

import java.io.Serializable;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class SecurityManagedBean implements Serializable {
	private AuthenticationTrustResolver trustResolver;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory
			.getLogger(SecurityManagedBean.class);

	public boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication()
				.isAuthenticated();
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public String getUserName() {
		return getAuthentication().getName();
	}

	public String getPassword() {
		Object details = getAuthentication().getPrincipal();
		if (details instanceof User) {
			UserDetails u = (User) details;
			return u.getPassword();
		} else {
			return null;
		}

	}

	// public boolean isUserInRole(String role) {
	// logger.debug("role passed is:" + role);
	// return getAuthentication().getAuthorities().contains(role);
	// }

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getAuthentication().getAuthorities();
	}

	public boolean permitAll() {
		return true;
	}

	public boolean denyAll() {
		return false;
	}

	public boolean hasRole(String role) {
		logger.debug("Role is: " + role);
		for (GrantedAuthority authority : getAuthorities()) {
			if (authority.getAuthority().equals(role)) {
				logger.debug("has role " + true);
				return true;
			}
		}
		logger.debug("has role " + false);
		return false;
	}

	// public boolean hasAnyRole(String[] roles) {
	// for (String role : roles) {
	// if (hasRole(role)) {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 
	 * @param roles
	 *            list of comma separated roles.
	 * @return
	 */
	public boolean hasAnyRole(String roles) {
		logger.debug("Roles are: " + roles);
		for (String role : roles.split(",")) {
			if (hasRole(role.trim())) {
				logger.debug("has any of " + roles + " = " + true);
				return true;
			}
		}
		logger.debug("has any of " + roles + " = " + false);
		return false;
	}

	public boolean hasAllRoles(String roles) {
		logger.debug("roles are: " + roles);
		for (String role : roles.split(",")) {
			if (!hasRole(role.trim())) {
				logger.debug("doesnt have role: " + role);
				return false;
			}
		}
		logger.debug("has all roles: " + roles + " = " + true);
		return true;
	}

	private AuthenticationTrustResolver getTrustResolver() {
		if (this.trustResolver == null) {
			this.trustResolver = new AuthenticationTrustResolverImpl();
		}
		return this.trustResolver;
	}

	public boolean isAnonymous() {
		return getTrustResolver().isAnonymous(getAuthentication());
	}

	public boolean isRememberMe() {
		return getTrustResolver().isRememberMe(getAuthentication());
	}

	public boolean isFullyAuthenticated() {
		if (!(isRememberMe() || isAnonymous())) {
			return true;
		} else
			return false;
	}

	public String getRemoteAddress() {
		Object d = getAuthentication().getDetails();
		if (d instanceof WebAuthenticationDetails) {
			String address = ((WebAuthenticationDetails) d).getRemoteAddress();
			logger.debug("Remote Address is:" + address);
			return address;
		} else {
			logger.debug("null IP address returned");
			return null;
		}
	}

	public boolean hasAddress(String address) {
		return address.equals(getRemoteAddress());
	}
}
