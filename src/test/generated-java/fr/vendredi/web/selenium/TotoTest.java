package fr.vendredi.web.selenium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TotoTest {
	@Test
	public void rege() {
		String input = "There are 53 results";
		Pattern pattern = Pattern.compile("There are ([0-9]+) results");
		Matcher m = pattern.matcher(input);
		if (m.matches()) {
			int total = Integer.parseInt(m.group(1));
			System.out.println(total);
		}
	}
}
