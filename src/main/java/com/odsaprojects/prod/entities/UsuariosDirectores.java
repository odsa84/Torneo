/**
 * 
 */
package com.odsaprojects.prod.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Entity
@Table(name = "usuarios_directores")
@NamedQueries({ @NamedQuery(name = "UsuariosDirectores.findAll", query = "SELECT ud FROM UsuariosDirectores ud"),
	@NamedQuery(name = "UsuariosDirectores.findByDirectores", query = "SELECT ud FROM UsuariosDirectores ud WHERE ud.id_directores = :idDirectores"),
	@NamedQuery(name = "UsuariosDirectores.findByUsuario", query = "SELECT ud FROM UsuariosDirectores ud WHERE ud.id_usuarios = :idUsuario")})
public class UsuariosDirectores implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long id_usuarios;
	private Long id_directores;

	public UsuariosDirectores() {

	}
	
	public UsuariosDirectores(Long id_usuarios, Long id_directores) {
		this.id_usuarios = id_usuarios;
		this.id_directores = id_directores;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "id_usuarios", nullable = false)
	public Long getId_usuarios() {
		return id_usuarios;
	}

	public void setId_usuarios(Long id_usuarios) {
		this.id_usuarios = id_usuarios;
	}

	@Column(name = "id_directores", nullable = false)
	public Long getId_directores() {
		return id_directores;
	}

	public void setId_directores(Long id_directores) {
		this.id_directores = id_directores;
	}
}
