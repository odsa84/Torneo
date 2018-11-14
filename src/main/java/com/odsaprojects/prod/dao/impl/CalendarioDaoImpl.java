/**
 * 
 */
package com.odsaprojects.prod.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.CalendarioDao;
import com.odsaprojects.prod.entities.Calendario;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class CalendarioDaoImpl implements CalendarioDao {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(Calendario.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public CalendarioDaoImpl() {
		
	}
	
	public boolean RegistrarCalendario(Calendario calendario) {
		boolean flag = true;
		
		try {
			em.getTransaction().begin();
			em.merge(calendario);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			
			flag = false;
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Calendario> DevolverCalendario() {
		List<Calendario> result = null;
		
		try {
			Query query = em.createNamedQuery("calendario.findAll");
			if(query.getResultList().size() != 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Calendario BuscarCalendarioById(long id) {
		Calendario unCalendario = null;
		
		try {
			Query query = em.createNamedQuery("calendario.findById");
			query.setParameter("id", id);
			List<Calendario> camp = query.getResultList();
			if (camp.size() != 0) {
				unCalendario = camp.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return unCalendario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Calendario> BuscarCalendarioByCampeonato(long idCampeonato){
		List<Calendario> result = new ArrayList<Calendario>();
		
		try {
			Query query = em.createNamedQuery("calendario.findByIdCampeonato");
			query.setParameter("idCampeonato", idCampeonato);
			if (query.getResultList().size() != 0) {
				result = query.getResultList();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Calendario> BuscarEventosPorFecha(int anio, int mes) {
		List<Calendario> result = new ArrayList<Calendario>();
		
		try {
			Query query = em.createNamedQuery("calendario.findByMonthAndYear");
			query.setParameter("mes", mes);
			query.setParameter("anio", anio);
			if (query.getResultList().size() != 0) {
				result = query.getResultList();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}

}
