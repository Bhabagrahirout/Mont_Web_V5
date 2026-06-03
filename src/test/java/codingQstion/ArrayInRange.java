package codingQstion;

import java.util.Arrays;

public class ArrayInRange {

	public static void main(String[] args) {

		int[] ar = { 1, 2, 3, 4, 5, 6 };

		Arrays.stream(ar)
	      .forEach(x -> System.out.print(x + ","));

//		System.out.println(count);
	}

}
