package com.gs.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		PostMethod p = new PostMethod(
				"http://202.206.64.193/ElecWebService/CommitService");
		Elec e = new Elec();
		e.setDate(20131209);
		e.setDay(9);
		e.setElecnum(120);
		e.setInputelec(100);
		e.setMonth(12);
		e.setMonth(12);
		e.setUsed(4);
		e.setYear(2013);
		Gson g = new Gson();
		p.setParameter("json", g.toJson(e));
		p.setParameter("user", "wpl");
		hc.executeMethod(p);
		System.out.println(p.getStatusCode());
		System.out.println(p.getResponseBodyAsString());
	}

	@Test
	public void testJson() {
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

	@Test
	public void test1() throws IOException {
		Elec elec = new Elec();
		elec.setDate(20131212);
		elec.setDay(12);
		elec.setElecnum(120);
		elec.setInputelec(100);
		elec.setMonth(12);
		elec.setMonth(12);
		elec.setUsed(4);
		elec.setYear(2013);
		
		
		Gson g = new Gson();
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		String params = "json="+g.toJson(elec)+"&user=wpl";
		// 尝试发送请求
		try {
			u = new URL("http://202.206.64.193/ElecWebService/CommitService");
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(params);
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		System.out.println(con.getResponseCode());
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println(buffer.toString());

	}
	
	@Test
	public void test2() throws HttpException, IOException{
		Elec elec = new Elec();
		elec.setDate(20131212);
		elec.setDay(12);
		elec.setElecnum(120);
		elec.setInputelec(100);
		elec.setMonth(12);
		elec.setMonth(12);
		elec.setUsed(4);
		elec.setYear(2013);
		
		HttpClient hc = new HttpClient();
		PostMethod p = new PostMethod(
				"http://202.206.64.193/ElecWebService/CommitService");
		Gson g = new Gson();
		p.setParameter("json", g.toJson(elec));
		p.setParameter("user", "wpl");
		hc.executeMethod(p);
		int commitcode = 0;
		try {
			commitcode = Integer.parseInt(p.getResponseBodyAsString());
		} catch (NumberFormatException e1) {
		}
		int code = p.getStatusCode();
		System.out.println(code +"   "+commitcode);
	}

}
