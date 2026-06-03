package test;

import com.apmosys.framework.Framework;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;

public class LibertyTest {

	public static void main(String[] args) throws FilloException, InterruptedException {

		Fillo f5 = new Fillo();
		com.codoid.products.fillo.Connection c = f5.getConnection("/home/apmosys/Downloads/Liberty_CarRenew.xlsx");

		String s41 = "Update Sheet2 set Payment = 'PayU' where Payment = '" + "PayTM" + "'";
		String s42 = "Update Sheet1 set RunStatus='Y' where PageName='Recommended Add-on Page(PayTM)'";
		System.out.println(s42);
		c.executeUpdate(s42);
		s42 = "Update Sheet1 set RunStatus='Y' where PageName='Payment Method Page(PayTM)'";
		System.out.println(s42);
		c.executeUpdate(s42);
		s42 = "Update Sheet1 set RunStatus='Y' where PageName='Payment Gateway Page(PayTM)'";
		System.out.println(s42);
		c.executeUpdate(s42);
		String s43 = "Update Sheet1 set RunStatus='N' where PageName='Recommended Add-on Page(PayU)'";
		c.executeUpdate(s43);
		System.out.println(s43);
		s43 = "Update Sheet1 set RunStatus='N' where PageName='Payment Method Page(PayU)'";
		c.executeUpdate(s43);
		System.out.println(s43);
		s43 = "Update Sheet1 set RunStatus='N' where PageName= 'Payment Gateway Page(PayU)'";
		c.executeUpdate(s43);
		System.out.println(s43);
		c.executeUpdate(s41);
		System.out.println(s41);
		Thread.sleep(2000L);

	}

}
