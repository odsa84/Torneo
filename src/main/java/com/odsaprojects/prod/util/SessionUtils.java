/**
 * 
 */
package com.odsaprojects.prod.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.bean.EquiposAdmBean;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class SessionUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(EquiposAdmBean.class); 

	public SessionUtils() {
	}
	
	public void add(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
	}
	
	public Object get(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
	}
	
	public void redirectPage(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	public void sendMessageToView(String message) {		
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void sendWarningMessageToView(String message) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void sendErrorMessageToView(String message) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void sendFatalMessageToView(String message) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void logoutUsuario() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

}
