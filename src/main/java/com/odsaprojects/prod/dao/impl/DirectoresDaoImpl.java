/**
 * 
 */
package com.odsaprojects.prod.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.DirectoresDao;
import com.odsaprojects.prod.entities.Directores;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Stateless
public class DirectoresDaoImpl implements DirectoresDao {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Directores.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public DirectoresDaoImpl() {

	}
	
	public boolean RegistrarDirector(Directores dir) {
		boolean flag = true;
		
		try {
			em.getTransaction().begin();
			em.merge(dir);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			
			flag = false;
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Directores> DevolverDirectores() {
		List<Directores> result = null;
		
		try {
			Query query = em.createNamedQuery("Directores.findAll");
			if(query.getResultList().size() != 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Directores BuscarDirector(String cedula) {
		Directores result = null;
		
		try {
			Query query = em.createNamedQuery("Directores.findByCedula");
			query.setParameter("cedula", cedula);
			List<Directores> director = query.getResultList();
			if(director.size() != 0) {
				result = director.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Directores BuscarDirectorPorId(long id) {
		Directores result = null;
		
		try {
			Query query = em.createNamedQuery("Directores.findById");
			query.setParameter("id", id);
			List<Directores> director = query.getResultList();
			if(director.size() != 0) {
				result = director.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Directores> DevolverDirSinEquipos() {
		List<Directores> result = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery("Directores.findByNoTeam");
			if(query.getResultList().size() != 0)
				result = query.getResultList();
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			
		}
				
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Directores> DevolverAlmostAll(long id) {
		List<Directores> result = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery("Directores.findAlmostAll");
			query.setParameter("id", id);
			if(query.getResultList().size() != 0)
				result = query.getResultList();
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			
		}
				
		return result;
	}

}
