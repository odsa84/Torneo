package com.odsaprojects.prod.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.odsaprojects.prod.dao.DirectoresDao;
import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.dao.UsuariosDao;
import com.odsaprojects.prod.dao.impl.DirectoresDaoImpl;
import com.odsaprojects.prod.dao.impl.EquiposDaoImpl;
import com.odsaprojects.prod.dao.impl.UsuariosDaoImpl;
import com.odsaprojects.prod.entities.Directores;
import com.odsaprojects.prod.entities.Equipos;
import com.odsaprojects.prod.entities.Usuarios;
import com.odsaprojects.prod.util.Constantes;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@ViewScoped
public class DirectoresAdmBean implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	private long id;
	private String nombres;
	private String apellidos;
	private String email;
	private String cedula;
	private String celular;
	
	private boolean director;	
	private boolean listaDirectores;
	private boolean editarDirector;
	
	private DirectoresDao dao;
	private EquiposDao daoEquipo;
	private UsuariosDao daoUsuario;
	private List<Directores> dirList;
	
	private int dir;
	private Map<String, Long> dirs;
	
	@Inject
	SessionUtils session;

	@SuppressWarnings("rawtypes")
	public DirectoresAdmBean() {
		director = true;
		listaDirectores = false;
		editarDirector = false;
		
		String variable1 = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		variable1 = (String) params.get("mng");
		
		if(variable1 != null && variable1.equals("1")) {			
			director = true;
			listaDirectores = false;			
		}else 
			if(variable1 != null && variable1.equals("0")) {
				DevolverDirectores();
				director = false;
				listaDirectores = true;
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

	public boolean isListaDirectores() {
		return listaDirectores;
	}

	public void setListaDirectores(boolean listaDirectores) {
		this.listaDirectores = listaDirectores;
	}

	public List<Directores> getDirList() {
		return dirList;
	}

	public void setDirList(List<Directores> dirList) {
		this.dirList = dirList;
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
	
	public boolean isEditarDirector() {
		return editarDirector;
	}

	public void setEditarDirector(boolean editarDirector) {
		this.editarDirector = editarDirector;
	}

	public void CrearDirector() {
		Directores unDirector = new Directores();
		Usuarios unUsuario = new Usuarios();
		dao = new DirectoresDaoImpl();
		daoUsuario = new UsuariosDaoImpl();
		
		unDirector.setNombres(getNombres());
		unDirector.setApellidos(getApellidos());
		unDirector.setEmail(getEmail());
		unDirector.setCedula(getCedula());
		unDirector.setCelular(getCelular());
		unDirector.setEstado(1);
		
		unUsuario = (Usuarios) session.get("objUsuario");
		
		unDirector.addUsuarios(unUsuario);
		
		if(dao.RegistrarDirector(unDirector)) {
			session.sendMessageToView(Constantes.CREATEMNGSUCCESS + getNombres() + " " + getApellidos());
			LimpiarCampos();			
		} else {
			session.sendErrorMessageToView(Constantes.CREATEMNGERROR);
		}
	}
	
	public void EditarDirector() {
		Directores unDirector = new Directores();
		dao = new DirectoresDaoImpl();	
		Usuarios unUsuario = new Usuarios();
		
		unDirector.setId(this.id);
		unDirector.setNombres(getNombres());
		unDirector.setApellidos(getApellidos());
		unDirector.setEmail(getEmail());
		unDirector.setCedula(getCedula());
		unDirector.setCelular(getCelular());
		unDirector.setEstado(1);
		
		unUsuario = (Usuarios) session.get("objUsuario");
		
		unDirector.addUsuarios(unUsuario);
		
		try {
			if(dao.RegistrarDirector(unDirector)) {
				session.sendMessageToView(Constantes.EDITMNGSUCCESS + unDirector.getNombres() + " " + unDirector.getApellidos());		
			} else {
				session.sendErrorMessageToView(Constantes.EDITMNGERROR);
			}
		} catch (Exception e) {
			session.sendErrorMessageToView(e.getMessage());
		}
	}
	
	public void DeleteDirector() {
		dao = new DirectoresDaoImpl();
		daoEquipo = new EquiposDaoImpl();
		
		//session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long var = Long.valueOf(strVar);
		
		Directores dirs = dao.BuscarDirectorPorId(this.dir, var);
		Equipos eqp = daoEquipo.BuscarEquiposPorIdDirector(dirs.getId());
		if(eqp != null) {
			session.sendWarningMessageToView(dirs.getNombres() + " " + dirs.getApellidos() + Constantes.DELETEDIRECTORWARN);
		} else {
			dirs.setEstado(0);
			
			try {
				if(dao.RegistrarDirector(dirs)) {
					//dirList = dao.DevolverDirectores();
					DevolverDirectores();
					session.sendMessageToView("Eliminado " + dirs.getNombres() + " " + dirs.getApellidos());
				} else {
					session.sendErrorMessageToView(Constantes.DELETEMNGERROR);
				}
			} catch (Exception e) {
				session.sendErrorMessageToView(e.getMessage());
			}
		}
	}
	
	public void LoadEditarDirector(Directores dir) {
		setId(dir.getId());
		setNombres(dir.getNombres());
		setApellidos(dir.getApellidos());
		setEmail(dir.getEmail());
		setCedula(dir.getCedula());
		setCelular(dir.getCelular());
		
		director = false;	
		listaDirectores = false;
		editarDirector = true;;
	}
	
	public void LoadDeleteDirector(Directores dir) {
		this.dir = dir.getId().intValue();
	}
	
	public void DevolverDirectores() {
		dao = new DirectoresDaoImpl();
		
		session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);
		
		dirList = dao.DevolverDirPorUsuario(idUser);
	}
	
	public void LimpiarCampos() {
		setNombres(null);
		setApellidos(null);
		setCedula(null);
		setEmail(null);
		setCelular(null);
	}
	
	public void LoadListarDirectores() {
		session.redirectPage("directoresAdm.xhtml?mng=0");
	}

}
