/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Campeonatos;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface CampeonatosDao extends Serializable {
	
	public boolean RegistrarCampeonatos(Campeonatos campeonato);
	
	public List<Campeonatos> DevolverCampeonatos();
	
	public Campeonatos BuscarCampeonatoPorId(long id);
	
	public List<Campeonatos> BuscarCampeonatosPorUsuario(long idUsuario);
	
	public Campeonatos BuscarCampeonatoPorIdCampeonato(String idCampeonato);

}
