/**
 * 
 */
package com.odsaprojects.prod.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Entity
@Table(name = "calendario")
@NamedQueries({ @NamedQuery(name = "calendario.findAll", query = "SELECT c FROM Calendario c WHERE c.estado = 1"),
	@NamedQuery(name = "calendario.findById", query = "SELECT c FROM Calendario c WHERE c.id = :id AND c.estado = 1"),
	@NamedQuery(name = "calendario.findByIdCampeonato", query = "SELECT c FROM Calendario c WHERE c.campeonato = :idCampeonato AND c.estado = 1")})
public class Calendario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long equipo1;
	private Long equipo2;
	private Date fechaHoraInicio;
	private Long campeonato;
	private int estado;

	public Calendario() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(Long equipo1) {
		this.equipo1 = equipo1;
	}

	public Long getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Long equipo2) {
		this.equipo2 = equipo2;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public Long getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Long campeonato) {
		this.campeonato = campeonato;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
}
