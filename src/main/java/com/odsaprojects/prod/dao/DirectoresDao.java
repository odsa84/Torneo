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
	
	public List<Directores> DevolverDirectores(long idUsuario);
	
	public Directores BuscarDirector(String cedula, long idUsuario);
	
	public Directores BuscarDirectorPorId(long id, long idUsuario);
	
	public List<Directores> DevolverDirSinEquipos(long idUsuario);
	
	public List<Directores> DevolverAlmostAll(long id, long idUsuario);
	
	public List<Directores> DevolverDirPorUsuario(long idUsuario);

}
