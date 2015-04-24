package com.shapira;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.http.*;
import java.util.Date;

public class StockDetailsServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("text/html");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 10, 15);
		Date date1 = cal.getTime();
		Date date2 = cal.getTime();
		Date date3 = cal.getTime();
		
		Stock pih = new Stock("PIH", 13.1f,12.4f , date1);
		Stock aal = new Stock("AAL", 5.78f,5.5f ,date2);
		Stock caas = new Stock("CAAS", 32.2f,31.5f ,date3);
		
		resp.getWriter().println(pih.getHtmlDescription());
		resp.getWriter().println("<p>");
		resp.getWriter().println(aal.getHtmlDescription());
		resp.getWriter().println("<p>");
		resp.getWriter().println(caas.getHtmlDescription());
		resp.getWriter().println("<p>");

		
}
}
