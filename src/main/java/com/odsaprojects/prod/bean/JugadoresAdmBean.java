package com.odsaprojects.prod.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.dao.JugadoresDao;
import com.odsaprojects.prod.dao.impl.EquiposDaoImpl;
import com.odsaprojects.prod.dao.impl.JugadoresDaoImpl;
import com.odsaprojects.prod.entities.Equipos;
import com.odsaprojects.prod.entities.Jugadores;
import com.odsaprojects.prod.util.SessionUtils;
import com.odsaprojects.prod.util.Constantes;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@ViewScoped
public class JugadoresAdmBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nombres;
	private String apellidos;
	private String cedula;
	private int edad;
	private String camiseta;
	
	private boolean jugador;	
	private boolean listaJugadores;
	private boolean editarJugador;
	
	private JugadoresDao dao;
	private EquiposDao daoEquipo;
	private List<Jugadores> jugList;
	private List<Equipos> equipList;
	
	private int equip;
	private Map<String, Long> equips;
	
	@Inject
	SessionUtils session;

	@SuppressWarnings("rawtypes")
	public JugadoresAdmBean() {
		jugador = true;
		listaJugadores = false;
		
		String variable1 = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		variable1 = (String) params.get("ply");
		BuscarEquipos();
		if(variable1 != null && variable1.equals("1")) {			
			jugador = true;
			listaJugadores = false;			
		}else 
			if(variable1 != null && variable1.equals("0")) {
				BuscarJugadores();
				jugador = false;
				listaJugadores = true;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCamiseta() {
		return camiseta;
	}

	public void setCamiseta(String camiseta) {
		this.camiseta = camiseta;
	}

	public boolean isJugador() {
		return jugador;
	}

	public void setJugador(boolean jugador) {
		this.jugador = jugador;
	}

	public boolean isListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(boolean listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

	public JugadoresDao getDao() {
		return dao;
	}

	public void setDao(JugadoresDao dao) {
		this.dao = dao;
	}

	public EquiposDao getDaoEquipo() {
		return daoEquipo;
	}

	public void setDaoEquipo(EquiposDao daoEquipo) {
		this.daoEquipo = daoEquipo;
	}

	public List<Jugadores> getJugList() {
		return jugList;
	}

	public void setJugList(List<Jugadores> jugList) {
		this.jugList = jugList;
	}

	public List<Equipos> getEquipList() {
		return equipList;
	}

	public void setEquipList(List<Equipos> equipList) {
		this.equipList = equipList;
	}

	public int getEquip() {
		return equip;
	}

	public void setEquip(int equip) {
		this.equip = equip;
	}

	public Map<String, Long> getEquips() {
		return equips;
	}

	public void setEquips(Map<String, Long> equips) {
		this.equips = equips;
	}
	
	public boolean isEditarJugador() {
		return editarJugador;
	}

	public void setEditarJugador(boolean editarJugador) {
		this.editarJugador = editarJugador;
	}

	public void BuscarJugadores() {
		dao = new JugadoresDaoImpl();
		
		jugList = dao.DevolverJugadores();
	}
	
	public void BuscarEquipos() {
		daoEquipo = new EquiposDaoImpl();
		dao = new JugadoresDaoImpl();
		
		List<Equipos> util = daoEquipo.DevolverEquipos();
		equips = new HashMap<>();
		
		for (Equipos equipos : util) {
			List<Jugadores> players = dao.BuscarJugadoresPorEquipos(equipos.getId());
			if(players.size() >= 0 && players.size() < Constantes.PLYBYTEAM) {
				equips.put(equipos.getNombre() + "(" + equipos.getAbreviatura() + ")", equipos.getId());
			}			
		}
	}
	
	public void Crearjugador() {

		Jugadores unJugador = new Jugadores();
		daoEquipo = new EquiposDaoImpl();
		dao = new JugadoresDaoImpl();

		unJugador.setNombres(getNombres());
		unJugador.setApellidos(getApellidos());
		unJugador.setCedula(getCedula());
		unJugador.setEdad(getEdad());
		unJugador.setCamiseta(getCamiseta());
		unJugador.setEstado(1);

		long var = equip;
		Equipos equipo = daoEquipo.BuscarEquiposPorId(var);
		unJugador.setEquipos(equipo);

		if (dao.RegistrarJugador(unJugador)) {
			session.sendMessageToView(Constantes.NEWPLYOK + getNombres() + " " + getApellidos());
			BuscarEquipos();
			LimpiarCampos();
		} else {
			session.sendErrorMessageToView(Constantes.NEWPLYERROR);
		}
	}
	
	public void EditarJugador() {
		
		Jugadores unJugador = new Jugadores();
		daoEquipo = new EquiposDaoImpl();
		dao = new JugadoresDaoImpl();
		
		unJugador.setId(this.id);
		unJugador.setNombres(getNombres());
		unJugador.setApellidos(getApellidos());
		unJugador.setCedula(getCedula());
		unJugador.setCamiseta(getCamiseta());
		unJugador.setEdad(getEdad());
		unJugador.setEstado(1);
		
		try {
			Equipos eqp = daoEquipo.BuscarEquiposPorId(this.equip);
			unJugador.setEquipos(eqp);
			
			if(dao.RegistrarJugador(unJugador))
				session.sendMessageToView(Constantes.EDITPLAYERSUCCESS + unJugador.getNombres() + " " + unJugador.getApellidos());
			else
				session.sendErrorMessageToView(Constantes.EDITPLAYERSERROR);
		} catch (Exception e) {
			session.sendErrorMessageToView(e.getMessage());
		}
		
	}
	
	public void DeleteJugador() {
		dao = new JugadoresDaoImpl();
		Jugadores ply = dao.BuscarJugadorPorId(this.id);
		ply.setEstado(0);
		try {
			if(dao.RegistrarJugador(ply)) {
				jugList = dao.DevolverJugadores();
				session.sendMessageToView("Eliminado " + ply.getNombres() + " " + ply.getApellidos());
			} else {
				session.sendErrorMessageToView(Constantes.DELETEPLAYERSERROR);
			}			
		} catch (Exception e) {
			session.sendErrorMessageToView(e.getMessage());
		}
	}
	
	public void LoadEditarJugador(Jugadores ply) {
		setId(ply.getId());
		setNombres(ply.getNombres());
		setApellidos(ply.getApellidos());
		setCedula(ply.getCedula());
		setEdad(ply.getEdad());
		setCamiseta(ply.getCamiseta());
		
		equips.put(ply.getEquipos().getNombre(), ply.getEquipos().getId());
		//Pongo al equipo del jugador como seleccionado en el combobox
		equip = ply.getEquipos().getId().intValue();	
		
		jugador = false;
		listaJugadores = false;
		editarJugador = true;
	}
	
	public void LoadDeleteJugador(Jugadores ply) {
		this.id = ply.getId();
	}
	
	public void LimpiarCampos() {
		
		setNombres(null);
		setApellidos(null);
		setCedula(null);
		setEdad(0);
		setCamiseta(null);
	}

}
