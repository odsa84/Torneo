/**
 * 
 */
package com.odsaprojects.prod.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@ViewScoped
public class CalendarioAdmBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long equipo1;
	private Long equipo2;
	private Date fechaHoraInicio;
	private Long campeonato;
	private int estado;
	
	private long idShp;

	@SuppressWarnings("rawtypes")
	public CalendarioAdmBean() {
		String aux = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		aux = (String) params.get("shp");
		this.setIdShp(Long.valueOf(aux));
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

	public long getIdShp() {
		return idShp;
	}

	public void setIdShp(long idShp) {
		this.idShp = idShp;
	}

}
