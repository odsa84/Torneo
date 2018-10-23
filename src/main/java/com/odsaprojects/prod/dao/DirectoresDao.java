package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Directores;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface DirectoresDao extends Serializable {
	
	public boolean RegistrarDirector(Directores dir);
	
	public List<Directores> DevolverDirectores();
	
	public Directores BuscarDirector(String cedula);
	
	public Directores BuscarDirectorPorId(long id);
	
	public List<Directores> DevolverDirSinEquipos();
	
	public List<Directores> DevolverAlmostAll(long id);

}
