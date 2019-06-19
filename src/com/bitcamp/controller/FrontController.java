package com.bitcamp.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.do" }, initParams = {
		@WebInitParam(name = "inital", value = "/WEB-INF/prop.properties") })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();
	}

	private Hashtable<String, Action> ht = new Hashtable<>();

	public void init(ServletConfig config) throws ServletException {
		String inital = config.getInitParameter("inital");
		// String realpath = config.getServletContext().getRealPath(param);
		Properties prop = new Properties();
		FileReader fr = null;
		try {
			String path = config.getServletContext().getRealPath(inital);

			prop.load(new FileReader(path));
			Enumeration enu = prop.keys();
			while (enu.hasMoreElements()) {
				String key = (String) enu.nextElement();
				String value = (String) prop.get(key);

				Class c = Class.forName(value);
				Action act = (Action) c.newInstance();
				ht.put(key, act);
			}

		} catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			System.out.println(e);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		req(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		req(request, response);
	}

	private void req(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlpath = request.getServletPath();
		Action act = null;
		act = ht.get(urlpath);
		ActionForward forward = act.execute(request, response);
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher disp = request.getRequestDispatcher(forward.getPath());
				disp.forward(request, response);
			}

		}

	}
}
