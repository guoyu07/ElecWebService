package com.gs.test;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gs.model.Msg;

public class TestMsg {

	@Test
	public void test() throws HttpException, IOException {
		Msg m = new Msg();
		m.setMsg("Hello");
		m.setRecipient("xt");
		m.setSender("wpl");
		m.setTime(System.currentTimeMillis());
		HttpClient hc = new HttpClient();
		PostMethod p = new PostMethod(
				"http://202.206.64.193/ElecWebService/MessageService");
		Gson g = new Gson();
		p.setParameter("msg", g.toJson(m));
		p.setParameter("clientID", "wpl");
		p.setParameter("key", String.valueOf(Integer// —È÷§√‹‘ø
				.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date(
						System.currentTimeMillis()))) + 94));
		hc.executeMethod(p);
		System.out.println(p.getStatusCode());
		System.out.println(p.getResponseBodyAsString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGet() throws HttpException, IOException {
		Msg m = new Msg();
		m.setMsg("hahaS");
		m.setRecipient("xt");
		m.setSender("wpl");
		m.setTime(System.currentTimeMillis());
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(
				"http://202.206.64.193/ElecWebService/MessageService?clientID=xt&recipient=xt&key="+String.valueOf(Integer
						.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date(
								System.currentTimeMillis()))) + 94));//?clientID=xt&recipient=xt&
		/*HttpMethodParams params = new HttpMethodParams();
		params.setParameter("clientID", "xt");
		params.setParameter("recipient", "xt");
		params.setParameter("key", String.valueOf(Integer
				.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date(
						System.currentTimeMillis()))) + 94));
		get.setParams(params);*/
		Gson g = new Gson();
		hc.executeMethod(get);
		System.out.println(get.getStatusCode());
		System.out.println(get.getResponseBodyAsString());
		System.out.println((List<Msg>)g.fromJson(get.getResponseBodyAsString(),
				new TypeToken<List<Msg>>() {
				}.getType()));
	}
}
