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

import com.odsaprojects.prod.dao.FaltasDao;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.Faltas;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class FaltasDaoImpl implements FaltasDao {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(Calendario.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public FaltasDaoImpl() {
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Faltas> DevolverFaltas() {
		List<Faltas> result = null;
		
		try {
			Query query = em.createNamedQuery("Faltas.findAll");
			if(query.getResultList().size() > 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Faltas DevolverfaltaPorId(long id) {
		Faltas unaFalta = null;
		
		try {
			Query query = em.createNamedQuery("Faltas.findById");
			query.setParameter("id", id);
			List<Faltas> result = query.getResultList();
			if(result.size() > 0)
				unaFalta = result.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return unaFalta;
	}

}
