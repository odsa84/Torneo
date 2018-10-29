package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Equipos;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface EquiposDao extends Serializable {
	
	public boolean RegistrarEquipo(Equipos equipos);
	
	public List<Equipos> DevolverEquipos(long idUsuario);
	
	public Equipos BuscarEquiposPorNombre(String nombre);
	
	public Equipos BuscarEquiposPorId(long id);
	
	public Equipos BuscarEquiposPorIdDirector(long idDirector);
	
	public List<Equipos> DevolverEquiposNoSinEquipo(long id, long idUsuario);
	
	public void EmRollback();
	
	public void EmClose();
}
