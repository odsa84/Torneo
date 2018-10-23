/**
 * 
 */
package com.odsaprojects.prod.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.entities.Directores;
import com.odsaprojects.prod.entities.Equipos;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Stateless
public class EquiposDaoImpl implements EquiposDao {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Directores.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	/**
	 * 
	 */
	public EquiposDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean RegistrarEquipo(Equipos equipo) {
		boolean flag = true;
		
		try {
			em.getTransaction().begin();
			em.merge(equipo);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);			
			flag = false;
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipos> DevolverEquipos() {
		List<Equipos> result = null;
		
		try {
			Query query = em.createNamedQuery("Equipos.findAll");			
			if(query.getResultList().size() != 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipos> DevolverEquiposNoSinEquipo(long id) {
		List<Equipos> result = null;
		
		try {
			Query query = em.createNamedQuery("Equipos.findAllNoSinEquipo");
			query.setParameter("id", id);
			if(query.getResultList().size() != 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Equipos BuscarEquiposPorNombre(String nombre) {
		Equipos result = null;
		
		try {
			Query query = em.createNamedQuery("Equipos.findByNombre");
			query.setParameter("nombre", nombre);
			List<Equipos> equipo = query.getResultList();
			if(equipo.size() != 0) {
				result = equipo.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Equipos BuscarEquiposPorId(long id) {
		Equipos result = null;
		
		try {
			Query query = em.createNamedQuery("Equipos.findById");
			query.setParameter("id", id);
			List<Equipos> equipo = query.getResultList();
			if(equipo.size() != 0) {
				result = equipo.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Equipos BuscarEquiposPorIdDirector(long idDirector) {
		Equipos result = null;
		
		try {
			Query query = em.createNamedQuery("Equipos.findByIdDirector");
			query.setParameter("idDirector", idDirector);
			List<Equipos> equipo = query.getResultList();
			if(equipo.size() != 0) {
				result = equipo.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	public void EmRollback() {
		em.getTransaction().rollback();
		EmClose();
	}
	
	public void EmClose() {
		em.close();
	}

}
