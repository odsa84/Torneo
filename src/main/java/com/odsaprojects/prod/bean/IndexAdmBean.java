package com.odsaprojects.prod.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.odsaprojects.prod.dao.DirectoresDao;
import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.dao.impl.DirectoresDaoImpl;
import com.odsaprojects.prod.dao.impl.EquiposDaoImpl;
import com.odsaprojects.prod.entities.Directores;
import com.odsaprojects.prod.entities.Equipos;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@RequestScoped
public class IndexAdmBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nombres;
	private String apellidos;
	private String email;
	private String cedula;
	private String celular;
	
	private String nombreEquipo;
	private String abreviatura;
	private String logo;
	
	private boolean director;
	private boolean jugador;
	private boolean equipo;
	private boolean campeonato;
	
	private boolean listaDirectores;
	private boolean listaJugadores;
	private boolean listaEquipos;
	private boolean listaCampeonatos;
	
	private DirectoresDao dao;
	private List<Directores> dirList;
	private List<Equipos> equipList;
	
	@NotNull
	private int dir;
	private Map<String, Long> dirs;
	
	@Inject
	SessionUtils session;
	
	@ManagedProperty(value="#{equiposAdmBean}")
	private EquiposAdmBean eab;

	public IndexAdmBean() {
		
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public boolean isDirector() {
		return director;
	}

	public void setDirector(boolean director) {
		this.director = director;
	}

	public boolean isJugador() {
		return jugador;
	}

	public void setJugador(boolean jugador) {
		this.jugador = jugador;
	}

	public boolean isCampeonato() {
		return campeonato;
	}

	public void setCampeonato(boolean campeonato) {
		this.campeonato = campeonato;
	}

	public boolean isEquipo() {
		return equipo;
	}

	public void setEquipo(boolean equipo) {
		this.equipo = equipo;
	}
	
	public boolean isListaDirectores() {
		return listaDirectores;
	}

	public void setListaDirectores(boolean listaDirectores) {
		this.listaDirectores = listaDirectores;
	}

	public boolean isListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(boolean listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

	public boolean isListaEquipos() {
		return listaEquipos;
	}

	public void setListaEquipos(boolean listaEquipos) {
		this.listaEquipos = listaEquipos;
	}

	public boolean isListaCampeonatos() {
		return listaCampeonatos;
	}

	public void setListaCampeonatos(boolean listaCampeonatos) {
		this.listaCampeonatos = listaCampeonatos;
	}

	public List<Directores> getDirList() {
		return dirList;
	}

	public void setDirList(List<Directores> dirList) {
		this.dirList = dirList;
	}

	public List<Equipos> getEquipList() {
		return equipList;
	}

	public void setEquipList(List<Equipos> equipList) {
		this.equipList = equipList;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public Map<String, Long> getDirs() {
		return dirs;
	}

	public void setDirs(Map<String, Long> dirs) {
		this.dirs = dirs;
	}

	public EquiposAdmBean getEab() {
		return eab;
	}

	public void setEab(EquiposAdmBean eab) {
		this.eab = eab;
	}

	public void CrearDirector() {
		Directores undirector = new Directores();
		dao = new DirectoresDaoImpl();
		
		undirector.setNombres(getNombres());
		undirector.setApellidos(getApellidos());
		undirector.setEmail(getEmail());
		undirector.setCedula(getCedula());
		undirector.setCelular(getCelular());
		undirector.setEstado(1);
		
		if(dao.RegistrarDirector(undirector)) {
			session.sendMessageToView("Creado el director " + getNombres() + " " + getApellidos());
			LimpiarCampos();			
		} else {
			session.sendMessageToView("Error al crear el Director");
		}
	}
	
	public void CrearEquipo() {
		Equipos unEquipo = new Equipos();		
		EquiposDao daoEquipo = new EquiposDaoImpl();
		dao = new DirectoresDaoImpl();
		
		unEquipo.setNombre(getNombreEquipo());
		unEquipo.setAbreviatura(getAbreviatura());
		unEquipo.setLogo(getLogo());
		
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);
		
		Directores dirEquipo = dao.BuscarDirectorPorId(getDir(), idUser);		
		unEquipo.setDirectores(dirEquipo);
		
		if(daoEquipo.RegistrarEquipo(unEquipo)) {
			session.sendMessageToView("Creado el Equipo " + getNombreEquipo());
			LimpiarCamposEquipo();
		}else {
			session.sendMessageToView("Error al crear el Equipo");
		}
	}
	
	public void DevolverJugadores() {
		session.redirectPage("jugadoresAdm.xhtml?ply=0");
	}
	
	public void DevolverDirectores() {
		session.redirectPage("directoresAdm.xhtml?mng=0");
	}
	
	public void DevolverEquipos() {
		session.redirectPage("equiposAdm.xhtml?eqp=0");
	}
	
	public void DevolverCampeonatos() {
		session.redirectPage("campeonatosAdm.xhtml?shp=0");
	}
	
	public String CrearJugador() {
		String flag = "Ok";
		
		return flag;
	}
	
	public void LimpiarCampos() {
		setNombres(null);
		setApellidos(null);
		setCedula(null);
		setEmail(null);
		setCelular(null);
	}
	
	public void LimpiarCamposEquipo() {
		setNombreEquipo(null);
		setAbreviatura(null);
		setLogo(null);
	}
	
	public void SeleccionarJugador() {
		session.redirectPage("jugadoresAdm.xhtml?ply=1");
	}
	
	public void SeleccionarDirector() {
		session.redirectPage("directoresAdm.xhtml?mng=1");
	}
	
	public void SeleccionarEquipo() {		
		session.redirectPage("equiposAdm.xhtml?eqp=1");
	}
	
	public void SeleccionarCampeonato() {
		session.redirectPage("campeonatosAdm.xhtml?shp=1");
	}
	
	public void ListaDirectores() {
		this.director = false;
		this.jugador = false;
		this.equipo = false;
		this.campeonato = false;
		this.listaDirectores = true;
		this.listaEquipos = false;
		this.listaJugadores = false;
		this.listaCampeonatos = false;
	}
	
	public void ListaEquipos() {
		this.director = false;
		this.jugador = false;
		this.equipo = false;
		this.campeonato = false;
		this.listaDirectores = false;
		this.listaEquipos = true;
		this.listaJugadores = false;
		this.listaCampeonatos = false;
	}
	
	public void ListaJugadores() {
		this.director = false;
		this.jugador = false;
		this.equipo = false;
		this.campeonato = false;
		this.listaDirectores = false;
		this.listaEquipos = false;
		this.listaJugadores = true;
		this.listaCampeonatos = false;
	}
	
	public void ListaCampeonatos() {
		this.director = false;
		this.jugador = false;
		this.equipo = false;
		this.campeonato = false;
		this.listaDirectores = false;
		this.listaEquipos = false;
		this.listaJugadores = false;
		this.listaCampeonatos = true;
	}

}
