/**
 * 
 */
package com.odsaprojects.prod.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.odsaprojects.prod.dao.UsuariosDao;
import com.odsaprojects.prod.dao.impl.UsuariosDaoImpl;
import com.odsaprojects.prod.entities.Usuarios;
import com.odsaprojects.prod.util.EncriptacionMd5;

/**
 * @author Osvaldo Sandoval
 *
 */
@ManagedBean(name = "registerBean")
@ViewScoped
public class RegisterBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String apellidos;
	private String password;
	private String email;
	private String cedula;
	
	private String outputText;
	private Boolean util;
	
	UsuariosDao dao;
		

	/**
	 * 
	 */
	public RegisterBean() {
		setOutputText(null);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}		

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}	

	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}	

	public Boolean getUtil() {
		return util;
	}

	public void setUtil(Boolean util) {
		this.util = util;
	}

	public String Registro() {
		Usuarios user = new Usuarios();	
		EncriptacionMd5 md5 = new EncriptacionMd5();
		String flag = "Ok";
		
		dao = new UsuariosDaoImpl();		
		
		user.setNombre(getNombre());
		user.setApellidos(getApellidos());
		user.setCedula(getCedula());
		user.setPassword(md5.EncriptarMD5(getPassword()));
		user.setEmail(getEmail());
		user.setEstado(1);
		
		if (dao.RegistrarUsuarios(user)) {
			LimpiarCampos();
		}else {
			setOutputText("Error al insertar los datos");
			setUtil(false);
			flag = "Error";
		}
			
		return flag;
	}
	
	public void LimpiarCampos() {

		setNombre(null);
		setApellidos(null);
		setCedula(null);
		setEmail(null);
		setPassword(null);
		
		setOutputText("Registro satisfactorio");
		setUtil(true);
	}

}
