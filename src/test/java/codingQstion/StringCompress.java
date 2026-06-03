package codingQstion;

public class StringCompress {

	public static void main(String[] args) {

		String s = "aaAbbBm";

		char[] arr = s.toCharArray();

		String prev = String.valueOf(arr[0]).toLowerCase();
		int count = 1;
		String cur;
		StringBuilder finalStr = new StringBuilder();
		for (int i = 1; i < arr.length; i++) {

			cur = String.valueOf(arr[i]).toLowerCase();
			System.out.println(prev + ":" + cur);

			if (cur.equalsIgnoreCase(prev)) {
				count++;

			} else {
				finalStr = finalStr.append(String.valueOf(count)).append(String.valueOf(prev).toLowerCase());
				count = 1;
			}
			prev = String.valueOf(arr[i]).toLowerCase();
		}
		finalStr = finalStr.append(String.valueOf(count)).append(String.valueOf(prev).toLowerCase());

		System.out.println("Result :" + finalStr);

	}

}
