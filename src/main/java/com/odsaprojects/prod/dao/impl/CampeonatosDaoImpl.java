package com.odsaprojects.prod.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.CampeonatosDao;
import com.odsaprojects.prod.entities.Campeonatos;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Stateless
public class CampeonatosDaoImpl implements CampeonatosDao {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(Campeonatos.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public CampeonatosDaoImpl() {

	}

	public boolean RegistrarCampeonatos(Campeonatos campeonato) {
		boolean flag = true;
		
		try {
			em.getTransaction().begin();
			em.merge(campeonato);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			
			flag = false;
		}
		
		return flag;				
	}

	@SuppressWarnings("unchecked")
	public List<Campeonatos> DevolverCampeonatos() {
		List<Campeonatos> result = null;
		
		try {
			Query query = em.createNamedQuery("Campeonatos.findAll");
			if(query.getResultList().size() != 0)
				result = query.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public Campeonatos BuscarCampeonatoPorId(long id) {
		Campeonatos unCampeonato = null;
		
		try {
			Query query = em.createNamedQuery("Campeonatos.findById");
			query.setParameter("id", id);
			List<Campeonatos> camp = query.getResultList();
			if (camp.size() != 0) {
				unCampeonato = camp.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return unCampeonato;
	}

	@SuppressWarnings("unchecked")
	public List<Campeonatos> BuscarCampeonatosPorUsuario(long idUsuario) {
		List<Campeonatos> result = null;
		
		try {
			Query query = em.createNamedQuery("Campeonatos.findByUsuario");
			query.setParameter("idUsuario", idUsuario);
			if (query.getResultList().size() != 0) {
				result = query.getResultList();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Campeonatos BuscarCampeonatoPorIdCampeonato(String idCampeonato) {
		Campeonatos unCampeonato = null;
		
		try {
			Query query = em.createNamedQuery("Campeonatos.findByIdCampeonato");
			query.setParameter("idCampeonato", idCampeonato);
			List<Campeonatos> camp = query.getResultList();
			if (camp.size() != 0) {
				unCampeonato = camp.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return unCampeonato;
	}

}
