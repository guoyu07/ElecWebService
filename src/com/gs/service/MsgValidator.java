package com.gs.service;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.model.Msg;

public class MsgValidator {
	protected static boolean validate(HttpServletRequest req,
			String correctClientID) {
		boolean result = false;
		String clientID = (String) req.getParameter("clientID");
		if (clientID == correctClientID)//��֤ClientID�Ϸ�
			result = true;
		else {
			result = false;
			return result;
		}
		try {//��֤Json��ʽ�Ƿ�Ϸ�
			new Gson().fromJson(req.getParameter("msg"), Msg.class);
			result = true;
		} catch (JsonSyntaxException e) {
			result = false;
			return result;
		}
		return result;
	}
}
