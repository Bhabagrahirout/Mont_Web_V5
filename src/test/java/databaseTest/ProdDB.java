package databaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProdDB {

	

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager
					// .getConnection("jdbc:mysql://192.168.1.228:3306/nonsbi_final?connectTimeout=5000",
					// "SynMonitoring", "Operation@123");
					.getConnection("jdbc:mysql://192.168.1.228:3306/nonsbi_final?connectTimeout=5000", "root",
							"Welcome@2023");

			Statement statement = connection.createStatement();

			String Query = "SELECT * FROM monitoring_instances WHERE instance_name LIKE '%BNK_Axis%'";

			System.out.println("========================>>>>>>>>" + Query);
			ResultSet rs = statement.executeQuery(Query);
			Thread.sleep(2000L);
			while (rs.next()) {
				String instanceName = rs.getString("instance_name");
				System.out.println("instanceName : " + instanceName);
			}
			rs.close();
			statement.close();
			connection.close();

			System.out.println("Connect successfully");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
