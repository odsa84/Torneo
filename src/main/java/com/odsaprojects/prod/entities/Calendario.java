/**
 * 
 */
package com.odsaprojects.prod.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "calendario")
@NamedQueries({ @NamedQuery(name = "calendario.findAll", query = "SELECT c FROM Calendario c WHERE c.estado = 1"),
	@NamedQuery(name = "calendario.findById", query = "SELECT c FROM Calendario c WHERE c.id = :id AND c.estado = 1"),
	@NamedQuery(name = "calendario.findByIdCampeonato", query = "SELECT c FROM Calendario c WHERE c.campeonato = :idCampeonato "
			+ "AND c.estado = 1"),
	@NamedQuery(name = "calendario.findByMonthAndYear", query = "SELECT c FROM Calendario c WHERE MONTH(c.fechaHoraInicio) = :mes "
			+ "AND YEAR(c.fechaHoraInicio) = :anio"),
	@NamedQuery(name = "calendario.findByEquipos", query = "SELECT c FROM Calendario c WHERE c.equipo1 = :idEqp "
			+ "OR c.equipo2 = :idEqp AND c.estado = 1")})
public class Calendario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long equipo1;
	private Long equipo2;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private Long campeonato;
	private int estado;

	public Calendario() {
		
	}
	
	public Calendario(Long id, Long equipo1, Long equipo2, Date fechaHoraInicio, Date fechaHoraFin, Long campeonato, int estado) {
		this.id = id;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this. campeonato = campeonato;
		this. estado = estado;
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

	@Column(name = "equipo1", nullable = false)
	public Long getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(Long equipo1) {
		this.equipo1 = equipo1;
	}

	@Column(name = "equipo2", nullable = false)
	public Long getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Long equipo2) {
		this.equipo2 = equipo2;
	}

	@Column(name = "fechaHoraInicio", nullable = false)
	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	
	@Column(name = "fechaHoraFin", nullable = false)
	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	@Column(name = "campeonato", nullable = false)
	public Long getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Long campeonato) {
		this.campeonato = campeonato;
	}

	@Column(name = "estado", nullable = false)
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
}
