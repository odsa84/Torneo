package com.odsaprojects.prod.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.odsaprojects.prod.dao.CalendarioDao;
import com.odsaprojects.prod.dao.EquiposDao;
import com.odsaprojects.prod.dao.impl.CalendarioDaoImpl;
import com.odsaprojects.prod.dao.impl.EquiposDaoImpl;
import com.odsaprojects.prod.entities.Calendario;
import com.odsaprojects.prod.entities.Equipos;

/**
 * Servlet implementation class CalendarJsonServlet
 */
@WebServlet("/CalendarJsonServlet")
public class CalendarJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarJsonServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List l = new ArrayList();
		 
		 EquiposDao daoEquipo = new EquiposDaoImpl();

        CalendarioDao dao = new CalendarioDaoImpl();
        
        String var = request.getParameter("shamp");
        
		Long idShamp = Long.parseLong(var);
		
        List<Calendario> listCal = dao.BuscarCalendarioByCampeonato(idShamp);
        
        CalendarDTO c;
        
        if(listCal.size() > 0) {        	
        	for (Calendario cal : listCal) {
        		c = new CalendarDTO();
        		c.setId(cal.getId());
        		c.setStart(cal.getFechaHoraInicio().toString());
        		c.setEnd(cal.getFechaHoraFin().toString());
        		
        		Equipos equipo = daoEquipo.BuscarEquiposPorId(cal.getEquipo1());
    			Equipos equipoV = daoEquipo.BuscarEquiposPorId(cal.getEquipo2());
    			
        		c.setTitle(equipo.getNombre() + " vs " + equipoV.getNombre());
        		
        		l.add(c);
        	}
        }
		 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(new Gson().toJson(l));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
