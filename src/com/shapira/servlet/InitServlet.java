package com.shapira.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.algo.service.ServiceManager;

import com.shapira.service.PortfolioManager;

public class InitServlet extends HttpServlet {

	
	public void init() throws ServletException {
		
		PortfolioManager pm = new PortfolioManager();
		ServiceManager.setPortfolioManager(pm);
		super.init();
	}
}
