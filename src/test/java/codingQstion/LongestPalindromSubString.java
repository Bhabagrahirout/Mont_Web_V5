package codingQstion;

public class LongestPalindromSubString {
	
	
	public static void main(String[] args) {
		String  s ="forgeeksskeegfor"; 
		System.out.println(longestPalindrome(s)); // Output: "bab" or "aba"
	}

	public static String longestPalindrome(String s) {
		if (s == null || s.length() < 1) {
			return "";
		}

		int start = 0, end = 0;

		for (int i = 0; i < s.length(); i++) {

			// Odd length palindrome (center at i)
			int len1 = expand(s, i, i);

			// Even length palindrome (center between i and i+1)
			int len2 = expand(s, i, i + 1);

			int len = Math.max(len1, len2);

			if (len > end - start) {
				start = i - (len - 1) / 2;
				end = i + len / 2;
			}
		}

		return s.substring(start, end + 1);
	}

	private static int expand(String s, int left, int right) {
		while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
			left--;
			right++;
		}
		return right - left - 1; // length of palindrome
	}

	
}
