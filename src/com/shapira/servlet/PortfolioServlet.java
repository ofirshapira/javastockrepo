package com.shapira.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shapira.model.portfolio;
import com.shapira.service.PortfolioManager;

public class PortfolioServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		resp.setContentType("text/html");
		PortfolioManager portfolioManager = new PortfolioManager();
		portfolio portfolio = portfolioManager.getPortfolio();


		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println("<br>");
	
}
	
	
}
