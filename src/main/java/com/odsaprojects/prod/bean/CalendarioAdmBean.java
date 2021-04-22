/**
 * 
 */
package com.odsaprojects.prod.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.el.ValueExpression;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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
import com.odsaprojects.prod.dao.JugadoresDao;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.Campeonatos;
import com.odsaprojects.prod.entities.Equipos;
import com.odsaprojects.prod.entities.Jugadores;
import com.odsaprojects.prod.util.CalendarioDTO;
import com.odsaprojects.prod.util.Constantes;
import com.odsaprojects.prod.util.Cronometro;
import com.odsaprojects.prod.util.EventoDTO;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@ViewScoped
public class CalendarioAdmBean implements Serializable, Observer {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(CalendarioAdmBean.class); 
	
	private Long id;
	private Long equipo1;
	private Long equipo2;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private Long campeonato;
	private int estado;
	private Date lastDateTime;
	private String nombreEquipo1;
	private String nombreEquipo2;
	private String nombreCampeonato;
	
	private List<EventoDTO> eventoList;
	private List<CalendarioDTO> calendarioList;
	
	private int equip;
	private Map<String, Long> equips;
	private int equipV;
	private Map<String, Long> equipsV;
	
	private Boolean disabled;
	
	private long idShp;
	private String nombreShp;
	
	private boolean editarCalendario;
	private boolean listarCalendario;
	private boolean calendario;
	private boolean jugarPartido;
	
	private List<Jugadores> jugadores;
	private List<Jugadores> jugadores1;
	private List<Jugadores> selectedJugadores;
	private List<Jugadores> selectedJugadores1;
	
	private String labelCrono;
	
	/*Interfaces inyectadas via constructor*/
	private CampeonatosDao daoCampeonatos;
	private EquiposDao daoEquipo;
	private CalendarioDao dao;
	private JugadoresDao daoJugadores;
	
	private SessionUtils session;
	
	
	@Inject
	@SuppressWarnings("rawtypes")
	public CalendarioAdmBean(EquiposDao daoEquipo, CampeonatosDao daoCampeonatos, CalendarioDao dao, SessionUtils session, 
			JugadoresDao daoJugadores) {
		this.daoEquipo = daoEquipo;
		this.daoCampeonatos = daoCampeonatos;
		this.dao = dao;
		this.session = session;
		this.daoJugadores = daoJugadores;
		
		this.listarCalendario = true;
		this.calendario = true;
		this.jugarPartido = false;
		
		String shp = null;
		String event = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		shp = (String) params.get("shp");
		event = (String) params.get("event");
		this.disabled = false;
		
		if (event != null) {
			BuscarEquiposYJugadoresParaJugar(Long.parseLong(event));
		}
		
		if (shp != null) {
			this.FillEquipos();
			this.setIdShp(Long.valueOf(shp));								
			BuscarCampeonato(Long.parseLong(shp));
			
			BuscarEventosPorCampeonato(Long.parseLong(shp));
		}else {
			BuscarCalendarios();
		}
	}
	
