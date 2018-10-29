package com.odsaprojects.prod.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Entity
@Table(name = "campeonatos")
@NamedQueries({ @NamedQuery(name = "Campeonatos.findAll", query = "SELECT c FROM Campeonatos c WHERE c.estado = 1"),
	@NamedQuery(name = "Campeonatos.findById", query = "SELECT c FROM Campeonatos c WHERE c.id = :id AND c.estado = 1"),
	@NamedQuery(name = "Campeonatos.findByUsuario", query = "SELECT c FROM Campeonatos c WHERE c.usuario.id = :idUsuario AND c.estado = 1"),
	@NamedQuery(name = "Campeonatos.findByIdCampeonato", query = "SELECT c FROM Campeonatos c WHERE c.idCampeonato = :idCampeonato AND c.estado = 1")})
public class Campeonatos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nombre;
	private int cantEquipos;
	private Date fechaInicio;
	private Date fechaFin;
	private Usuarios usuario;
	private int estado;
	private String idCampeonato;
	
	public Campeonatos() {
		
	}
	
	public Campeonatos(Long id, String nombre, int cantEquipos, Date fechaInicio, Date fechaFin, Usuarios usuario, int estado) {
		this.id = id;
		this.nombre = nombre;
		this.cantEquipos = cantEquipos;
		this.fechaInicio = fechaInicio;		
		this.fechaFin = fechaFin;
		this.usuario = usuario;
		this.estado = estado;
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

	@Column(name = "nombre", nullable = false, length = 256)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "cantEquipos", nullable = true)
	public int getCantEquipos() {
		return cantEquipos;
	}

	public void setCantEquipos(int cantEquipos) {
		this.cantEquipos = cantEquipos;
	}

	@Column(name = "fechaInicio", nullable = false)
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name = "fechaFin", nullable = true)
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@ManyToOne()
	@JoinColumn(name = "idUsuarioAdmin")
	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	@Column(name = "estado", nullable = false)
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Column(name = "idCampeonato", nullable = false)
	public String getIdCampeonato() {
		return idCampeonato;
	}

	public void setIdCampeonato(String idCampeonato) {
		this.idCampeonato = idCampeonato;
	}
	
	
}
