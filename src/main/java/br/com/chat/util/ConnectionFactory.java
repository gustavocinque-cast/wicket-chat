package br.com.chat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {	
	
	private ConnectionFactory(){}

	private static Connection con= null;
	
	static{		
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
//			DriverManager.registerDriver(new Driver());
		} catch (ClassNotFoundException e1) {throw new RuntimeException("Erro do Driver!!!!!!!!!!!!!!");}
		
		
		try {
			if(con==null)con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatline","","");
			
		} catch (SQLException e) {throw new RuntimeException("Erro ao conectar com banco!!!!!!!!!!!!!!");}
	}
	
	public static Connection getConnection(){
		return con;
	}

}