	public void CrearEvento() {
		Calendario unCalendario = new Calendario();
		//daoEquipo = new EquiposDaoImpl();
		//dao = new CalendarioDaoImpl();
		
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
	
	public void EditarEvento() {
		Calendario unCalendario = TransformCalendarioDTOToCalendario(1);
		
		try {
			
			if(dao.RegistrarCalendario(unCalendario))
				session.sendMessageToView(Constantes.EDITCALENDARSUCCESS);
			else
				session.sendErrorMessageToView(Constantes.EDITCALENDARERROR);
		} catch (Exception e) {
			session.sendErrorMessageToView(e.getMessage());
		}
	}
	
	public void LoadEditarEvento(CalendarioDTO caldto) {
		this.setId(caldto.getId());
		this.setEquipo1(caldto.getEquipo1());
		this.setNombreEquipo1(caldto.getNombreEquipo1());
		this.setEquipo2(caldto.getEquipo2());
		this.setNombreEquipo2(caldto.getNombreEquipo2());
		this.setFechaHoraInicio(caldto.getFechaHoraInicio());
		this.setFechaHoraFin(caldto.getFechaHoraFin());
		this.setCampeonato(caldto.getCampeonatoId());
		this.setEstado(caldto.getEstado());
		
		this.listarCalendario = false;
		this.editarCalendario = true;
	}
	
	public void LoadListarEventos() {
		session.redirectPage("eventosAdm.xhtml");
	}
	
	public void LoadCalendario(Long idShp) {
		session.redirectPage("calendarioAdm.xhtml?shp=" + idShp);
	}
	
	public void DeleteEvento() {
		Calendario unCalendario = dao.BuscarCalendarioById(getId());
		
		unCalendario.setEstado(0);
		
		try {		
			
			if (dao.RegistrarCalendario(unCalendario)) {	
				this.BuscarCalendarios();
				session.sendMessageToView(Constantes.DELETECALENDARSUCCESS);
				this.disabled = true;
				
			} else {
				session.sendErrorMessageToView(Constantes.DELETECALENDARERROR);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			session.sendErrorMessageToView(e.getMessage());
		}
	}
	
	public void JugarPartido() {
		session.redirectPage("calendarioAdm.xhtml?shp="+getIdShp()+"&event="+getId());

		/*FacesContext.getCurrentInstance().getExternalContext().getFlash().put("equipo1", unCalendario.getEquipo1());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("equipo2", unCalendario.getEquipo2());
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("jugarPartidoAdm.xhtml?jp=1");*/
	}
	
	public void BuscarEquiposYJugadoresParaJugar(Long shpId) {
		Calendario unCalendario = dao.BuscarCalendarioById(shpId);
		this.setEquipo1(unCalendario.getEquipo1());
		this.setEquipo2(unCalendario.getEquipo2());
		
		this.setJugadores(daoJugadores.BuscarJugadoresPorEquipos(equipo1));
		this.setJugadores1(daoJugadores.BuscarJugadoresPorEquipos(equipo2));
		
		calendario = false;
		jugarPartido = true;
	}
	
	public void BuscarCalendarios() {
		List<Calendario> cal = dao.DevolverCalendario();
		
		calendarioList = new ArrayList<CalendarioDTO>();
		Equipos eqp;
		Campeonatos shp;
		List<Equipos> equipoLst = this.DevolverlistaEquipos();
		List<Campeonatos> shpLst = this.BuscarCampeonatos();
		
		boolean flag = true;
		
		for (Calendario  c : cal) {
			CalendarioDTO calendario = new CalendarioDTO();
			calendario.setId(c.getId());
			eqp = equipoLst.stream()
			.filter(e -> e.getId() == c.getEquipo1())
			.findAny()
			.orElse(null);
			if(eqp != null) {
				calendario.setEquipo1(eqp.getId());
				calendario.setNombreEquipo1(eqp.getNombre());
			} else {
				flag = false;
			}
			eqp = equipoLst.stream()
					.filter(e -> e.getId() == c.getEquipo2())
					.findAny()
					.orElse(null);
			if(eqp != null) {
				calendario.setEquipo2(eqp.getId());
				calendario.setNombreEquipo2(eqp.getNombre());
			} else {
				flag = false;
			}
			calendario.setFechaHoraInicio(c.getFechaHoraInicio());
			calendario.setFechaHoraFin(c.getFechaHoraFin());
			shp = shpLst.stream()
					.filter(sp -> sp.getId() == c.getCampeonato())
					.findAny()
					.orElse(null);
			if(shp != null) {
				calendario.setCampeonatoId(shp.getId());
				calendario.setNombreCampeonato(shp.getNombre());
			} else {
				flag = false;
			}
			calendario.setEstado(c.getEstado());
			
			if (flag)
				calendarioList.add(calendario);
			
			flag = true;
		}
	}	
	
	public void FillEquipos() {
		//daoEquipo = new EquiposDaoImpl();
		
		//session = new SessionUtils();
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
		session.redirectPage("calendarioAdm.xhtml?shp="+getIdShp()+"&event");
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
	
	public List<Campeonatos> BuscarCampeonatos() {
		String strVar = String.valueOf(session.get("idUsuario"));
		long idUser = Long.valueOf(strVar);
		
		List<Campeonatos> util = daoCampeonatos.BuscarCampeonatosPorUsuario(idUser);
		
		return util;
	}
	
	public void BuscarCampeonato(Long idShp) {
		//daoCampeonatos = new CampeonatosDaoImpl();
		
		Campeonatos unCampeonato = null;		
		unCampeonato = daoCampeonatos.BuscarCampeonatoPorId(idShp);
		
		if (unCampeonato != null) {
			setNombreShp(unCampeonato.getNombre());
		}
	}
	
	public void BuscarEventosPorCampeonato(long aux) {
		//dao = new CalendarioDaoImpl();
		
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
			
			ObtenerUltimaFechaHora(eventoList);
		}
	}
	
	public void ObtenerUltimaFechaHora(List<EventoDTO> lst) {
		Calendar myCalendar = new GregorianCalendar(1900, 1, 1);
		Date lastDate = myCalendar.getTime();
		
		for (EventoDTO eDto: lst) {
			if (eDto.getFechaHoraInicio() != null && eDto.getFechaHoraInicio().compareTo(lastDate) > 0) {
				lastDate = eDto.getFechaHoraInicio();
			}
		}
		
		this.lastDateTime = lastDate;
	}
	
	public List<Equipos> DevolverlistaEquipos() {
		//daoEquipo = new EquiposDaoImpl();

		//session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);
		
		List<Equipos> util = daoEquipo.DevolverEquipos(idUser);
		
		return util;
	}
	
	public Calendario TransformCalendarioDTOToCalendario(int estado) {
		Calendario unCalendario = new Calendario();
		
		unCalendario.setId(this.getId());
		unCalendario.setEquipo1(this.getEquipo1());
		unCalendario.setEquipo2(this.getEquipo2());		
		unCalendario.setFechaHoraInicio(this.getFechaHoraInicio());
		unCalendario.setFechaHoraFin(this.getFechaHoraFin());
		unCalendario.setCampeonato(this.getCampeonato());
		unCalendario.setEstado(estado);
		
		return unCalendario;
	}
	
	public void JugadorSeleccionado() {
		Jugadores jug = selectedJugadores.get(0);
		System.out.println("Jugador: " + jug.getNombres());
	}
	
	public void InicializarCronometro(ComponentSystemEvent e) {
		HtmlForm form = (HtmlForm) e.getComponent();
		
		UIInput input = new HtmlInputText();
		//UIComponent comp = new HtmlOutputLabel();
		
		input.setId("cronoInput"); // Must be unique!
		input.setValueExpression("value", createValueExpression("#{jugarPartidoAdmBean.labelCrono}", String.class));
		//comp.setId("labelId");
		//comp.setValueExpression("value", createValueExpression("#{jugarPartidoAdmBean.labelCrono}", String.class));
		//input.resetValue();
        form.getChildren().add(input);
        //form.getChildren().add(comp);
        //form.getChildren().remove(input);
	}
	
	private ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return context.getApplication().getExpressionFactory()
	        .createValueExpression(context.getELContext(), valueExpression, valueType);
	}
	
	public void ComensarPartido() {
		
		Cronometro crono = new Cronometro(0, 0);
		crono.addObserver(this);
		Thread t = new Thread(crono);
		t.start();
		
		/*System.setProperty("java.awt.headless", "false");
		JFrame f = new JFrame("Basic GUI"); // create Frame
	   int pressed = 0; // tracks number of button presses.
	   JLabel label1 = new JLabel("You have pressed button " + pressed + "times.");
	   JButton start = new JButton("Click To Start!");
	   
	   f.getContentPane().setLayout(new GridLayout(0, 1));
	      start.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 int pressed = 0;
	        	 pressed++;
	        	 label1.setText("You have pressed button " + pressed + "times.");
	         }
	      });
	      // Add components
	      f.add(label1);
	      f.add(start);
	      // Allows the Swing App to be closed
	      f.addWindowListener(new ListenCloseWdw());*/
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.setLabelCrono((String) arg);
		//InicializarCronometro(new HtmlInputText());
		System.out.println(this.getLabelCrono());
		
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

	public Date getLastDateTime() {
		return lastDateTime;
	}

	public void setLastDateTime(Date lastDateTime) {
		this.lastDateTime = lastDateTime;
	}

	public List<EventoDTO> getEventoList() {
		return eventoList;
	}

	public void setEventoList(List<EventoDTO> eventoList) {
		this.eventoList = eventoList;
	}

	public List<CalendarioDTO> getCalendarioList() {
		return calendarioList;
	}

	public void setCalendarioList(List<CalendarioDTO> calendarioList) {
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
	
	public void LoadDeleteCalendario(long id) {
		this.id = id;
	}

	public String getNombreEquipo1() {
		return nombreEquipo1;
	}

	public void setNombreEquipo1(String nombreEquipo1) {
		this.nombreEquipo1 = nombreEquipo1;
	}

	public String getNombreEquipo2() {
		return nombreEquipo2;
	}

	public void setNombreEquipo2(String nombreEquipo2) {
		this.nombreEquipo2 = nombreEquipo2;
	}

	public String getNombreCampeonato() {
		return nombreCampeonato;
	}

	public void setNombreCampeonato(String nombreCampeonato) {
		this.nombreCampeonato = nombreCampeonato;
	}

	public boolean isEditarCalendario() {
		return editarCalendario;
	}

	public void setEditarCalendario(boolean editarCalendario) {
		this.editarCalendario = editarCalendario;
	}

	public boolean isListarCalendario() {
		return listarCalendario;
	}

	public void setListarCalendario(boolean listarCalendario) {
		this.listarCalendario = listarCalendario;
	}

	public List<Jugadores> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugadores> jugadores) {
		this.jugadores = jugadores;
	}

	public List<Jugadores> getJugadores1() {
		return jugadores1;
	}

	public void setJugadores1(List<Jugadores> jugadores1) {
		this.jugadores1 = jugadores1;
	}
	
	public List<Jugadores> getSelectedJugadores() {
		return selectedJugadores;
	}

	public void setSelectedJugadores(List<Jugadores> selectedJugadores) {
		this.selectedJugadores = selectedJugadores;
	}

	public List<Jugadores> getSelectedJugadores1() {
		return selectedJugadores1;
	}

	public void setSelectedJugadores1(List<Jugadores> selectedJugadores1) {
		this.selectedJugadores1 = selectedJugadores1;
	}

	public String getLabelCrono() {
		return labelCrono;
	}

	public void setLabelCrono(String labelCrono) {
		this.labelCrono = labelCrono;
	}

	public boolean isCalendario() {
		return calendario;
	}

	public void setCalendario(boolean calendario) {
		this.calendario = calendario;
	}

	public boolean isJugarPartido() {
		return jugarPartido;
	}

	public void setJugarPartido(boolean jugarPartido) {
		this.jugarPartido = jugarPartido;
	}
}
