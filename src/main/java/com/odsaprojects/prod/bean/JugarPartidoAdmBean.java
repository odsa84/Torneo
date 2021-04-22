package com.odsaprojects.prod.bean;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.odsaprojects.prod.dao.DirectoresDao;
import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.dao.JugadoresDao;
import com.odsaprojects.prod.dao.impl.DirectoresDaoImpl;
import com.odsaprojects.prod.dao.impl.EquiposDaoImpl;
import com.odsaprojects.prod.entities.Directores;
import com.odsaprojects.prod.entities.Equipos;
import com.odsaprojects.prod.entities.Jugadores;
import com.odsaprojects.prod.util.Cronometro;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@RequestScoped
public class JugarPartidoAdmBean implements Observer {
	
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
	
	private List<Jugadores> jugadores;
	private List<Jugadores> jugadores1;
	private List<Jugadores> selectedJugadores;
	private List<Jugadores> selectedJugadores1;
	
	private Equipos equipo1;
	private Equipos equipo2;
	
	private String color[]=new String[]{"Red", "Yellow", "Blue"};
	
	private String labelCrono = "00:00";
	
	@NotNull
	private int dir;
	private Map<String, Long> dirs;
	
	@Inject
	SessionUtils session;
	
	private JugadoresDao daoJugadores;
	private EquiposDao daoEquipos;
	
	@ManagedProperty(value="#{equiposAdmBean}")
	private EquiposAdmBean eab;
	
	private Object eqp1;
	private Object eqp2;

	@Inject
	@SuppressWarnings("rawtypes")
	public JugarPartidoAdmBean(JugadoresDao daoJugadores, EquiposDao daoEquipos) {
		this.daoJugadores = daoJugadores;
		this.daoEquipos = daoEquipos;
		
		String aux = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		aux = (String) params.get("jp");
		
		if(aux != null) {
			this.eqp1 = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("equipo1");
			this.eqp2 = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("equipo2");
			
			this.BuscarEquipos((long)eqp1, (long)eqp2);
			this.BuscarJugadoresPorEquipo((long)eqp1, (long)eqp2);
		}
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
	
	@Override
	public void update(Observable o, Object arg) {
		this.setLabelCrono((String) arg);
		//InicializarCronometro(new HtmlInputText());
		System.out.println(this.getLabelCrono());
		
	}
	
	public class ListenCloseWdw extends WindowAdapter {
      public void windowClosing(WindowEvent e) {
         System.exit(0);
      }
   }
	
	public void BuscarEquipos(long id1, long id2) {
		this.setEquipo1(daoEquipos.BuscarEquiposPorId(id1));
		this.setEquipo2(daoEquipos.BuscarEquiposPorId(id2));
	}
	
	public void BuscarJugadoresPorEquipo(long idEqp1, long idEqp2) {
		this.setJugadores(daoJugadores.BuscarJugadoresPorEquipos(idEqp1));
		this.setJugadores1(daoJugadores.BuscarJugadoresPorEquipos(idEqp2));
	}
	
	public void JugadorSeleccionado() {
		Jugadores jug = selectedJugadores.get(0);
		System.out.println(jug.getNombres());
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
	
	public void SeleccionarEventos() {
		session.redirectPage("eventosAdm.xhtml");
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

	public JugadoresDao getDaoJugadores() {
		return daoJugadores;
	}

	public void setDaoJugadores(JugadoresDao daoJugadores) {
		this.daoJugadores = daoJugadores;
	}

	public List<Jugadores> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugadores> jugadores) {
		this.jugadores = jugadores;
	}

	public List<Jugadores> getSelectedJugadores() {
		return selectedJugadores;
	}

	public void setSelectedJugadores(List<Jugadores> selectedJugadores) {
		this.selectedJugadores = selectedJugadores;
	}

	public List<Jugadores> getJugadores1() {
		return jugadores1;
	}

	public void setJugadores1(List<Jugadores> jugadores1) {
		this.jugadores1 = jugadores1;
	}

	public List<Jugadores> getSelectedJugadores1() {
		return selectedJugadores1;
	}

	public void setSelectedJugadores1(List<Jugadores> selectedJugadores1) {
		this.selectedJugadores1 = selectedJugadores1;
	}

	public Equipos getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(Equipos equipo1) {
		this.equipo1 = equipo1;
	}

	public Equipos getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Equipos equipo2) {
		this.equipo2 = equipo2;
	}
	
	public String[] getColor() {
		 return color;
		 }
		 
		 public void setColor(String[] color) {
		 this.color = color;
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

		public String getLabelCrono() {
			return labelCrono;
		}

		public void setLabelCrono(String labelCrono) {
			this.labelCrono = labelCrono;
		}
}
