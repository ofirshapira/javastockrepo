package com.shapira;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.org.mozilla.javascript.internal.ast.ForInLoop;

public class Exercise3 extends HttpServlet{
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	resp.setContentType("text/html");

	
	
	double Radius = 50;
	double area = Radius * Radius * Math.PI;
	String line1 = new String("Area of circle with radius " + Radius + " is: " + area + " square-cm. ");

	
	double degrees = 30;
	int hypotenuse = 50;
	double Rdegrees = Math.toRadians(degrees);
	double result = Math.sin(Rdegrees) *hypotenuse;
	
	String line2 = new String("Length of opposite where angle B is " + degrees + " and hypotenuse length is " + hypotenuse + "cm is " + result + "cm");

	
	int base = 20;
	int exp = 13;
	

	double res3 = Math.pow(base, exp);
	String line3 = new String("a" + base);


	line3 = new String("Power of " + base + " with exp of " + exp + " is " + res3);

	
	String resultStr = line1 + "<br>" + line2 + "<br>" + line3;
	resp.getWriter().println(resultStr);
}
}