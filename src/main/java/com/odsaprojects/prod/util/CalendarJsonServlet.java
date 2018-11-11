package com.odsaprojects.prod.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		List l = new ArrayList();
		 
		/* CalendarDTO c = new CalendarDTO();
		 c.setId(1);
		 c.setStart("2018-11-07 08:00:00");
		 c.setEnd("2018-11-07 09:30:00");
		 c.setTitle("Task in Progress Dia 7");
		 
		 CalendarDTO d = new CalendarDTO();
		 d.setId(2);
		 d.setStart("2018-11-07 09:45:00");
		 d.setEnd("2018-11-07 11:15:00");
		 d.setTitle("Task in Progress Dia 7");
		 
		 l.add(c);
		 l.add(d);*/
		 
		 EquiposDao daoEquipo = new EquiposDaoImpl();
			
		//String month = request.getParameter("month");
		//String year = request.getParameter("year");
		
        //List<Event> events = new ArrayList<Event>();
		
                // skipped code ....

        CalendarioDao dao = new CalendarioDaoImpl();
		//ArrayList<Calendario> listCalendario = (ArrayList<Calendario>) dao.BuscarEventosPorFecha(Integer.parseInt(year), Integer.parseInt(month));
        //List<Calendario> listCal = dao.BuscarEventosPorFecha(Integer.parseInt(year), Integer.parseInt(month));
        List<Calendario> listCal = dao.DevolverCalendario();
        
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
			
                // convert "apps" to "events"		
		
		/*String json = new Gson().toJson(events);
		
		
		// Write JSON string.
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);*/
		 
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
