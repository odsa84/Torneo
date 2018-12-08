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
@Table(name = "faltas")
@NamedQueries({@NamedQuery(name = "Faltas.findAll", query = "SELECT f FROM Faltas f"),
	@NamedQuery(name = "Faltas.findById", query = "SELECT f FROM Faltas f WHERE f.id = :id")})
public class Faltas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String tipoFalta;

	public Faltas() {

	}
	
	public Faltas(Long id, String tipoFalta) {
		this.id = id;
		this.tipoFalta = tipoFalta;
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

	@Column(name = "tipoFalta", nullable = false)
	public String getTipoFalta() {
		return tipoFalta;
	}

	public void setTipoFalta(String tipoFalta) {
		this.tipoFalta = tipoFalta;
	}
}
