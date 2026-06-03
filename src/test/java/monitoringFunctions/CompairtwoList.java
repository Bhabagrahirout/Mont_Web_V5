package monitoringFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompairtwoList {

	public static void main(String[] args) {

		Character[] cr1 = { 'A', 'B', 'C', 'A' };
		Character[] cr2 = { 'B', 'A', 'Q', 'G', 'A' };

		List<Character> al1 = new ArrayList<>(Arrays.asList(cr1));
		List<Character> al2 = new ArrayList<>(Arrays.asList(cr2));

		Map<Character, Integer> freqAl2 = new HashMap<>();

		for (Character c : al2) {
			freqAl2.put(c, freqAl2.getOrDefault(c, 0) + 1);
		}
		al2 = new ArrayList<Character>();

		for (Character c : al1) {
			if (freqAl2.getOrDefault(c, 0) > 0) {
				al2.add(c);
				freqAl2.put(c, freqAl2.get(c) - 1);
			}
		}

		System.out.println(al2);

	}

}
