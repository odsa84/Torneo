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
@Table(name = "goles_resultados_calendario")
@NamedQueries({@NamedQuery(name = "GolesResultadosCalendario.findAll", query = "SELECT grc FROM GolesResultadosCalendario grc"),
	@NamedQuery(name = "GolesResultadosCalendario.findById", query = "SELECT grc FROM GolesResultadosCalendario grc "
			+ "WHERE grc.id = :id"),
	@NamedQuery(name = "GolesResultadosCalendario.findByIdCalendario", query = "SELECT grc FROM GolesResultadosCalendario grc "
			+ "WHERE grc.calendario.id = :idCalendario"),
	@NamedQuery(name = "GolesResultadosCalendario.findByIdEquipo", query = "SELECT grc FROM GolesResultadosCalendario grc "
			+ "WHERE grc.equipo.id = :idEquipo"),
	@NamedQuery(name = "GolesResultadosCalendario.findByIdJugador", query = "SELECT grc FROM GolesResultadosCalendario grc "
			+ "WHERE grc.jugador.id = :idJugador"),
	@NamedQuery(name = "GolesResultadosCalendario.findByIdGol", query = "SELECT grc FROM GolesResultadosCalendario grc "
			+ "WHERE grc.gol.id = :idGol"),
	@NamedQuery(name = "GolesResultadosCalendario.findByMinGol", query = "SELECT grc FROM GolesResultadosCalendario grc "
			+ "WHERE grc.minGol = :min")})
public class GolesResultadosCalendario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Calendario calendario;
	private Equipos equipo;
	private Jugadores jugador;
	private Gol gol;
	private int minGol;

	public GolesResultadosCalendario() {

	}
	
	public GolesResultadosCalendario(Long id, Calendario calendario, Equipos equipo, Jugadores jugador, Gol gol, int minGol) {
		this. id = id;
		this.calendario = calendario;
		this.equipo = equipo;
		this.jugador = jugador;
		this.gol = gol;
		this.minGol = minGol;
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
	@JoinColumn(name = "id_gol")
	public Gol getGol() {
		return gol;
	}

	public void setGol(Gol gol) {
		this.gol = gol;
	}

	@Column(name = "minutoGol")
	public int getMinGol() {
		return minGol;
	}

	public void setMinGol(int minGol) {
		this.minGol = minGol;
	}
}
