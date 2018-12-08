/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.GolesResultadosCalendario;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface GolesResultadosCalendarioDao extends Serializable {
	
	public List<GolesResultadosCalendario> DevolverTodo();
	
	public GolesResultadosCalendario DevolverPorId(long id);
	
	public GolesResultadosCalendario DevolverPorIdCalendario(long idCalendario);
	
	public GolesResultadosCalendario DevolverPorIdEquipo(long idEquipo);
	
	public GolesResultadosCalendario DevolverPorIdJugador(long idJugador);
	
	public GolesResultadosCalendario DevolverPorIdGol(long idGol);
	
	public GolesResultadosCalendario DevolverEnElMinuto(int min);

}
