package com.gs.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.DAO.MsgDAO;
import com.gs.model.Msg;

public class MessageService extends HttpServlet {
	

	private Set<String> clientIDSet;
	private MsgDAO dao;

	public MessageService() {
		dao = new MsgDAO();
		clientIDSet = new HashSet<String>();
		clientIDSet.add("wpl");
		clientIDSet.add("xt");
		clientIDSet.add("gsh");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean clientIDCorrect = false, jsonCorrect = false, keyCorrect = false;
		String clientID = (String) req.getParameter("clientID");
		if (this.clientIDSet.contains(clientID))// 验证ClientID合法
			clientIDCorrect = true;
		Msg msg = null;
		try {// 验证Json格式是否合法
			msg = new Gson().fromJson(req.getParameter("msg"), Msg.class);
			jsonCorrect = true;
		} catch (JsonSyntaxException e) {
		}

		if (Integer.valueOf(req.getParameter("key")) == (Integer// 验证密钥
				.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date(
						System.currentTimeMillis()))) + 94))
			keyCorrect = true;
		String postCode = "820";
		if (!(clientIDCorrect == true && jsonCorrect == true && keyCorrect == true)) {
			resp.setStatus(403);
			if (!clientIDCorrect) {
				postCode = "830";
			}
			if (!jsonCorrect) {
				postCode = "831";
			}
			if (!keyCorrect) {
				postCode = "832";
			}
			resp.getWriter().write(postCode);
			return;
		}

		try {
			if (!dao.isConnected())
				dao.getConnection();
		} catch (SQLException e) {
			resp.setStatus(500);
			postCode = "850";
			return;
		}

		try {
			dao.save(msg);
		} catch (SQLException e) {
			resp.setStatus(500);
			postCode = "851";
			return;
		}
		resp.setStatus(200);
		resp.getWriter().write(postCode);

	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean clientIDCorrect = false, keyCorrect = false;
		if (Integer.valueOf(req.getParameter("key")==null?"0":req.getParameter("key")) == (Integer// 验证密钥
				.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date(
						System.currentTimeMillis()))) + 94))
			keyCorrect = true;
		String clientID = req.getParameter("clientID");
		if (this.clientIDSet.contains(clientID))// 验证ClientID合法
			clientIDCorrect = true;
		if(!clientIDCorrect || !keyCorrect){resp.sendRedirect("MessageService.html");resp.setStatus(403);return;}
		
		try {
			if (!dao.isConnected())
				dao.getConnection();
		} catch (SQLException e) {
			resp.setStatus(500);
			return;
		}
		String recipient = req.getParameter("recipient");
		List<Msg> list = null;
		if (recipient != null) {
			list = dao.getMsgs(recipient);
		}
		for(Msg m :list){
			try {
				dao.delete(m.getTime());
			} catch (SQLException e) {
				resp.setStatus(500);
			}
		}
		String json = new Gson().toJson(list);
		resp.getWriter().write(json);
	}

}
