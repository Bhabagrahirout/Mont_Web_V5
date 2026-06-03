package test;

import java.util.Random;

public class RandomNoTest {

	public static void main(String[] args) throws InterruptedException {

		
		while(true)
		{
		Random random = new Random();
		int randomNumber = random.nextInt(2) + 1;
		int num ;
		if(randomNumber==1)
		{
			num=random.nextInt(20)+80;
		}else
		{
			num = random.nextInt(40)+11;
		}
		
		String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
		 double tTotalTime = Double.parseDouble(val);
		 System.out.println(tTotalTime);
		 Thread.sleep(2000);
		}

	}

}
