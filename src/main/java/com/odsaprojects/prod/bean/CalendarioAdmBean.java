/**
 * 
 */
package com.odsaprojects.prod.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.odsaprojects.prod.dao.CalendarioDao;
import com.odsaprojects.prod.dao.CampeonatosDao;
import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.dao.impl.CalendarioDaoImpl;
import com.odsaprojects.prod.dao.impl.CampeonatosDaoImpl;
import com.odsaprojects.prod.dao.impl.EquiposDaoImpl;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.Campeonatos;
import com.odsaprojects.prod.entities.Equipos;
import com.odsaprojects.prod.util.Constantes;
import com.odsaprojects.prod.util.EventoDTO;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@ViewScoped
public class CalendarioAdmBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(CalendarioAdmBean.class); 
	
	private Long id;
	private Long equipo1;
	private Long equipo2;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private Long campeonato;
	private int estado;
	
	private List<EventoDTO> eventoList;
	private List<Calendario> calendarioList;
	
	private int equip;
	private Map<String, Long> equips;
	private int equipV;
	private Map<String, Long> equipsV;
	
	private Boolean disabled;
	
	private long idShp;
	private String nombreShp;
	
	private CampeonatosDao daoCampeonatos;
	private EquiposDao daoEquipo;
	private CalendarioDao dao;
	
	@Inject
	SessionUtils session;

	@SuppressWarnings("rawtypes")
	public CalendarioAdmBean() {
		String aux = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		aux = (String) params.get("shp");
		this.disabled = false;
		
		dao = new CalendarioDaoImpl();		
		
		if (aux != null) {
			this.setIdShp(Long.valueOf(aux));								
			BuscarCampeonato(Long.parseLong(aux));
			
			BuscarEventosPorCampeonato(Long.parseLong(aux));
		}else {
			BuscarCalendarios();
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEquipo1() { 
		return equipo1;
	}

	public void setEquipo1(Long equipo1) {
		this.equipo1 = equipo1;
	}

	public Long getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Long equipo2) {
		this.equipo2 = equipo2;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	
	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public Long getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Long campeonato) {
		this.campeonato = campeonato;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public List<EventoDTO> getEventoList() {
		return eventoList;
	}

	public void setEventoList(List<EventoDTO> eventoList) {
		this.eventoList = eventoList;
	}

	public List<Calendario> getCalendarioList() {
		return calendarioList;
	}

	public void setCalendarioList(List<Calendario> calendarioList) {
		this.calendarioList = calendarioList;
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

	public int getEquipV() {
		return equipV;
	}

	public void setEquipV(int equipV) {
		this.equipV = equipV;
	}

	public Map<String, Long> getEquipsV() {
		return equipsV;
	}

	public void setEquipsV(Map<String, Long> equipsV) {
		this.equipsV = equipsV;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public long getIdShp() {
		return idShp;
	}

	public void setIdShp(long idShp) {
		this.idShp = idShp;
	}
	
	public String getNombreShp() {
		return nombreShp;
	}

	public void setNombreShp(String nombreShp) {
		this.nombreShp = nombreShp;
	}

	public void CrearEvento() {
		Calendario unCalendario = new Calendario();
		daoEquipo = new EquiposDaoImpl();
		dao = new CalendarioDaoImpl();
		
		if (equip == equipV) {
			session.sendErrorMessageToView(Constantes.NEWCALENDARSAMETEAM);
		} else {
			long var = equip;
			long var1 = equipV;
			Equipos equipo = daoEquipo.BuscarEquiposPorId(var);
			Equipos equipoV = daoEquipo.BuscarEquiposPorId(var1);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getFechaHoraInicio());
			calendar.add(Calendar.MINUTE, 90);
			
			unCalendario.setEquipo1(var);
			unCalendario.setEquipo2(var1);
			unCalendario.setFechaHoraInicio(getFechaHoraInicio());
			unCalendario.setFechaHoraFin(calendar.getTime());
			unCalendario.setCampeonato(getIdShp());
			unCalendario.setEstado(1);
	
			if (dao.RegistrarCalendario(unCalendario)) {
				session.sendMessageToView(Constantes.NEWCALENDARSUCCESS + equipo.getNombre() + " vs " + equipoV.getNombre());
                
			} else {
				session.sendErrorMessageToView(Constantes.NEWCALENDARERROR);
			}
		}	
	}
	
	public void EditarEvento(Long id) {
		
	}
	
	public void DeleteEvento() {
		dao = new CalendarioDaoImpl();		
		Calendario unCalendario = dao.BuscarCalendarioById(getId());
		
		unCalendario.setEstado(0);
		
		try {		
			
			if (dao.RegistrarCalendario(unCalendario)) {				
				session.sendMessageToView(Constantes.DELETECALENDARSUCCESS);
				this.disabled = true;
				
				//ActualizarCalendar();
			} else {
				session.sendErrorMessageToView(Constantes.DELETECALENDARERROR);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			session.sendErrorMessageToView(e.getMessage());
		}
	}
	
	public void BuscarCalendarios() {
		dao = new CalendarioDaoImpl();
		
		calendarioList = dao.DevolverCalendario();

	}
	
	public void BuscarEquipos() {
		daoEquipo = new EquiposDaoImpl();
		
		session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);
		
		List<Equipos> util = daoEquipo.DevolverEquipos(idUser);
		equips = new HashMap<>();
		equipsV = new HashMap<>();
		
		if (util != null) {
			for (Equipos equipos : util) {
				equips.put(equipos.getNombre() + "(" + equipos.getAbreviatura() + ")", equipos.getId());			
				equipsV.put(equipos.getNombre() + "(" + equipos.getAbreviatura() + ")", equipos.getId());
			}
		}
	}	
	
	public void ActualizarCalendar() {
		session.redirectPage("calendarioAdm.xhtml?shp="+getIdShp());
	}
	
	//Invocar Servlet pasandole un atributo - no se utiliza
	public void AddSessionShamps() {
		FacesContext context = FacesContext.getCurrentInstance();				
		try {
			
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            
            request.setAttribute("shamp", getIdShp());
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CalendarJsonServlet");
            dispatcher.forward(request, response);
            
		} catch (Exception e) {  
               e.printStackTrace();  
		} finally{  
           context.responseComplete();  
        }
	}
	
	public void BuscarCampeonato(Long idShp) {
		daoCampeonatos = new CampeonatosDaoImpl();
		
		Campeonatos unCampeonato = null;		
		unCampeonato = daoCampeonatos.BuscarCampeonatoPorId(idShp);
		
		if (unCampeonato != null) {
			setNombreShp(unCampeonato.getNombre());
		}
	}
	
	public void BuscarEventosPorCampeonato(long aux) {
		dao = new CalendarioDaoImpl();
		
		List<Calendario> util = dao.BuscarCalendarioByCampeonato(Long.valueOf(aux));
		
		List<Equipos> listUtil = DevolverlistaEquipos();
		
		EventoDTO eDto;
		
		eventoList = new ArrayList<EventoDTO>();
		
		if(util.size() != 0) {
			for (Calendario cal: util) {
				eDto = new EventoDTO();
				for (Equipos eqp: listUtil) {
					if (eqp.getId() == cal.getEquipo1()) {
						eDto.setId(cal.getId());
						eDto.setNombreEquipo1(eqp.getNombre());
						eDto.setFechaHoraInicio(cal.getFechaHoraInicio());
						eDto.setFechaHoraFin(cal.getFechaHoraFin());
					} else if (eqp.getId() == cal.getEquipo2()) {
						eDto.setId(cal.getId());
						eDto.setNombreEquipo2(eqp.getNombre());
						eDto.setFechaHoraInicio(cal.getFechaHoraInicio());
						eDto.setFechaHoraFin(cal.getFechaHoraFin());
					}
				}
				eDto.setNombreCampeonato(nombreShp);
				eventoList.add(eDto);
			}			
		}
	}
	
	public List<Equipos> DevolverlistaEquipos() {
		daoEquipo = new EquiposDaoImpl();

		session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);
		
		List<Equipos> util = daoEquipo.DevolverEquipos(idUser);
		
		return util;
	}
	
	public void LoadDeleteCalendario(Calendario cal) {
		this.id = cal.getId();
	}

}
