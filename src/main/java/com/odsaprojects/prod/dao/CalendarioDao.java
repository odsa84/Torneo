/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Calendario;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface CalendarioDao extends Serializable {
	
	public boolean RegistrarCalendario(Calendario calendario);
	
	public boolean ActualizarEstadoEnCeroListaCalendario(List<Calendario> calList);
	
	public List<Calendario> DevolverCalendario();
	
	public Calendario BuscarCalendarioById(long id);
	
	public List<Calendario> BuscarCalendarioByCampeonato(long idCampeonato);
	
	public List<Calendario> BuscarEventosPorFecha(int anio, int mes);
	
	public List<Calendario> BuscarCalendarioByEquipo(long idEqp);

}
