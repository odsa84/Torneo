/**
 * 
 */
package com.odsaprojects.prod.util;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class ClaseFiltro implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		String loginURI = request.getContextPath() + Constantes.FILTROL;
		String registerURI = request.getContextPath() + Constantes.FILTROR;

		boolean loggedIn = session != null && session.getAttribute("usuario") != null;
		boolean loginRequest = request.getRequestURI().equals(loginURI);
		boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
		boolean registerRequest = request.getRequestURI().equals(registerURI);

		if (loggedIn || loginRequest || resourceRequest || registerRequest) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(loginURI);
		}

	}  

	public void destroy() {}

	public void init(FilterConfig arg0) throws ServletException {

	}  
}
