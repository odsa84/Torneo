/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Jugadores;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface JugadoresDao extends Serializable {
	
	public boolean RegistrarJugador(Jugadores jugador);
	
	public List<Jugadores> DevolverJugadores(long idUsuario);
	
	public List<Jugadores> BuscarJugadoresPorEquipos(Long long1);
	
	public Jugadores BuscarJugadorPorCedula(String cedula);
	
	public Jugadores BuscarJugadorPorId(long id);

}
