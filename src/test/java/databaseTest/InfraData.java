package databaseTest;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class InfraData {

	private static int infraId;
	
	public static void main1(String[] args) {

		String str ="INS_BAGIC_Maximus_Portal_Health_Web\n"
				+ "INS_BAGIC_Maximus_Portal_Motor_Web\n"
				+ "INS_BAGIC_Maximus_Portal_Travel_Web\n"
				+ "INS_Niva_Bupa_Policy_Download_Web\n"
				+ "GOV_NSDL_OLTAS_CSI_Web\n"
				+ "GOV_NSDL_TAN_Application_ Status_Web\n"
				+ "INS_BAGIC_Family_Health_Insurance_Web\n"
				+ "INS_BAGIC_Family_Travel_Insurance_Web\n"
				+ "INS_BAGIC_Individual_Health_Insurance_Web\n"
				+ "INS_BAGIC_Individual_Travel_Insurance_Web\n"
				+ "MF_HDFC_Corporate_URL_Web\n"
				+ "MF_HDFC_Corporate_URLs_Web\n"
				+ "MF_HDFC_Investor_Login_Web\n"
				+ "INS_BAGIC_Car_Insurance_Online_Web\n"
				+ "INS_BAGIC_Top_Up_Health_Insurance_Web\n"
				+ "INS_BAGIC_Two_Wheeler_Insurance_Online_Web\n"
				+ "INS_Bagic_Two_Wheeler_Insurance_Third_Party_Web\n"
				+ "INS_Liberty_CarRenew_Web\n"
				+ "INS_Liberty_Two_Wheeler_Renew_Web\n"
				+ "INS_Liberty_Two_Wheeler_Rollover_Web\n"
				+ "INS_LIC_OTP_Web\n"
				+ "INS_Liberty_CarRollover_Web\n"
				+ "INS_Liberty_HealthRollover_Web\n"
				+ "INS_GC_Cyber_Risk_Web\n"
				+ "INS_Niva_Bupa_Renewal_Web\n"
				+ "TRD_Geojit_Flip_Web\n"
				+ "MF_ABSL_Investor_Portal_Web\n"
				+ "MF_ABSL_AMC_Pre_Login_Web\n"
				+ "TRD_IDBI_Direct_On_Market_Web\n"
				+ "TRD_IDBI_Direct_Off_Market_Web\n"
				+ "MF_HDFC_Distributor_Web\n"
				+ "INS_BAGIC_API\n"
				+ "TRD_Yes_Securities_Web\n"
				+ "BNK_ESAF_CIB_RTGS_Fund_Transfer_Checker_Web\n"
				+ "BNK_ESAF_CIB_RTGS_Fund_Transfer_Maker_Web\n"
				+ "FIN_ABC_One_Service_Portal_ABAMC_Web\n"
				+ "FIN_ABC_One_Service_Portal_ABHI_Web\n"
				+ "FIN_ABC_One_Service_Portal_Stocks_Web\n"
				+ "MF_HDFC_Investor_Web1\n"
				+ "MF_HDFC_Investor_Web2\n"
				+ "INS_LGI_Full_Quote_Calculation_API\n"
				+ "INS_LGI_MOTOR_PI1_API\n"
				+ "INS_LGI_Motor_PI4_API\n"
				+ "INS_LGI_Quick_Quote_Calculation_API\n"
				+ "INS_LGI_TP_Services_Full_Quote_Calculation_New_API\n"
				+ "INS_LGI_TP_Services_Full_Quote_Calculation_Rollover_API\n"
				+ "INS_LGI_TP_Services_Policy_Number_Generation_API\n"
				+ "INS_LGI_TP_Services_Policy_Schedule_Download_API\n"
				+ "INS_LGI_TP_Services_Quick_Quote_Calculation_New_API\n"
				+ "INS_LGI_TP_Services_Quick_Quote_Calculation_Rollover_API\n"
				+ "GOV_UTIITSL_Rest_Pages_Web\n"
				+ "GOV_UTIITSL_Main_Page_Web\n"
				+ "AXIS_MF_Pre_Login_URLs\n"
				+ "INS_LGI_Maruti4_API\n"
				+ "INS_LGI_Maruti_New_API\n"
				+ "INS_LGI_Suzuki_New_API\n"
				+ "INS_SUD_Policy_Details_And_Beat_Plan_Prod_API\n"
				+ "TRD_Yes_Invest_Web\n"
				+ "TRD_ABML_On_Market_Android\n"
				+ "TRD_ABML_AMO Journey Android\n"
				+ "TRD_ABML_Off_Market_Android\n";

		str=str.replaceAll("\n", ",");
		str=str.replaceAll(",","','");
		System.out.println(str);
//		String str="BNK_SBI_Yono_2.0_Domestic_NEFT_Fund_Transfer_iOS,BNK_SBI_Yono_2.0_Electricity_Bill_Payment_iOS,BNK_SBI_Yono_2.0_Domestic_IMPS_Fund_Transfer_iOS,BNK_SBI_Yono_2.0_Within_Bank_Fund_Transfer_iOS,BNK_SBI_Yono_2.0_Electricity_Bill_Payment_Android,BNK_SBI_Yono_2.0_Domestic_NEFT_Fund_Transfer_Android,BNK_SBI_Yono_2.0_Domestic_IMPS_Fund_Transfer_Android,BNK_SBI_Yono_2.0_Within_Bank_Fund_Transfer_Android,BNK_SBI_Yono_2.0_UPI_Fund_Transfer_iOS,BNK_SBI_Yono_2.0_UPI_Fund_Transfer_Android,BNK_SBI_Yono_2.0_User_Profile_Management_IOS,BNK_SBI_Yono_2.0_FastTag_Bill_Payment_iOS,BNK_SBI_Yono_2.0_FastTag_Bill_Payment_Android,BNK_SBI_Yono_2.0_LI_Redirection_iOS,BNK_SBI_Yono_2.0_Download_Statement_iOS,BNK_SBI_Yono_2.0_Tax_Related_Services_iOS,BNK_SBI_Yono_2.0_Feedback_Journey_iOS,BNK_SBI_Yono_2.0_User_Profile_Management_Android,BNK_SBI_Yono_2.0_Feedback_Journey_AndroidBNK_SBI_Yono_2.0_Bill_Payment_Postpaid_iOS,BNK_SBI_Yono_2.0_Bill_Payment_Postpaid_Android,BNK_SBI_Yono_2.0_Savings_Account_Opening_iOS,BNK_SBI_Yono_2.0_Savings_Account_Opening_Android,BNK_SBI_Yono_2.0_Login&Logout_Web,BNK_SBI_Yono_2.0_Account_Summary_Web,BNK_SBI_Yono_2.0_Feedback_Web,BNK_SBI_Yono_2.0_LI_Redirection_Android,BNK_SBI_Yono_2.0_Download_Statement_Android,BNK_SBI_Yono_2.0_Tax_Related_Services_Android,BNK_SBI_Yono_2.0_Prepaid_Recharge_iOS,BNK_SBI_Yono_2.0_Prepaid_Recharge_Android,BNK_SBI_Yono_2.0_MPIN_SMS_iOS,BNK_SBI_Yono_2.0_MPIN_SMS_Android,BNK_SBI_Yono_2.0_Cheque_Services_iOS,BNK_SBI_Yono_2.0_Cheque_Services_Android,BNK_SBI_Yono_2.0_Account_Services_Android,BNK_SBI_YONO_2.0_Open_FD_Android,BNK_SBI_Yono_2.0_Yono_Cash_Android,BNK_SBI_Yono_2.0_Close_FD_Android,BNK_SBI_Yono_2.0_MPIN_SMS_WhatsApp_Android,BNK_SBI_Yono_2.0_Close_RD_Android,BNK_SBI_Yono_2.0_Open_RD_Android,BNK_SBI_Yono_2.0_Yono_Cash_iOS,BNK_SBI_Yono_2.0_Account_Services_iOS,BNK_SBI_Yono_2.0_Card_Services_Android,BNK_SBI_Yono_2.0_Close_FD_iOS,BNK_SBI_Yono_2.0_Close_RD_iOS,BNK_SBI_Yono_2.0_Open_FD_iOS,BNK_SBI_Yono_2.0_Open_RD_iOS,BNK_SBI_Yono_2.0_Card_Services_iOS,BNK_SBI_Yono_2.0_PPF_iOS,BNK_SBI_Yono_2.0_PPF_Android,BNK_SBI_Yono_2.0_Manage_Existing_Deposits_iOS,BNK_SBI_Yono_2.0_Manage_Existing_Deposits_Android,BNK_SBI_Yono_2.0_Domestic_NEFT_Fund_Transfer_Web,BNK_SBI_Yono_2.0_Domestic_IMPS_Fund_Transfer_Web,BNK_SBI_Yono_2.0_Within_Bank_Fund_Transfer_Web,BNK_SBI_Yono_2.0_Quick_Fund_Transfer_Web,BNK_SBI_Yono_2.0_Mobile_Recharge_Bill_Payment_Web,BNK_SBI_Yono_2.0_Electricity_Bill_Payment_Web,BNK_SBI_Yono_2.0_Mobile_Postpaid_Bill_Payment_Web,BNK_SBI_Yono_2.0_Close_FD_Web,BNK_SBI_Yono_2.0_Open_FD_Web,BNK_SBI_Yono_2.0_Close_RD_Web,BNK_SBI_Yono_2.0_Open_RD_Web,BNK_SBI_Yono_2.0_NPS_Android,BNK_SBI_Yono_2.0_Apply_Loan_Android,BNK_SBI_Yono_2.0_Apply_Loan_iOS,BNK_SBI_Yono_2.0_NPS_iOS,BNK_SBI_Yono_2.0_Cheque_Services_Web,BNK_SBI_Yono_2.0_GI_Redirection_Health_Purchase_Android,BNK_SBI_Yono_2.0_GI_Redirection_Motor_Purchase_Android,BNK_SBI_Yono_2.0_ETBSA_Android,BNK_SBI_Yono_2.0_GI_Redirection_Motor_Purchase_iOS,BNK_SBI_Yono_2.0_GI_Redirection_Health_Purchase_iOS,BNK_SBI_Yono_2.0_ETBSA_iOS,BNK_SBI_Yono_2.0_Demat_Account_iOS,BNK_SBI_Yono_2.0_ESecure_iOS,BNK_SBI_Yono_2.0_Demat_Account_Android,BNK_SBI_Yono_2.0_ESecure_Android,BNK_SBI_Yono_2.0_FasTag_Bill_Payment_Web,BNK_SBI_Yono_2.0_NPS_Web,BNK_SBI_Yono_2.0_ETBSA_Web,BNK_SBI_Yono_2.0_Clear_Tax_Android,BNK_SBI_Yono_2.0_Smart_Shield_Plus_Android,BNK_SBI_Yono_2.0_Motorz_Android,BNK_SBI_Yono_2.0_Clear_Tax_iOS,BNK_SBI_Yono_2.0_Motorz_iOS,BNK_SBI_Yono_2.0_Smart_Shield_Plus_iOS,BNK_SBI_Yono_2.0_Start_SIP_iOS,BNK_SBI_Yono_2.0_Start_SIP_Android,BNK_SBI_Yono_2.0_GI_Redirection_Motor_Web,BNK_SBI_Yono_2.0_GPAI_iOS,BNK_SBI_Yono_2.0_GSA_iOS";
//		String[] split = str.split(",");
//		System.out.println("length ==" + split.length);
//		String query="";
//		for(String s:split)
//		{
//			query=query+s+"',";
//			
//		}
//		String replaceAll = str.replaceAll(",","','");
//		System.out.println(replaceAll);
	}

	public static void main(String[] args) throws Exception {

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		java.sql.Connection dbConn = DriverManager.getConnection(
				"jdbc:mysql://192.168.1.228:3306/nonsbi_final?connectTimeout=5000", "SynMonitoring", "Operation@123");

		Fillo fillo = new Fillo();
		Connection con = fillo.getConnection("/home/apmosys/Downloads/ZoneWiseInfraMapping.xlsx");
		String query = "select * from Sheet1";
		Recordset record = con.executeQuery(query);
		int i = 1;
		while (record.next()) {

			String instance_name = record.getField("Instance Name");
			String container_ip = record.getField("Container IP");
			String host_os = record.getField("Host");
			String jenkins_master_ip = record.getField("Jenkins Master");
			String created_by = record.getField("Created By");
			// new data
			String environment_type = container_ip.split("\\.")[2] + "-"
					+ container_ip.split("\\.")[3].replaceAll(":5901", "-monitoring-container");

			System.out.println(
					instance_name + ":" + container_ip + ":" + host_os + ":" + jenkins_master_ip + ":" + created_by);

			query = "SELECT infra_id FROM infra_master WHERE container_ip = ?";
			PreparedStatement ps = dbConn.prepareStatement(query);
			ps.setString(1, container_ip);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				infraId = rs.getInt("infra_id");
				System.out.println("Data found, updating...");
				updateInfra(dbConn, infraId, environment_type, host_os, jenkins_master_ip, created_by);
			} else {
				System.out.println("No data found, inserting...");
				insertInfra(dbConn, environment_type, container_ip, host_os, jenkins_master_ip, created_by);
			}

			updateMonitoringInstance(dbConn, instance_name);
			Thread.sleep(4000);
			System.out.println("######## updated successfully excel row" + i);
			System.out.println();
			i++;
//			break;
		}

		con.close();
		dbConn.close();
		System.out.println("Done............");

	}

	public static void insertInfra(java.sql.Connection dbConn, String environment_type, String container_ip,
			String host_os, String jenkins_master_ip, String created_by) {

		try {
			String query = "INSERT INTO infra_master (environment_type,container_ip,host_os, jenkins_master_ip,is_active, created_by) VALUES (?,?,?,?,?,?)";
//			System.out.printf(" [DB]  QUERY        : %s%n", query);

			try (PreparedStatement preparedStatement = dbConn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, environment_type);
				preparedStatement.setString(2, container_ip);
				preparedStatement.setString(3, host_os);
				preparedStatement.setString(4, jenkins_master_ip);
				preparedStatement.setString(5, "Y");
				preparedStatement.setString(6, created_by);
				preparedStatement.executeUpdate();
				ResultSet executionRs = preparedStatement.getGeneratedKeys();

				if (executionRs.next()) {
					infraId = executionRs.getInt(1);
					System.out.printf(" [DB] inserted, infraId ID      : %d%n", infraId);
				}
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateInfra(java.sql.Connection dbConn, int infraId, String environment_type, String host_os,
			String jenkins_master_ip, String created_by) throws Exception {

		String updateQuery = "UPDATE infra_master SET environment_type=?, host_os=?, jenkins_master_ip=?, is_active=?, created_by=? WHERE infra_id=?";

		try (PreparedStatement updateStmt = dbConn.prepareStatement(updateQuery)) {

			updateStmt.setString(1, environment_type);
			updateStmt.setString(2, host_os);
			updateStmt.setString(3, jenkins_master_ip);
			updateStmt.setString(4, "Y");
			updateStmt.setString(5, created_by);
			updateStmt.setInt(6, infraId);

			int row = updateStmt.executeUpdate();
			System.out.printf(" [DB]  infraId table      : %d rows update %n", row);
		}
	}

	public static void updateMonitoringInstance(java.sql.Connection dbConn, String instance_name) throws SQLException {

		String updateQuery = "UPDATE monitoring_instances SET infra_id=? WHERE instance_name=?";

		try (PreparedStatement updateStmt = dbConn.prepareStatement(updateQuery)) {

			updateStmt.setInt(1, infraId);
			updateStmt.setString(2, instance_name); // ✅ important

			int row = updateStmt.executeUpdate();

			System.out.printf(" [DB]  monitoring_instances : %d rows updated%n", row);
			System.out.println("Infra_id set ="+infraId);
		}
	}
}
