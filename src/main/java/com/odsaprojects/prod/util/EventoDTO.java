/**
 * 
 */
package com.odsaprojects.prod.util;

import java.util.Date;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class EventoDTO {
	
	private Long id;
	private String nombreEquipo1;
	private String nombreEquipo2;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private String nombreCampeonato;


	public EventoDTO() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombreEquipo1() {
		return nombreEquipo1;
	}


	public void setNombreEquipo1(String nombreEquipo1) {
		this.nombreEquipo1 = nombreEquipo1;
	}


	public String getNombreEquipo2() {
		return nombreEquipo2;
	}


	public void setNombreEquipo2(String nombreEquipo2) {
		this.nombreEquipo2 = nombreEquipo2;
	}


	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}


	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}


	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}


	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}


	public String getNombreCampeonato() {
		return nombreCampeonato;
	}


	public void setNombreCampeonato(String nombreCampeonato) {
		this.nombreCampeonato = nombreCampeonato;
	}
	
	

}
