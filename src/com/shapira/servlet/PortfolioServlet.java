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
		portfolio portfolio = portfolioManager .getPortfolio();

		portfolio portfolio2 = new portfolio(portfolio);
		portfolio2.setTitle("Portfolio #2");
		
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println("<br>");
		resp.getWriter().println(portfolio2.getHtmlString());
		
		portfolio.removeStock(portfolio.getStocks()[0].getSymbole());
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println("<br>");
		resp.getWriter().println(portfolio2.getHtmlString());
		
		portfolio2.getStocks()[portfolio2.getStockIndex()-1].setBid((float) 55.55);
		
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println("<br>");
		resp.getWriter().println(portfolio2.getHtmlString());
}
	
	
}
