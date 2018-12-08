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
@Table(name = "gol")
@NamedQueries({@NamedQuery(name = "Gol.findAll", query = "SELECT g FROM Gol g"),
	@NamedQuery(name = "Gol.findById", query = "SELECT g FROM Gol g WHERE g.id = :id")})
public class Gol implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String tipoGol;
	
	public Gol() {

	}
	
	public Gol(Long id, String tipoGol) {
		this.id = id;
		this.tipoGol = tipoGol;
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

	@Column(name = "tipoGol", nullable = false)
	public String getTipoGol() {
		return tipoGol;
	}

	public void setTipoGol(String tipoGol) {
		this.tipoGol = tipoGol;
	}
	
	

}
