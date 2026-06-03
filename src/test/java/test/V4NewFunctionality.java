package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class V4NewFunctionality {

	public static void main(String[] args) throws IOException {
		
		
		
		File file=new File("/home/apmosys/Downloads/New Students/a.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();
		String line;
		int lineCount=0;
		while ((line = br.readLine()) != null) {
			
			System.out.println(line);
			lineCount++;
			sb.append(line);
		}
		
		if(lineCount==1)
		{
			System.out.println("Work"+lineCount);
		}
		else
		{
			System.out.println("Not work");
		}
		String previousData = sb.toString();
		String actualDate = previousData.split("#")[0].trim();
		
		
		br.close();
		fr.close();
		
	}
	
	
}
