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

import com.odsaprojects.prod.dao.JugadoresDao;
import com.odsaprojects.prod.entities.Directores;
import com.odsaprojects.prod.entities.Jugadores;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class JugadoresDaoImpl implements JugadoresDao {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Directores.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public JugadoresDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean RegistrarJugador(Jugadores jugador) {
		boolean flag = true;
		
		try {
			em.getTransaction().begin();
			em.merge(jugador);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);			
			flag = false;
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Jugadores> DevolverJugadores(long idUsuario) {
		List<Jugadores> result = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery("Jugadores.findByIdUsuario");
			query.setParameter("idUsuario", idUsuario);
			if(query.getResultList().size() != 0) {
				result = query.getResultList();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Jugadores> BuscarJugadoresPorEquipos(Long equipo) {
		List<Jugadores> result = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery("Jugadores.findByEquipo");
			query.setParameter("equipo", equipo);
			if(query.getResultList().size() != 0) {
				result = query.getResultList();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Jugadores BuscarJugadorPorCedula(String cedula) {
		Jugadores result = null;
		
		try {
			Query query = em.createNamedQuery("Jugadores.findByCedula");
			query.setParameter("cedula", cedula);
			List<Jugadores> jugador = query.getResultList();
			if(jugador.size() != 0) {
				result = jugador.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Jugadores BuscarJugadorPorId(long id) {
		Jugadores result = null;
		
		try {
			Query query = em.createNamedQuery("Jugadores.findById");
			query.setParameter("id", id);
			List<Jugadores> jugador = query.getResultList();
			if(jugador.size() != 0) {
				result = jugador.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}

}
