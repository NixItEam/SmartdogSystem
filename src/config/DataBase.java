package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.Properties;

/**
 * 连接Mysql数据库所用类
 * @author fate
 *
 */
public class DataBase {
	
	private final String driver = "com.mysql.jdbc.Driver";
	
	private String url = "";
	
	private String user = "";
	
	private String password = "";
	
	public Connection conn = null;
	
	public Statement stat = null;
	
	public PreparedStatement ps = null;
	
	public DataBase() {
		
		try {
			
			// 读取数据库配置文件
			File file = new File(System.getProperty("user.dir") + "/database.properties");
			InputStream in = new FileInputStream(file);
			
			Properties prts = new Properties();
			prts.load(in);
			this.url = prts.getProperty("url");
			this.user = prts.getProperty("user");
			this.password = prts.getProperty("pass");
		} 
		catch (Exception e) {
			
			System.out.println("dataBase类构造方法出现异常!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库连接
	 * @param driver 驱动
	 * @param url 链接地址
	 * @param user 数据库用户
	 * @param password 密码
	 * @return
	 */
	public Connection getConn() {
		
		try {
			
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, user, password);
	
			return conn;
		} 
		catch (Exception e) {
			
			System.out.println("dataBase类getConn方法出现异常!");
			e.printStackTrace();
		}
		
		return null;
	} 
	
	/**
	 * 关闭数据库
	 * @param conn 数据库链接
	 * @param stat 数据库执行对象
	 */
	public void dbColse(Connection conn, Statement stat) {
		
		try {
			
			if (conn != null) {
				
				conn.close();
			}
			
			if (stat != null) {
				
				stat.close();
			}
		} 
		catch (Exception e) {
			
			System.out.println("dataBase类dbColse方法出现异常!");
			e.printStackTrace();
		}
	}
}
 