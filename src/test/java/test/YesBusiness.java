package test;

import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class YesBusiness {

	public static void main(String[] args) {

		String path = "/home/apmosys/Downloads/BNK_Yes_Business_IMPS_Bulk_Transfer_Maker_Web/data/IMPS SDSC1.xlsx";

		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection con = null;
		Recordset record = null;

		try {
			con = fillo.getConnection(path);

			String query = "SELECT * FROM Sheet1";
			record = con.executeQuery(query);

			String value = null;

			if (record.next()) {
				value = record.getField("Remarks");
				System.out.println("Before Change Remark = " + value);
			}

			// Format timestamp
			String timestamp = java.time.LocalDateTime.now()
					.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

			value = "TEST " + timestamp;

			// ✅ Update only first row (assuming Remarks is unique or using another column)
			String updateQuery = "UPDATE Sheet1 SET Remarks='" + value.replace("'", "''") + "' WHERE Remarks='"
					+ record.getField("Remarks") + "'";

			con.executeUpdate(updateQuery);
			System.out.println("Update successfully");

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (record != null)
				record.close();
			if (con != null)
				con.close();
		}

	}

}
