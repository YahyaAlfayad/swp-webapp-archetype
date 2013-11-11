#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.swp.mvc.login;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.RememberMeServices;

public class LoginManagedBean {
	private String userName;
	private String password;
	private Boolean rememberMe;
	private String successPage;
	private String failPage;
	private static Logger logger = LoggerFactory
			.getLogger(LoginManagedBean.class);

	@Autowired
	private RememberMeServices rememberMeServices;

	public String login() {
		logger.debug("form login attempt");
		logger.debug("Login bean properties: username=" + userName
				+ ", password=" + password + ", remember me=" + rememberMe);
		ExternalContext ecntx = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			ecntx.getRequestMap().put("j_username", userName);
			ecntx.getRequestMap().put("j_password", password);
			if (rememberMe) {
				ecntx.getRequestMap().put("_spring_security_remember_me", "on");
			}
			logger.debug("form login attempt: username="
					+ ecntx.getRequestMap().get("j_username") + " ,password="
					+ ecntx.getRequestMap().get("j_password")
					+ ", remember me= "
					+ ecntx.getRequestMap().get("_spring_security_remember_me"));
			RequestDispatcher rd = getRequest().getRequestDispatcher(
					getResponse().encodeURL("/j_spring_security_check"));
			rd.forward((ServletRequest) ecntx.getRequest(),
					(ServletResponse) ecntx.getResponse());
			FacesContext.getCurrentInstance().responseComplete();
		} catch (ServletException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		logger.debug("username was set to: " + userName);
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		logger.debug("password was set to: " + password);
		this.password = password;
	}

	public Boolean getRememberMe() {
		return this.rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	private AuthenticationManager authenticationManager;

	private HttpServletRequest getRequest() {
		ExternalContext ecntx = FacesContext.getCurrentInstance()
				.getExternalContext();
		Object r = ecntx.getRequest();
		if (r instanceof HttpServletRequest) {
			return (HttpServletRequest) r;
		} else {
			return null;
		}
	}

	private HttpServletResponse getResponse() {
		ExternalContext ecntx = FacesContext.getCurrentInstance()
				.getExternalContext();
		Object res = ecntx.getResponse();
		if (res instanceof HttpServletResponse) {
			return (HttpServletResponse) res;
		} else {
			return null;
		}
	}

	public String programmaticLogin() {
		logger.debug("programmatic login attempt: username=" + userName
				+ ", password=" + password + ", remember me=" + rememberMe);
		logger.debug("authentication manager=" + authenticationManager);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				userName, password);
		try {
			Authentication auth = authenticationManager.authenticate(token);
			logger.debug("Authentication result=" + auth);
			SecurityContextHolder.getContext().setAuthentication(auth);
			if (rememberMe) {
				rememberMeServices.loginSuccess(getRequest(), getResponse(),
						auth);
			}
		} catch (AuthenticationException e) {
			FacesContext cntx = FacesContext.getCurrentInstance();
			String errorMessage = cntx.getApplication()
					.getResourceBundle(cntx, "loginMsg")
					.getString("invalidMessage");
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
							""));
			logger.debug("fail page is:" + failPage);
			return failPage;
		}
		logger.debug("success page is:" + successPage);
		return successPage;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public String getSuccessPage() {
		return successPage;
	}

	public void setSuccessPage(String successPage) {
		logger.debug("success page was set to: " + successPage);
		this.successPage = successPage;
	}

	public String getFailPage() {
		return failPage;
	}

	public void setFailPage(String failPage) {
		this.failPage = failPage;
	}

	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}
}
