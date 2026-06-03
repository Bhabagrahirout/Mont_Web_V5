package protenTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class ReadOtp {

	
	public static void main(String[] args) throws InterruptedException {
		while (true) {
			main1();
			Thread.sleep(4000);
		}
	}
	
	// both for intranet and internet
	public static void main1() {
		String mobileNumber="7208718998";
		String sender="EDLAMC";
		String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

		String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
				.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);

		String OTP = js.getString("[0].otp");
		System.out.println("OTP is " + OTP);
		

	}

}
