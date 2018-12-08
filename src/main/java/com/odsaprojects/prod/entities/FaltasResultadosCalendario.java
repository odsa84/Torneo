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
@Table(name = "faltas_resultados_calendario")
@NamedQueries({@NamedQuery(name = "FaltasResultadosCalendario.findAll", query = "SELECT frc FROM FaltasResultadosCalendario frc"),
	@NamedQuery(name = "FaltasResultadosCalendario.findById", query = "SELECT frc FROM FaltasResultadosCalendario frc "
			+ "WHERE frc.id = :id"),
	@NamedQuery(name = "FaltasResultadosCalendario.findByIdCalendario", query = "SELECT frc FROM FaltasResultadosCalendario frc "
			+ "WHERE frc.calendario.id = :idCalendario"),
	@NamedQuery(name = "FaltasResultadosCalendario.findByIdEquipo", query = "SELECT frc FROM FaltasResultadosCalendario frc "
			+ "WHERE frc.equipo.id = :idEquipo"),
	@NamedQuery(name = "FaltasResultadosCalendario.findByIdJugador", query = "SELECT frc FROM FaltasResultadosCalendario frc "
			+ "WHERE frc.jugador.id = :idJugador"),
	@NamedQuery(name = "FaltasResultadosCalendario.findByIdFalta", query = "SELECT frc FROM FaltasResultadosCalendario frc "
			+ "WHERE frc.falta.id = :idFalta"),
	@NamedQuery(name = "FaltasResultadosCalendario.findByMinFalta", query = "SELECT frc FROM FaltasResultadosCalendario frc "
			+ "WHERE frc.minFalta = :min")})
public class FaltasResultadosCalendario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Calendario calendario;
	private Equipos equipo;
	private Jugadores jugador;
	private Faltas falta;
	private int minFalta;

	public FaltasResultadosCalendario() {

	}
	
	public FaltasResultadosCalendario(Long id, Calendario calendario, Equipos equipo, Jugadores jugador, Faltas falta, int minFalta) {
		this. id = id;
		this.calendario = calendario;
		this.equipo = equipo;
		this.jugador = jugador;
		this.falta = falta;
		this.minFalta = minFalta;
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

	@ManyToOne()
	@JoinColumn(name = "id_calendario")
	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	@ManyToOne()
	@JoinColumn(name = "id_equipo")
	public Equipos getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipos equipo) {
		this.equipo = equipo;
	}

	@ManyToOne()
	@JoinColumn(name = "id_jugador")
	public Jugadores getJugador() {
		return jugador;
	}

	public void setJugador(Jugadores jugador) {
		this.jugador = jugador;
	}

	@ManyToOne()
	@JoinColumn(name = "id_falta")
	public Faltas getFalta() {
		return falta;
	}

	public void setFalta(Faltas falta) {
		this.falta = falta;
	}

	@Column(name = "minutoFalta")
	public int getMinFalta() {
		return minFalta;
	}

	public void setMinFalta(int minFalta) {
		this.minFalta = minFalta;
	}

}
