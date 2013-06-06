package fr.vendredi.web.selenium.support.element;

import static com.google.common.collect.Lists.newArrayList;
import static com.palominolabs.xpath.XPathUtils.getXPathString;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ListBox extends CustomElement {
	public ListBox(String id) {
		super(id);
	}

	private List<WebElement> getOptions() {
		String xpath = "//select[@id=" + getXPathString(id) + "]/option";
		return webClient.findAll(By.xpath(xpath));

	}

	public List<String> texts() {
		List<String> ret = newArrayList();
		for (WebElement webElement : getOptions()) {
			ret.add(webElement.getText());
		}
		return ret;
	}

	public List<String> selectedTexts() {
		List<String> ret = newArrayList();
		for (WebElement webElement : getOptions()) {
			if (webElement.isSelected()) {
				ret.add(webElement.getText());
			}
		}
		return ret;
	}

	public List<String> selectedValues() {
		List<String> ret = newArrayList();
		for (WebElement webElement : getOptions()) {
			if (webElement.isSelected()) {
				ret.add(webElement.getAttribute("value"));
			}
		}
		return ret;
	}

	public List<String> values() {
		List<String> ret = newArrayList();
		for (WebElement webElement : getOptions()) {
			ret.add(webElement.getAttribute("value"));
		}
		return ret;
	}

	public void text(String... texts) {
		for (String text : texts) {
			String xpath = "//select[@id=" + getXPathString(id) + "]/option[contains(text(), " + getXPathString(text) + ")]";
			webClient.click(By.xpath(xpath));
		}
	}

	public void value(String... values) {
		for (String value : values) {
			String xpath = "//select[@id=" + getXPathString(id) + "]/option[contains(@value, " + getXPathString(value) + ")]";
			webClient.click(By.xpath(xpath));
		}
	}

	public void selectAll() {
		for (WebElement webElement : getOptions()) {
			if (!webElement.isSelected()) {
				webClient.click(webElement);
			}
		}
	}

	public void unselectAll() {
		for (WebElement webElement : getOptions()) {
			if (webElement.isSelected()) {
				webClient.click(webElement);
			}
		}
	}
}
