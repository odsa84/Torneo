/**
 * 
 */
package com.odsaprojects.prod.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.GolDao;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.Gol;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class GolDaoImpl implements GolDao {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(Calendario.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public GolDaoImpl() {

	}
	
	@SuppressWarnings("unchecked")
	public List<Gol> DevolverGoles(){
		List<Gol> result = null;
		
		try {
			Query query = em.createNamedQuery("Go.findAll");
			if(query.getResultList().size() != 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Gol BuscarGolPorId(long id) {
		Gol unGol = null;
		
		try {
			Query query = em.createNamedQuery("Gol.findById");
			query.setParameter("id", id);
			List<Gol> result = query.getResultList();
			if (result.size() != 0)
				unGol = result.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return unGol;		
	}

}
