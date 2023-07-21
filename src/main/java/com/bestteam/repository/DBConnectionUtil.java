package com.bestteam.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionUtil {

	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/cafe?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	public static final String ID = "root";
	public static final String PASSWORD = "tkfkdgo486";
	
	private DBConnectionUtil() {
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, ID, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
	}
}
