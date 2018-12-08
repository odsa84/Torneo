/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Faltas;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface FaltasDao extends Serializable {
	
	public List<Faltas> DevolverFaltas();
	
	public Faltas DevolverfaltaPorId(long id);

}
