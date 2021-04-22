/**
 * 
 */
package com.odsaprojects.prod.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.entities.Directores;
import com.odsaprojects.prod.entities.Equipos;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Stateless
@Named("EquiposDaoImpl")
public class EquiposDaoImpl implements EquiposDao {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Directores.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();
	
	public EquiposDaoImpl() {
		
	}
	
	public boolean RegistrarEquipo(Equipos equipo) {
		boolean flag = true;
		
		try {
			////EntityManager em = CreateEntityManager();
			em.getTransaction().begin();
			em.persist(equipo);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);			
			flag = false;
		}
		
		return flag;
	}
	
	public boolean ActualizarEquipo(Equipos equipo) {
		boolean flag = true;
		
		try {
			//EntityManager em = CreateEntityManager();
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
	public List<Equipos> DevolverEquipos(long idUsuario) {
		List<Equipos> result = new ArrayList<Equipos>();
		
		try {
			//EntityManager em = CreateEntityManager();
			Query query = em.createNamedQuery("Equipos.findByIdUsuario");
			query.setParameter("idUsuario", idUsuario);
			if(query.getResultList().size() != 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipos> DevolverEquiposNoSinEquipo(long id, long idUsuario) {
		List<Equipos> result = new ArrayList<Equipos>();
		
		try {
			//EntityManager em = CreateEntityManager();
			Query query = em.createNamedQuery("Equipos.findAllNoSinEquipo");
			query.setParameter("id", id);
			query.setParameter("idUsuario", idUsuario);
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
			//EntityManager em = CreateEntityManager();
			Query query = em.createNamedQuery("Equipos.findByNombre");
			query.setParameter("nombre", nombre);
			List<Equipos> equipo = query.getResultList();
			if(equipo.size() != 0) 
				result = equipo.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Equipos BuscarEquiposPorId(long id) {
		Equipos result = null;
		
		try {
			//EntityManager em = CreateEntityManager();
			Query query = em.createNamedQuery("Equipos.findById");
			query.setParameter("id", id);
			List<Equipos> equipo = query.getResultList();
			if(equipo.size() != 0) 
				result = equipo.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Equipos BuscarEquiposPorIdDirector(long idDirector) {
		Equipos result = null;
		
		try {
			//EntityManager em = CreateEntityManager();
			Query query = em.createNamedQuery("Equipos.findByIdDirector");
			query.setParameter("idDirector", idDirector);
			List<Equipos> equipo = query.getResultList();
			if(equipo.size() != 0) 
				result = equipo.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	public void EmRollback() {
		//EntityManager em = CreateEntityManager();
		em.getTransaction().rollback();
		EmClose();
	}
	
	public void EmClose() {
		em.close();
	}

}
