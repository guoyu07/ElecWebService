package com.gs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.model.Elec;
import com.gs.util.MyDate;

public class ElecDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	private String dbname;
	private String dbpass;
	// 创建静态全局变量
	private  Connection conn;

	private Statement st;

	/* 插入数据记录，并输出插入的数据记录数 */
	public void save(Elec e) throws SQLException {
		String sql = "INSERT INTO elec(date, day, elecnum,inputelec, month,used,year)"
				+ " VALUES ("
				+ e.getDate()
				+ ", "
				+ e.getDay()
				+ ", "
				+ e.getElecnum()
				+ ", '"
				+ e.getInputelec()
				+ "','"
				+ e.getMonth()
				+ "','"
				+ e.getUsed()
				+ "','"
				+ e.getYear()
				+ "')";
		// 插入数据的sql语句
		st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
		st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
	}

	/* 获取数据库连接的函数 */
	public Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/elec", dbname, dbpass);// 创建数据连接

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}

	public void create() {
		try {
			String create = "CREATE  TABLE `elec`.`elec` ( `date` INT NOT NULL ,  `day` INT NULL ,  `elecnum` INT NULL ,`inputelec` INT NULL , `month` INT NULL ,`used` INT NULL ,`year` INT NULL ,  PRIMARY KEY (`id`) );";
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象

			st.executeUpdate(create); // 执行插入操作的sql语句，并返回插入数据的个数

		} catch (SQLException e) {
			logger.error("creat table fail" + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public ElecDAO() {
		this.dbname = "root";
		this.dbpass = "940409";
		conn = getConnection();
	}

	public ElecDAO(String dbname, String dbpass) {
		this.dbname = dbname;
		this.dbpass = dbpass;
		conn = getConnection();
	}

	public Elec loadElec(int date) throws SQLException {
		String sql = "select * from elec where date = " + date;
		Elec e = null;
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				// System.out.println(rs.getString("date"));
			}
			e = new Elec();
			e.setDate(date);
			e.setDay(rs.getInt("day"));
			e.setElecnum(rs.getInt("elecnum"));
			e.setInputelec(rs.getInt("inputelec"));
			e.setMonth(rs.getInt("month"));
			e.setUsed(rs.getInt("used"));
			e.setYear(rs.getInt("year"));
			st.close();

		return e;
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
	public List<Elec> getElecs() {
		List<Elec> list = new LinkedList<Elec>();
		String sql = "select * from elec";

		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Elec e = null;
				e = new Elec();
				e.setDate(rs.getInt("date"));
				e.setDay(rs.getInt("day"));
				e.setElecnum(rs.getInt("elecnum"));
				e.setInputelec(rs.getInt("inputelec"));
				e.setMonth(rs.getInt("month"));
				e.setUsed(rs.getInt("used"));
				e.setYear(rs.getInt("year"));
				list.add(e);
			}
			st.close();
		} catch (SQLException s) {
			logger.error(s.getMessage());
			s.printStackTrace();
		}

		return list;
	}

	public List<Elec> getCurrentMonthElecs() {
		List<Elec> list = new LinkedList<Elec>();
		String sql = "select * from elec where month = "
				+ (new Date(System.currentTimeMillis()).getMonth() + 1);

		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Elec e = null;
				e = new Elec();
				e.setDate(rs.getInt("date"));
				e.setDay(rs.getInt("day"));
				e.setElecnum(rs.getInt("elecnum"));
				e.setInputelec(rs.getInt("inputelec"));
				e.setMonth(rs.getInt("month"));
				e.setUsed(rs.getInt("used"));
				e.setYear(rs.getInt("year"));
				list.add(e);
			}
			st.close();
		} catch (SQLException s) {
			logger.error(s.getMessage());
			s.printStackTrace();
		}

		return list;
	}

	/**
	 * @return
	 * @throws SQLException 
	 */
	public Elec getCurrent() throws SQLException {
		List<Elec> elecs = getElecs();
		Iterator<Elec> iter = elecs.iterator();
		int max = 0;
		while (iter.hasNext()) {
			int date = iter.next().getDate();
			if (date > max)
				max = date;
		}
		return loadElec(max);
	}

	/**
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Elec getBefore(int date) throws Exception {
		Elec e = new Elec();
		int m = 0;
		try {
			m = MyDate.getBefore(date);
		} catch (ParseException e1) {
			logger.error("日期解析错误"+e1.getMessage());
			e1.printStackTrace();
		}
		int num = 1;
		while (num != 10) {
			if (checkElecWithDate(m)) {
				e = loadElec(m);
			} else {
				m = MyDate.getBefore(m);
			}
			num++;
		}
		return e;
	}

	/**
	 * @param date
	 * @return
	 */
	private boolean checkElecWithDate(int date) {
		String sql = "select * from elec where date = " + date;
		boolean re = false;
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			re = rs.next();
			st.close();
		} catch (SQLException s) {
			logger.error(s.getMessage());
			s.printStackTrace();
		}

		return re;
	}

	public boolean delete(int date) throws SQLException {
		String sql = " DELETE elec FROM elec WHERE date = " + date + ";";
		boolean re = false;
		st = (Statement) conn.createStatement();
		st.executeUpdate(sql);
		st.close();
		return re;
	}
}
