package test;

import java.util.Base64;

public class Enocde {

	public static void main(String[] args) {
		
		String decodedBytes = Base64.getEncoder().encodeToString("Apmosys@123".getBytes());
		System.out.println(decodedBytes);
		decodedBytes="QXBtb3N5c0AxMjM=";
		
		 System.out.println("✅ Successfully Verified that file downloaded and content is correct.");

		 System.out.println("❌ Content does not match expected data.");
		byte[] string = Base64.getDecoder().decode(decodedBytes.getBytes());
		String result = new String(string);
		System.out.println(result);
		
		 
		 String Bhaba="Bhaba##Rout";
		 String[] split = Bhaba.split("#");
		 System.out.println(split[1]);
	}

}
