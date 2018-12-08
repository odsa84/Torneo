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

import com.odsaprojects.prod.dao.GolesResultadosCalendarioDao;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.GolesResultadosCalendario;

/**
 * @author Osvaldo
 *
 */
public class GolesResultadosCalendarioDaoImpl implements GolesResultadosCalendarioDao {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(Calendario.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();
	
	public GolesResultadosCalendarioDaoImpl() {
		
	}
	
	@SuppressWarnings("unchecked")
	public List<GolesResultadosCalendario> DevolverTodo() {
		List<GolesResultadosCalendario> result = null;
		
		try {
			Query query = em.createNamedQuery("GolesResultadosCalendario.findAll");
			if(query.getResultList().size() > 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public GolesResultadosCalendario DevolverPorId(long id) {
		GolesResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("GolesResultadosCalendario.findById");
			query.setParameter("id", id);
			List<GolesResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
				
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public GolesResultadosCalendario DevolverPorIdCalendario(long idCalendario) {
		GolesResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("GolesResultadosCalendario.findByIdCalendario");
			query.setParameter("idCalendario", idCalendario);
			List<GolesResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
				
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public GolesResultadosCalendario DevolverPorIdEquipo(long idEquipo) {
		GolesResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("GolesResultadosCalendario.findByIdEquipo");
			query.setParameter("idEquipo", idEquipo);
			List<GolesResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public GolesResultadosCalendario DevolverPorIdJugador(long idJugador) {
		GolesResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("GolesResultadosCalendario.findByIdJugador");
			query.setParameter("idJugador", idJugador);
			List<GolesResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public GolesResultadosCalendario DevolverPorIdGol(long idGol) {
		GolesResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("GolesResultadosCalendario.findByIdGol");
			query.setParameter("idGol", idGol);
			List<GolesResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public GolesResultadosCalendario DevolverEnElMinuto(int min) {
		GolesResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("GolesResultadosCalendario.findByMinGol");
			query.setParameter("min", min);
			List<GolesResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}

}
