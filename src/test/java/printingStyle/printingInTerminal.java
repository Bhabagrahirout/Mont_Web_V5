package printingStyle;

public class printingInTerminal {

	public static void main(String[] args) {
		String Query = "UPDATE runtime_instance SET end_time ='" + " 3456" + "' , status='" + "True"
				+ "' WHERE application_id=" + "123" + " AND instance_run_id=" + "123";
		int rs = 1;
		String apiFilePath = "Axis Png";

		String divider = "═".repeat(60);
		String subDiv = "─".repeat(60);
		
		
		System.out.println("\n" + divider);
		System.out.println(" 🔍 EXECUTION DETAILS");
		System.out.println(subDiv);
		System.out.printf(" [WEB] ELEMENT      : Displayed = %s [✓]%n", "true");
		System.out.printf(" [LOG] FLAG STATE   : %s%n", "true | true");
		System.out.printf(" [INFO] STATUS      : %s%n", "1");
		System.out.println(subDiv);
		System.out.printf(" [INFO] TIME RAW    : %s sec%n", "6.088");
		System.out.printf(" [INFO] TIME FINAL  : %s sec%n", "6.09");
		System.out.println(subDiv);
		System.out.printf(" [WEB] CONDITION    : %s%n", "false");
		System.out.printf(" [WEB] CURRENT URL  : %s%n", "https://feepayment.cub.bank.in/StudentLogin?Instname=SUE");

		System.out.println("════════════════════════════════════════════════════════════");

		System.out.println("\n" + divider);
		System.out.println(" 📊 DATABASE OPERATIONS");
		System.out.println(subDiv);
		System.out.printf(" [DB]  QUERY        : %s%n", Query);
		System.out.printf(" [DB]  STATUS       : %d rows updated successfully [✓]%n", rs);
		System.out.printf(" [DB]  CONNECTION   : Closed [✓]%n");
		System.out.printf(" [DB]  INSERT       :  execution_data table [✓]%n");
		System.out.printf(" [DB]  EXEC ID      : %d%n", rs);
		System.out.printf(" [DB]  INFO         :No matching rows found to delete.%n");
		System.out.println(subDiv);

		// API
		System.out.printf(" [API] FILE PATH    : %s%n", apiFilePath);
		System.out.printf(" [API] RESPONSE     : %d [✓]%n", rs);
		System.out.printf(" [API] BODY         : %s%n", apiFilePath);

		// Info
		System.out.printf(" [INFO] ERROR_INPUT : %s%n", apiFilePath);

		System.out.println("\n" + divider);
		System.out.println(" ⚡ SHUTDOWN HOOK ACTIVATED");
		System.out.println(subDiv);

		System.out.println(subDiv);

		// Web Driver Section
//		System.out.printf(" [WEB] INSTANCE   : Terminated [✓]%n");
		System.out.printf(" [WEB] DRIVER     : Quit successfully [✓]%n");

		System.out.println(divider);

	}

}
