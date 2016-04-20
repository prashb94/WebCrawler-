package edu.uci.ics.crawler4j.examples.basic;
import java.sql.*;

public class DBconnect {
		private Connection con;
		private Statement statement;
		private ResultSet rs;
		
		public DBconnect()
		{
				try{
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webcrawl","root","");
					statement = con.createStatement();
				}catch(Exception e)
				{
					System.out.println("Error : " + e);
				}
		}
		
		public void putData(String url, String anchor, String html)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webcrawl","root","");
				String query = "INSERT INTO `webcrawl`.`l3s` (`URL`, `AnchorText`, `HTML`) VALUES (?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
			      preparedStmt.setString (1, url);
			      preparedStmt.setString (2, anchor.toString());
			      preparedStmt.setString (3, html);
			      preparedStmt.execute();
			}catch(Exception e)
			{
				System.out.println("Error : "+ e);
			}
		}
		
		public void getData()
		{
			try
			{
				String query = "Select * from l3s";
				rs = statement.executeQuery(query);
				System.out.println("Records List : ");
				while(rs.next())
				{
					String urldb = rs.getString("URL");
					String htmldb = rs.getString("HTML");
					String AnchorText = rs.getString("AnchorText");
					System.out.println("\nURL : " + urldb + "  " + "\nHTML : " + htmldb + "  " + "\nAnchor Text : " + AnchorText );
					
				}
			}catch(Exception e)
			{
				System.out.println("Error : "+ e);
			}
		}
		
public void searchData(String Search)
		{
			try
			{
				/*Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webcrawl","root","");
				String query = "Select * from l3s where AnchorText like '%?%'";
				PreparedStatement preparedStmt = con.prepareStatement(query);
			      preparedStmt.setString (1, Search);
			      preparedStmt.execute();*/
				String query = "Select * from l3s where AnchorText like '%" + Search + "%'";
				rs = statement.executeQuery(query);
				System.out.println("Records List : ");
				while(rs.next())
				{
					String urldb = rs.getString("URL");
					String htmldb = rs.getString("HTML");
					String AnchorText = rs.getString("AnchorText");
					System.out.println("\nURL : " + urldb + "  " + "\nHTML : " + htmldb + "  " + "\nAnchor Text : " + AnchorText );
				}
				
			}catch(Exception e)
			{
				System.out.println("Error : "+ e);
			}
		}
}
