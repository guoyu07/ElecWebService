package com.gs.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import com.google.gson.Gson;
import com.gs.model.Elec;

public class TestServlet {

	@Test
	public void test() throws HttpException, IOException {
		HttpClient hc = new HttpClient();
		PostMethod p = new PostMethod("http://127.0.0.1:8888/ElecWebService/CommitService");
		Elec e = new Elec();
		e.setDate(20131212);
		e.setDay(12);
		e.setElecnum(120);
		e.setInputelec(100);
		e.setMonth(12);
		e.setMonth(12);
		e.setUsed(4);
		e.setYear(2013);
		Gson g = new Gson();
		p.setParameter("json", g.toJson(e));
		hc.executeMethod(p);
		System.out.println(p.getStatusCode());
		System.out.println(p.getResponseBodyAsString());
	}
	
	@Test
	public void testJson(){
		Elec e = new Elec();
		e.setDate(20131212);
		e.setDay(12);
		e.setElecnum(120);
		e.setInputelec(100);
		e.setMonth(12);
		e.setMonth(12);
		e.setUsed(4);
		e.setYear(2013);
		Gson g = new Gson();
		System.out.println(g.toJson(e));
	}

}
