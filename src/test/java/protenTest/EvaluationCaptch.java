package protenTest;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class EvaluationCaptch {

	public static void main(String[] args) {
		
		
		
		File file=new File("/home/apmosys/Pictures/Captch/BOM_Captchas/Img_10_59_06.jpg");
		String r = RestAssured.given().multiPart("image", file).when().post("http://192.168.7.38:5000/ocr_solve").then().extract().response().asString();;
	
		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);
	
	}

}
