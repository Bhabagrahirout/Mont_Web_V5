package protenTest;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class CaptchRead {

	public static void main(String[] args) {
		
		singleImage();
		System.out.println("========================");
//		int i = 1;
//		while (i < 20) {
//			File file = new File("/home/apmosys/Pictures/aadharhousing/img" + i + ".png");
////			File file = new File("/home/apmosys/Pictures/Captch/AndhraGramyaBank/img1.png");
//
//			String r = RestAssured.given().multiPart("image", file).when().post("http://192.168.7.38:5010/predict")
//					.then().extract().response().asString();
//
//			System.out.println("Response Body: " + r);
//			JsonPath js = new JsonPath(r);
//			String data = js.getString("text");
//			System.out.println("Text is === " + data);
//			i++;
//		}

	}

	public static void singleImage() {
		String path=System.getProperty("user.dir")+"/img.png";

		File file = new File(path);

		String r = RestAssured.given().multiPart("image", file).when().post("http://192.168.7.38:5011/predict").then()
				.extract().response().asString();

		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);

	}

}
