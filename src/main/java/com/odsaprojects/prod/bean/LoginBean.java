/**
 * 
 */
package com.odsaprojects.prod.bean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odsaprojects.prod.dao.UsuariosDao;
import com.odsaprojects.prod.dao.impl.UsuariosDaoImpl;
import com.odsaprojects.prod.entities.Usuarios;
import com.odsaprojects.prod.util.EncriptacionMd5;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@RequestScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
	private UsuariosDao dao;
	
	@Inject
	private SessionUtils session;

	public LoginBean() {

	}

	public void Login() {
		Usuarios result = null;
		String encriptPass = getPassword();
		EncriptacionMd5 md5 = new EncriptacionMd5();
		
		dao = new UsuariosDaoImpl();
		
		if (!(encriptPass.isEmpty()))		
			encriptPass = md5.EncriptarMD5(getPassword());
		
		result = dao.LoginUsuarios(getEmail(), encriptPass);
		
		if(result != null) {
			LimpiarCampos();
			session.add("usuario", result.getNombre());
			session.add("idUsuario", result.getId());
			session.add("objUsuario", result);
			session.redirectPage("indexAdm.xhtml");
		}else {
			
			session.sendMessageToView("Usuario o contraseña incorrectos");
		}
		
	}
	
	public void Logout() {
		session.logoutUsuario();
		session.redirectPage("login.xhtml");
	}
	
	public void LimpiarCampos() {
		setEmail(null);
		setPassword(null);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
