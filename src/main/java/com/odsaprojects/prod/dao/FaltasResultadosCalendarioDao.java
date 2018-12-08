/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.FaltasResultadosCalendario;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface FaltasResultadosCalendarioDao extends Serializable {
	
	public List<FaltasResultadosCalendario> DevolverTodo();
	
	public FaltasResultadosCalendario DevolverPorId(long id);
	
	public FaltasResultadosCalendario DevolverPorIdCalendario(long idCalendario);
	
	public FaltasResultadosCalendario DevolverPorIdEquipo(long idEquipo);
	
	public FaltasResultadosCalendario DevolverPorIdJugador(long idJugador);
	
	public FaltasResultadosCalendario DevolverPorIdFalta(long idFalta);
	
	public FaltasResultadosCalendario DevolverEnElMinuto(int min);

}
