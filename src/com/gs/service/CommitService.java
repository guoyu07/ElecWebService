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
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = req.getParameter("user");
		if(user == null){resp.setStatus(403);return;}
		if (user.equals("gsh199449")) {
			doPost(req, resp);
		}else{
			resp.setStatus(403);
		}
		
	}

	private static final long serialVersionUID = -2651430616398372911L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = req.getParameter("user");
		if(user == null){resp.setStatus(403);return;}
		if(!(user.equals("wpl")||user.equals("gsh199449"))) {resp.setStatus(403);return;}
		dao = new ElecDAO("root","zhouking");
		int commitcode = 0;
		Elec elec = null;
		try {
			elec = new Gson().fromJson(req.getParameter("json"),Elec.class);
		} catch (JsonSyntaxException e2) {
			commitcode = 890;
			e2.printStackTrace();
			resp.getWriter().write(String.valueOf(commitcode));
			resp.getWriter().flush();
			return;
		}
		
		try {
			dao.save(elec);
			commitcode = 820;
		} catch (SQLException e) {
			try {
				dao.delete(elec.getDate());
				dao.save(elec);
				commitcode = 810;
			} catch (SQLException e1) {
				e1.printStackTrace();
				commitcode = 880;
			}
			e.printStackTrace();
		}
		resp.getWriter().write(String.valueOf(commitcode));
		resp.getWriter().flush();
		dao.destory();
	}

}
