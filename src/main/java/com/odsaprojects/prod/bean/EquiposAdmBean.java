package com.odsaprojects.prod.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.odsaprojects.prod.dao.CalendarioDao;
import com.odsaprojects.prod.dao.DirectoresDao;
import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.dao.JugadoresDao;
import com.odsaprojects.prod.dao.impl.DirectoresDaoImpl;
import com.odsaprojects.prod.dao.impl.EquiposDaoImpl;
import com.odsaprojects.prod.dao.impl.JugadoresDaoImpl;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.Directores;
import com.odsaprojects.prod.entities.Equipos;
import com.odsaprojects.prod.entities.Jugadores;
import com.odsaprojects.prod.util.Constantes;
import com.odsaprojects.prod.util.SessionUtils;

/**
 * @author Osvaldo D. Sandoval
 *
 */
@Named
@ViewScoped
public class EquiposAdmBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(EquiposAdmBean.class); 
	
	private long id;
	private String nombreEquipo;
	private String abreviatura;
	private String logo;
	private UploadedFile file;
	
	private boolean listaEquipos;
	private boolean equipo;
	private boolean editarEquipo;
	
	private StreamedContent image;
	
	private DirectoresDao dao;
	private EquiposDao daoEquipo;	
	private CalendarioDao daoCalendario;
	private List<Directores> dirList;
	private List<Equipos> equipList;
	private Equipos edtEquipo;
	
	private int dir;
	private int dirAntes;
	private Map<String, Long> dirs;
	
	@Inject
	SessionUtils session;

	@Inject
	@SuppressWarnings("rawtypes")
	public EquiposAdmBean(CalendarioDao daoCalendario) {
		this.daoCalendario = daoCalendario;
		equipo = true;
		listaEquipos = false;
		editarEquipo = false;
		
		String variable1 = null;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		Map params = ec.getRequestParameterMap();
		variable1 = (String) params.get("eqp");
		BuscarDirectores();
		if(variable1 != null && variable1.equals("1")) {			
			equipo = true;
			listaEquipos = false;	
			editarEquipo = false;
		}else 
			if(variable1 != null && variable1.equals("0")) {
				BuscarEquipos();
				equipo = false;
				listaEquipos = true;
				editarEquipo = false;
		} else {
			equipo = false;
			listaEquipos = false;
			editarEquipo = true;
			}			
	}

	

	public StreamedContent getImage() {
		FacesContext context = FacesContext.getCurrentInstance();
		byte[] imageInByte = null;

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            image = new DefaultStreamedContent();
        }
        else {
            String equipoId = context.getExternalContext().getRequestParameterMap().get("equipId");
            for (Equipos unEquipo : equipList) {
            	if(unEquipo.getId() == Long.valueOf(equipoId)) {          		                   
                    try {
            			
                    	File folderInput = new File(Constantes.UPLOADEDFILES + unEquipo.getLogo());
        	            BufferedImage folderImage = ImageIO.read(folderInput);
                    			
                    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    	ImageIO.write( folderImage, "jpg", baos );
                    	baos.flush();
                    	imageInByte = baos.toByteArray();
                    	baos.close();
                    			
                	} catch(IOException e) {
                		LOG.error(e.getMessage(), e);
                	}
            		break;
            	}					
			}
                                              
            image =  new DefaultStreamedContent(new ByteArrayInputStream(imageInByte));	            	           
        }
        
        return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
		LOG.info("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
	}
	
	public void CrearTeam() {
		
		String relativePath = Constantes.RESOURCEIMAGE;
		String absolutePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath);
		Path folder = Paths.get(absolutePath);
		
		String filename = FilenameUtils.getBaseName(file.getFileName());
		String extension = FilenameUtils.getExtension(file.getFileName());
		try {
			Path tempFile = Files.createTempFile(folder, filename + "-", "." + extension);
			InputStream input = file.getInputstream();
			Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);

			Equipos unEquipo = new Equipos();
			daoEquipo = new EquiposDaoImpl();
			dao = new DirectoresDaoImpl();

			unEquipo.setNombre(getNombreEquipo());
			unEquipo.setAbreviatura(getAbreviatura());
			unEquipo.setLogo(tempFile.getFileName().toString());
			unEquipo.setEstado(1);
			
			String strVar = String.valueOf(session.get("idUsuario"));
			Long idUser = Long.valueOf(strVar);

			long idDir = dir;
			Directores dirEquipo = dao.BuscarDirectorPorId(idDir, idUser);
			unEquipo.setDirectores(dirEquipo);

			if (daoEquipo.RegistrarEquipo(unEquipo)) {
				if(dirEquipo.getId() != 0)
					dirEquipo.setDireqp(1);
				dao.RegistrarDirector(dirEquipo);
				session.sendMessageToView(Constantes.CREATETEAMSUCCESS + getNombreEquipo());
				LimpiarCamposEquipo();
			} else {
				session.sendErrorMessageToView(Constantes.CREATETEAMERROR);
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
			session.sendMessageToView(e.getMessage());
		}
	}
	
	public void EditarTeam() {
		Equipos unEquipo = new Equipos();
		daoEquipo = new EquiposDaoImpl();
		dao = new DirectoresDaoImpl();
				
		unEquipo.setId(this.id);
		unEquipo.setNombre(getNombreEquipo());
		unEquipo.setAbreviatura(getAbreviatura());
		unEquipo.setLogo(this.logo);
		unEquipo.setEstado(1);
		
		String filename = FilenameUtils.getBaseName(file.getFileName());
		String extension = FilenameUtils.getExtension(file.getFileName());
		
		if(!filename.isEmpty()) {
			String relativePath = Constantes.RESOURCEIMAGE;
			String absolutePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath);
			Path folder = Paths.get(absolutePath);			
			try {
				Path tempFile = Files.createTempFile(folder, filename + "-", "." + extension);
				InputStream input = file.getInputstream();
				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
				unEquipo.setLogo(tempFile.getFileName().toString());
			} catch (Exception e) {
				LOG.error(e.getMessage());
				session.sendMessageToView(e.getMessage());
			}			
		}
				
		try {
			String strVar = String.valueOf(session.get("idUsuario"));
			Long idUser = Long.valueOf(strVar);
			Directores dirEquipo = dao.BuscarDirectorPorId(dir, idUser);
			unEquipo.setDirectores(dirEquipo);
		
			if (daoEquipo.ActualizarEquipo(unEquipo)) {
				if(this.dirAntes != dir) {
					Directores dirEquipoAntes = dao.BuscarDirectorPorId(dirAntes, idUser);
					dirEquipoAntes.setDireqp(0);
					dao.RegistrarDirector(dirEquipoAntes);
				}
				
				dirEquipo.setDireqp(1);
				if (dao.RegistrarDirector(dirEquipo)) {
					session.sendMessageToView(Constantes.EDITTEAMSUCCESS + getNombreEquipo());
				} else {
					session.sendErrorMessageToView(Constantes.EDITTEAMSDIRERROR);
				}
			} else {
				session.sendErrorMessageToView(Constantes.EDITTEAMSERROR);
			}
			EditarEquipos(unEquipo);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			session.sendErrorMessageToView(Constantes.EDITTEAMSDIRERROR);
		}
	}
	
	public void DevolverEquipos() {
		daoEquipo = new EquiposDaoImpl();
		
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);
		
		equipList = daoEquipo.DevolverEquipos(idUser);
		
		ListaEquipos();
	}
	
	public void LimpiarCamposEquipo() {
		setNombreEquipo(null);
		setAbreviatura(null);
		setFile(null);
		
		BuscarDirectores();
	}
	
	public void ListaEquipos() {
		this.equipo = false;
		this.listaEquipos = true;
	}
	
	public void EditarEquipos(Equipos eqp) {
		setId(eqp.getId());
		setNombreEquipo(eqp.getNombre());
		setAbreviatura(eqp.getAbreviatura());
		setLogo(eqp.getLogo());
		dirs.put(eqp.getDirectores().getNombres() + " " + eqp.getDirectores().getApellidos(), eqp.getDirectores().getId());
		//Pongo al director del equipo como seleccionado en el combobox
		dir = eqp.getDirectores().getId().intValue();
		setDirAntes(dir);
		
		equipo = false;
		listaEquipos = false;
		editarEquipo = true;
	}
	
	public void DeleteEquipo() {
		daoEquipo = new EquiposDaoImpl();
		dao = new DirectoresDaoImpl();
		JugadoresDao daoJugador = new JugadoresDaoImpl();
		Equipos eqp = daoEquipo.BuscarEquiposPorId(this.id);		
		eqp.setEstado(0);
		try {
			String strVar = String.valueOf(session.get("idUsuario"));
			Long idUser = Long.valueOf(strVar);
			Directores dir = dao.BuscarDirectorPorId(eqp.getDirectores().getId(), idUser);
			if(dir != null) {
				dir.setDireqp(0);
				dao.RegistrarDirector(dir);
			}
			List<Jugadores> plyList = daoJugador.BuscarJugadoresPorEquipos(eqp.getId());
			if(plyList.size() != 0) {
				Equipos eqp0 = daoEquipo.BuscarEquiposPorId(0);
				for (Jugadores ply : plyList) {
					ply.setEquipos(eqp0);
					int cont = 1;
					boolean reintentar = true;
					while(cont <= 3 && reintentar) {
						if(daoJugador.RegistrarJugador(ply)) {
							reintentar = false;
						} 
						cont += cont;
					}
				}
			}
			List<Calendario> calList = daoCalendario.BuscarCalendarioByEquipo(eqp.getId());
			if(calList.size() != 0) {
				int cont1 = 1;
				boolean reintentar1 = true;
				while(cont1 <= 3 && reintentar1) {
					if(daoCalendario.ActualizarEstadoEnCeroListaCalendario(calList)) {
						reintentar1 = false;
					}
					
					cont1 += cont1;
				}
			}
			if(daoEquipo.ActualizarEquipo(eqp)) {
				equipList = daoEquipo.DevolverEquipos(idUser);
				//equipList.remove(eqp);
				session.sendMessageToView("Eliminado " + eqp.getNombre());
			} else {
				session.sendErrorMessageToView("Error al eliminar el equipo");
			}
		} catch (Exception e) {
			session.sendErrorMessageToView(e.getMessage());
		}
	}
	
	public void BuscarEquipos() {
		daoEquipo = new EquiposDaoImpl();
		
		session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);
		
		equipList = daoEquipo.DevolverEquiposNoSinEquipo(0, idUser);
	}
	
	public void BuscarEquipoById(long id) {
		daoEquipo = new EquiposDaoImpl();
		
		edtEquipo = daoEquipo.BuscarEquiposPorId(id);
	}
	
	public void BuscarDirectores() {
		dao = new DirectoresDaoImpl();	

		session = new SessionUtils();
		String strVar = String.valueOf(session.get("idUsuario"));
		Long idUser = Long.valueOf(strVar);	
			
		List<Directores> util = dao.DevolverDirSinEquipos(idUser);
		dirs = new HashMap<>();
		
		for (Directores directores : util) {
			dirs.put(directores.getNombres() + " " + directores.getApellidos(), directores.getId());
			
		}
	}
	
	public void LoadDeleteEquipo(Equipos eqp) {
		this.id = eqp.getId();
	}
	
	public void LoadListarEquipos() {
		session.redirectPage("equiposAdm.xhtml?eqp=0");
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

	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }    

	public boolean isListaEquipos() {
		return listaEquipos;
	}

	public void setListaEquipos(boolean listaEquipos) {
		this.listaEquipos = listaEquipos;
	}

	public boolean isEquipo() {
		return equipo;
	}

	public void setEquipo(boolean equipo) {
		this.equipo = equipo;
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
	
	public boolean isEditarEquipo() {
		return editarEquipo;
	}

	public void setEditarEquipo(boolean editarEquipo) {
		this.editarEquipo = editarEquipo;
	}

	public Equipos getEdtEquipo() {
		return edtEquipo;
	}

	public void setEdtEquipo(Equipos edtEquipo) {
		this.edtEquipo = edtEquipo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getDirAntes() {
		return dirAntes;
	}

	public void setDirAntes(int dirAntes) {
		this.dirAntes = dirAntes;
	}

}
