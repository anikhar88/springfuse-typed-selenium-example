package fr.vendredi.web.selenium.support.element;

import static com.palominolabs.xpath.XPathUtils.getXPathString;

import org.openqa.selenium.By;


public class ManyBooleans extends CustomElement {
	String id;

	public ManyBooleans(String id) {
		this.id = id;
	}

	public void choose(Boolean... values) {
		for (Boolean value : values) {
			webClient.click(By.xpath("//input[@type='checkbox' and @name=" + getXPathString(id) + " and @value=" + getXPathString(value ? "true" : "false")
					+ "]"));
		}
	}
}

