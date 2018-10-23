/**
 * 
 */
package com.odsaprojects.prod.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;
import com.odsaprojects.prod.dao.UsuariosDao;
import com.odsaprojects.prod.entities.Usuarios;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Stateless
public class UsuariosDaoImpl implements UsuariosDao {
	
	private static final long serialVersionUID = 1L;

	private final static Logger LOG = Logger.getLogger(Usuarios.class);
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Torneo");
	EntityManager em = emf.createEntityManager();

	public UsuariosDaoImpl() {
		
	}
	
	public boolean RegistrarUsuarios(Usuarios usuarios){	
		boolean flag = true;
		try {

			em.getTransaction().begin();
			em.merge(usuarios);
			em.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			LOG.error(e.getMessage(), e);
			
			flag = false;
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public Usuarios LoginUsuarios(String email, String password){
		Usuarios result = null;
		try {
			Query query = em.createNamedQuery("Usuarios.login");
			query.setParameter("email", email);
			query.setParameter("password", password);
			List<Usuarios> usuarios = query.getResultList();
			if(usuarios.size() != 0)
				result = usuarios.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuarios> DevolverUsuarios(){
		List<Usuarios> result = null;
		try{
			Query query = em.createNamedQuery("Usuarios.findAll");
			if(query.getResultList().size() != 0)
				result = query.getResultList();
			
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;		
	}
	
	@SuppressWarnings("unchecked")
	public Usuarios BuscarUsuario(String cedula){
		Usuarios result = null;
		try {
			Query query = em.createNamedQuery("Usuarios.findByCedula");
			query.setParameter("cedula", cedula);
			List<Usuarios> usuarios = query.getResultList();
			if(usuarios.size() != 0)
					result = usuarios.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;		
	}
	
	public boolean ActualizarUsuario(Usuarios usuarios){	
		boolean flag = true;
		try {
			em.getTransaction().begin();
			em.merge(usuarios);
			em.getTransaction().commit();
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);			
			flag = false;
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public Usuarios BuscarUsuarioById(Long id) {
		Usuarios result = null;
		try {
			Query query = em.createNamedQuery("Usuarios.findById");
			query.setParameter("id", id);
			List<Usuarios> usuarios = query.getResultList();
			if(usuarios.size() != 0)
				result = usuarios.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Usuarios BuscarUsuarioByEmail(String email) {
		Usuarios result = null;
		try {
			Query query = em.createNamedQuery("Usuarios.findByEmail");
			query.setParameter("email", email);
			List<Usuarios> usuarios = query.getResultList();
			if(usuarios.size() != 0)
				result = usuarios.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}

}
