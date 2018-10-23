/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Usuarios;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface UsuariosDao extends Serializable {

	public boolean RegistrarUsuarios(Usuarios usuarios);
	
	public Usuarios LoginUsuarios(String email, String password);
	
	public List<Usuarios> DevolverUsuarios();
	
	public Usuarios BuscarUsuario(String cedula);
	
	public boolean ActualizarUsuario(Usuarios usuarios);
	
	public Usuarios BuscarUsuarioById(Long id);
	
	public Usuarios BuscarUsuarioByEmail(String email);
}
