/**
 * 
 */
package com.odsaprojects.prod.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Named;

import com.odsaprojects.prod.dao.UsuariosDao;
import com.odsaprojects.prod.dao.impl.UsuariosDaoImpl;
import com.odsaprojects.prod.entities.Usuarios;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@RequestScoped
public class UsuariosBean {
	
	private List<Usuarios> userList;
	private Boolean editar;
	private String nombre;
	private String apellidos;
	private String email;
	private String cedula;
	
	private HtmlDataTable table;
	private Usuarios usuario;
	private int index;
	
	private String userLogueado;
	
	UsuariosDao dao;

	public UsuariosBean() {
		userList = DevolverUsuarios();
		setEditar(false);
	}

	public void Seleccionar(Usuarios usuario) {
		nombre = usuario.getNombre();
		apellidos = usuario.getApellidos();
		email = usuario.getEmail();
		cedula = usuario.getCedula();
		
		setEditar(true);
	}
	
	public void Modificar() {
		dao = new UsuariosDaoImpl();
		
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setEmail(email);
		usuario.setCedula(cedula);
		
		if (dao.RegistrarUsuarios(usuario)) {
			try{
				userList.set(index, usuario);
				setEditar(false);
		    }catch(Exception e){
		      System.out.println(e);
		    }
		}
	}
	
	public void Eliminar() {
		dao = new UsuariosDaoImpl();
		
		usuario = (Usuarios) table.getRowData();
		index = table.getRowIndex();
		usuario.setEstado(0);
		
		if (dao.RegistrarUsuarios(usuario)) {
			try{
				//userList.set(index, usuario);
				userList = DevolverUsuarios();
				setEditar(false);
		    }catch(Exception e){
		      System.out.println(e);
		    }
		}
	}
	
	public List<Usuarios> getUserList() {
		return userList;
	}

	public void setUserList(List<Usuarios> userList) {
		this.userList = userList;
	}
	
	public List<Usuarios> DevolverUsuarios() {
		dao = new UsuariosDaoImpl();
		List<Usuarios> result = null;
		
		result = dao.DevolverUsuarios();
		
		return result;
	}
	
	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
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

	public HtmlDataTable getTable() {
		return table;
	}

	public void setTable(HtmlDataTable table) {
		this.table = table;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}	

	public String getUserLogueado() {
		return userLogueado;
	}

	public void setUserLogueado(String userLogueado) {
		this.userLogueado = userLogueado;
	}

}
