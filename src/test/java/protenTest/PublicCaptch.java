package protenTest;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class PublicCaptch {
	
	
	public static void main(String[] args) {
		
		File file=new File("/home/apmosys/Downloads/imageMail.png");
		String r = RestAssured.given().multiPart("image", file).when()
				.post("https://proteansaasuat.apmosys.com/captcha/predict").then().extract().response().asString();

		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);
		
	}
	

}
