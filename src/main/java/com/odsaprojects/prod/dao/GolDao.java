/**
 * 
 */
package com.odsaprojects.prod.dao;

import java.io.Serializable;
import java.util.List;

import com.odsaprojects.prod.entities.Gol;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public interface GolDao extends Serializable {
	
	public List<Gol> DevolverGoles();
	
	public Gol BuscarGolPorId(long id);

}
