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

import com.odsaprojects.prod.dao.FaltasResultadosCalendarioDao;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.FaltasResultadosCalendario;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class FaltasResultadosCalendarioDaoImpl implements FaltasResultadosCalendarioDao {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(Calendario.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public FaltasResultadosCalendarioDaoImpl() {
		
	}
	
	@SuppressWarnings("unchecked")
	public List<FaltasResultadosCalendario> DevolverTodo() {
		List<FaltasResultadosCalendario> result = null;
		
		try {
			Query query = em.createNamedQuery("FaltasResultadosCalendario.findAll");
			if(query.getResultList().size() > 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public FaltasResultadosCalendario DevolverPorId(long id) {
		FaltasResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("FaltasResultadosCalendario.findById");
			query.setParameter("id", id);
			List<FaltasResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
				
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public FaltasResultadosCalendario DevolverPorIdCalendario(long idCalendario) {
		FaltasResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("FaltasResultadosCalendario.findByIdCalendario");
			query.setParameter("idCalendario", idCalendario);
			List<FaltasResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
				
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public FaltasResultadosCalendario DevolverPorIdEquipo(long idEquipo) {
		FaltasResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("FaltasResultadosCalendario.findByIdEquipo");
			query.setParameter("idEquipo", idEquipo);
			List<FaltasResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public FaltasResultadosCalendario DevolverPorIdJugador(long idJugador) {
		FaltasResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("FaltasResultadosCalendario.findByIdJugador");
			query.setParameter("idJugador", idJugador);
			List<FaltasResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public FaltasResultadosCalendario DevolverPorIdFalta(long idFalta) {
		FaltasResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("FaltasResultadosCalendario.findByIdFalta");
			query.setParameter("idFalta", idFalta);
			List<FaltasResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public FaltasResultadosCalendario DevolverEnElMinuto(int min) {
		FaltasResultadosCalendario result = null;
		
		try {
			Query query = em.createNamedQuery("FaltasResultadosCalendario.findByMinFalta");
			query.setParameter("min", min);
			List<FaltasResultadosCalendario> aux = query.getResultList();
			if(aux.size() > 0)
				result = aux.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
}
