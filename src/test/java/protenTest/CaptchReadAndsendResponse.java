package protenTest;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class CaptchReadAndsendResponse {

	public static void main(String[] args) throws InterruptedException {

		
		singleImage();
	}

	public static void singleImage() throws InterruptedException {

		File file = new File("/home/apmosys/Pictures/Captch/CaptchImage/NivaBupa.png");

		String r = RestAssured.given().multiPart("image", file).when().post("http://192.168.7.38:5011/predict").then()
				.extract().response().asString();

		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);
		Thread.sleep(4000);
		String response = RestAssured
		        .given()
		        .contentType("application/json")
		        .body("{\n" +
		              "  \"image_name\":\" "+data +"\",\n" +
		              "  \"script_status\": \"true\"\n" +
		              "}")
		        .when()
		        .post("http://192.168.7.38:5011/log-script-status")
		        .then()
		        .extract()
		        .asString();

		System.out.println(response);


	}

}
