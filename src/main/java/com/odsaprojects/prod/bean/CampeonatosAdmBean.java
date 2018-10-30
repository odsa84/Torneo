/**
 * 
 */
package com.odsaprojects.prod.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.CampeonatosDao;
import com.odsaprojects.prod.dao.UsuariosDao;
import com.odsaprojects.prod.dao.impl.CampeonatosDaoImpl;
import com.odsaprojects.prod.dao.impl.UsuariosDaoImpl;
import com.odsaprojects.prod.entities.Campeonatos;
import com.odsaprojects.prod.entities.Usuarios;
import com.odsaprojects.prod.util.Constantes;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@ViewScoped
public class CampeonatosAdmBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(CampeonatosAdmBean.class); 
	
	private Long id;
	private String nombre;
	private int cantEquipos;
	private Date fechaInicio;
	private Date fechaFin;
	private Long idUsuarioAdmin;
	private String idCampeonato;
	private Usuarios usuario;
	
	private boolean listaCampeonatos;
	private boolean campeonatos;
	private boolean editarCampeonatos;
	
	private CampeonatosDao dao;
	private UsuariosDao daoUsuario;
	
	private List<Campeonatos> shampList;
	
	private Map<String, Long> shamps;
	
	@Inject
	SessionUtils session;

	@SuppressWarnings("rawtypes")
	public CampeonatosAdmBean() {
		campeonatos = true;
		listaCampeonatos = false;
		editarCampeonatos = false;
		
		String variable1 = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		variable1 = (String) params.get("shp");
		if(variable1 != null && variable1.equals("1")) {			
			campeonatos = true;
			listaCampeonatos = false;	
			editarCampeonatos = false;
		}else 
			if(variable1 != null && variable1.equals("0")) {
				BuscarCampeonatos();
				campeonatos = false;
				listaCampeonatos = true;
				editarCampeonatos = false;
		} else {
			campeonatos = false;
			listaCampeonatos = false;
			editarCampeonatos = true;
			}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantEquipos() {
		return cantEquipos;
	}

	public void setCantEquipos(int cantEquipos) {
		this.cantEquipos = cantEquipos;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isListaCampeonatos() {
		return listaCampeonatos;
	}

	public void setListaCampeonatos(boolean listaCampeonatos) {
		this.listaCampeonatos = listaCampeonatos;
	}

	public boolean isCampeonatos() {
		return campeonatos;
	}

	public void setCampeonatos(boolean campeonatos) {
		this.campeonatos = campeonatos;
	}

	public boolean isEditarCampeonatos() {
		return editarCampeonatos;
	}

	public void setEditarCampeonatos(boolean editarCampeonatos) {
		this.editarCampeonatos = editarCampeonatos;
	}
	
	public Map<String, Long> getShamps() {
		return shamps;
	}

	public void setShamps(Map<String, Long> shamps) {
		this.shamps = shamps;
	}
	
	public Long getIdUsuarioAdmin() {
		return idUsuarioAdmin;
	}

	public void setIdUsuarioAdmin(Long idUsuarioAdmin) {
		this.idUsuarioAdmin = idUsuarioAdmin;
	}

	public List<Campeonatos> getShampList() {
		return shampList;
	}

	public void setShampList(List<Campeonatos> shampList) {
		this.shampList = shampList;
	}

	public String getIdCampeonato() {
		return idCampeonato;
	}

	public void setIdCampeonato(String idCampeonato) {
		this.idCampeonato = idCampeonato;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	public void BuscarCampeonatos() {
		dao = new CampeonatosDaoImpl();		
		
		session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);	
		
		List<Campeonatos> util = dao.BuscarCampeonatosPorUsuario(idUser);
		shampList = util;
		shamps = new HashMap<>();
		
		if(util != null) {
			for (Campeonatos unCampeonato : util) {
				shamps.put(unCampeonato.getNombre(), unCampeonato.getId());
				
			}
		}
	}
	
	public void CrearCampeonato() {
		try {
			Campeonatos unCampeonato = new Campeonatos();
			dao = new CampeonatosDaoImpl();
			daoUsuario = new UsuariosDaoImpl();
											
			unCampeonato.setNombre(getNombre());
			unCampeonato.setCantEquipos(getCantEquipos());
			unCampeonato.setFechaInicio(getFechaInicio());
			unCampeonato.setFechaFin(getFechaFin());
			unCampeonato.setEstado(1);
			unCampeonato.setIdCampeonato(CrearIdCampeonato(getNombre()));
			
			Usuarios user = new Usuarios();
			String strVar = String.valueOf(session.get("idUsuario"));
			Long var = Long.valueOf(strVar);
			user = daoUsuario.BuscarUsuarioById(var);
			unCampeonato.setUsuario(user);
				
			if(dao.RegistrarCampeonatos(unCampeonato)) {
				session.sendMessageToView(Constantes.CREATESHPSUCCESS + getNombre());
				LimpiarCampos();			
			} else {
				session.sendErrorMessageToView(Constantes.CREATESHPERROR);
			}
		} catch (Exception e) {
			session.sendErrorMessageToView(e.getMessage());
			LOG.error(e.getMessage(), e);
		}		
	}
	
	public void EditarShamps() {
		Campeonatos unCampeonato = new Campeonatos();
		dao = new CampeonatosDaoImpl();
				
		unCampeonato.setId(this.id);
		unCampeonato.setNombre(getNombre());
		unCampeonato.setCantEquipos(getCantEquipos());
		unCampeonato.setFechaInicio(getFechaInicio());
		unCampeonato.setFechaFin(getFechaFin());
		unCampeonato.setIdCampeonato(getIdCampeonato());
		unCampeonato.setUsuario(getUsuario());
		unCampeonato.setEstado(1);
				
		try {		
			
			if (dao.RegistrarCampeonatos(unCampeonato)) {				
				session.sendMessageToView(Constantes.EDITSHPSUCCESS + getNombre());
			} else {
				session.sendErrorMessageToView(Constantes.EDITSHPERROR);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			session.sendErrorMessageToView(e.getMessage());
		}
	}
	
	/*Funcion para cargar los valores en el formulario de editar*/
	public void EditarCampeonato(Campeonatos shp) {
		setId(shp.getId());
		setNombre(shp.getNombre());
		setCantEquipos(shp.getCantEquipos());
		setFechaInicio(shp.getFechaInicio());
		setFechaFin(shp.getFechaFin());
		setIdCampeonato(shp.getIdCampeonato());
		setUsuario(shp.getUsuario());
		
		campeonatos = false;
		listaCampeonatos = false;
		editarCampeonatos = true;
	}
	
	public void DeleteCampeonato() {
		dao = new CampeonatosDaoImpl();
		Campeonatos shp = dao.BuscarCampeonatoPorId(this.id);		
		shp.setEstado(0);
		try {
			if(dao.RegistrarCampeonatos(shp)) {
				String strVar = String.valueOf(session.get("idUsuario"));
				Long idUser = Long.valueOf(strVar);	
				shampList = dao.BuscarCampeonatosPorUsuario(idUser);
				session.sendMessageToView("Eliminado " + shp.getNombre());
			} else {
				session.sendErrorMessageToView("Error al eliminar el campeonato");
			}
		} catch (Exception e) {
			session.sendErrorMessageToView(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		
	}

	public void LimpiarCampos() {
		setNombre(null);
		setCantEquipos(0);
		setFechaInicio(null);
		setFechaFin(null);
	}
	
	public String CrearIdCampeonato(String nombre) {		
		String nombreAux = nombre.replace(" ", "");
		Date fechaHoraActual = new Date();
		DateFormat formato = new SimpleDateFormat("ddmmyyyHHmmss");
		String fechaHoraFormateada = formato.format(fechaHoraActual);
		
		String result = nombreAux + fechaHoraFormateada;
		
		return result.toLowerCase();
	}
	
	public void LoadDeleteCampeonato(Campeonatos shp) {
		this.id = shp.getId();
	}
	
	public void LoadListarCampeonatos() {
		session.redirectPage("campeonatosAdm.xhtml?shp=0");
	}
	
	public void LoadCalendario(Campeonatos shp) {
		session.redirectPage("calendarioAdm.xhtml?shp="+shp.getId());
	}
	
	public void DevolverCampeonatos() {
		dao = new CampeonatosDaoImpl();
		
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);	
		
		shampList = dao.BuscarCampeonatosPorUsuario(idUser);
		
		this.campeonatos = false;
		this.listaCampeonatos = true;
	}

}
