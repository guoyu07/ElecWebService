package com.gs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.model.Msg;

public class MsgDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	private String dbname;
	private String dbpass;
	// ������̬ȫ�ֱ���
	private Connection conn;

	private Statement st;

	/* �������ݼ�¼�����������������ݼ�¼�� */
	public void save(Msg m) throws SQLException {
		String sql = "INSERT INTO `elec`.`msg` (`msg`, `sender`, `recipient`, `time`) VALUES ('"
				+ m.getMsg()
				+ "', '"
				+ m.getSender()
				+ "', '"
				+ m.getRecipient() + "', '" + m.getTime() + "');";
		// �������ݵ�sql����
		st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql������Statement����
		st.executeUpdate(sql); // ִ�в���������sql���䣬�����ز������ݵĸ���
	}

	/* ��ȡ���ݿ����ӵĺ��� */
	public Connection getConnection() {
		conn = null; // ���������������ݿ���Connection����
		try {
			Class.forName("com.mysql.jdbc.Driver");// ����Mysql��������

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/elec", dbname, dbpass);// ������������

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return conn; // ���������������ݿ�����
	}

	/**
	 * 
	 */
	public MsgDAO() {
		this.dbname = "root";
		this.dbpass = "";//FIXME
		conn = getConnection();
	}

	public MsgDAO(String dbname, String dbpass) {
		this.dbname = dbname;
		this.dbpass = dbpass;
		conn = getConnection();
	}

	public Msg loadMsg(int time) throws SQLException {
		String sql = "select * from msg where time = " + time;
		Msg m = null;
		st = (Statement) conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		m = new Msg();
		m.setTime(time);
		m.setMsg(rs.getString("msg"));
		m.setRecipient(rs.getString("recipient"));
		m.setSender((rs.getString("sender")));
		st.close();
		return m;
	}

	public void destory() {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public List<Msg> getMsgs(String recipient) {
		List<Msg> list = new LinkedList<Msg>();
		String sql = "select * from msg where recipient = '" + recipient+"'";
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Msg m = null;
				m = new Msg();
				m.setTime(rs.getLong("time"));
				m.setMsg(rs.getString("msg"));
				m.setRecipient(rs.getString("recipient"));
				m.setSender((rs.getString("sender")));
				list.add(m);
			}
			st.close();
		} catch (SQLException s) {
			logger.error(s.getMessage());
			s.printStackTrace();
		}

		return list;
	}

	public boolean delete(long time) throws SQLException {
		String sql = " DELETE FROM `elec`.`msg` WHERE `msg`.`time` = " + time
				+ ";";
		boolean re = false;
		st = (Statement) conn.createStatement();
		st.executeUpdate(sql);
		st.close();
		return re;
	}

	public boolean isConnected() throws SQLException {
		return conn.isClosed();
	}

}
