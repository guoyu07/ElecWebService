package com.gs.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.DAO.ElecDAO;
import com.gs.model.Elec;

public class CommitService extends HttpServlet {
	private ElecDAO dao;
	@Override
	public void init() throws ServletException {
		dao = new ElecDAO("root","zhouking");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	private static final long serialVersionUID = -2651430616398372911L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Elec elec = new Gson().fromJson(req.getParameter("json"),Elec.class);
		try {
			dao.save(elec);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				dao.delete(elec.getDate());
				dao.save(elec);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		resp.getWriter().write("Commit OK");
		resp.getWriter().flush();
	}

}
