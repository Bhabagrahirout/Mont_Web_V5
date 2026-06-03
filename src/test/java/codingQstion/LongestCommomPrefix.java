package codingQstion;

public class LongestCommomPrefix {

	public static void main(String[] args) throws InterruptedException {

		String[] arr = { "geekfbj", "geekweww", "geeiui", "gee" };
		String longestCommonPrefix = longestCommonPrefix(arr);
		System.out.println(longestCommonPrefix);

	}

	public static String longestCommonPrefix(String arr[]) throws InterruptedException {

		int lengthCount = 0, finalCount = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length - 1; i++) {

			for (int j = 0; j < arr[i].length() && j < arr[i + 1].length(); j++) {
//			System.out.println("working for :"+arr[i]+"::"+arr[i+1]);
				if (arr[i].charAt(j) == (arr[i + 1].charAt(j))) {
					lengthCount++;
				} else {
					break;
				}
			}
			System.out.println("Length :" + lengthCount);

			if (lengthCount < finalCount)
				finalCount = lengthCount;

			lengthCount = 0;
			Thread.sleep(1000);
		}
		return arr[0].substring(0, finalCount);
	}
	
	
	public static String longestCommonPrefixSecondMethod(String arr[]) throws InterruptedException {

		
		if(arr.length==0)return "";
		
		String smallString=arr[0];
		
		for(String a:arr)
		{
			if(a.length()<smallString.length())
				smallString=a;
		}
		
		for(int i=0;i<smallString.length();i++)
		{
			char c=smallString.charAt(i);
			
			for(String str:arr)
			{
				if(str.charAt(i)!=c)
				{
					smallString=str.substring(0,i);
				}
			}
		}
		
		return smallString;
		
	}
}
