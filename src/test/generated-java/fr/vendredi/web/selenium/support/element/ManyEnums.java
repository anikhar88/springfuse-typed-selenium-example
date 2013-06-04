package fr.vendredi.web.selenium.support.element;

import static com.google.common.collect.Lists.newArrayList;
import static com.palominolabs.xpath.XPathUtils.getXPathString;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ManyEnums<T extends Enum<T>> extends CustomElement {
	private final String id;
	private final Class<T> clazz;

	public ManyEnums(Class<T> clazz, String id) {
		this.clazz = clazz;
		this.id = id;
	}

	public boolean isSelected(T value) {
		return webClient.find(By.xpath("//input[@type='checkbox' and @name=" + getXPathString(id) + " and @value=" + getXPathString(value.name()) + "]"))
				.isSelected();

	}

	public void choose(T... values) {
		for (T value : values) {
			if (!isSelected(value)) {
				webClient.click(By.xpath("//input[@type='checkbox' and @name=" + getXPathString(id) + " and @value=" + getXPathString(value.name()) + "]"));
			}
		}
	}

	public List<T> values() {
		List<T> ret = newArrayList();
		for (WebElement webElement : webClient.findAll(By.xpath("//input[@type='checkbox' and @name=" + getXPathString(id) + "]"))) {
			if (webElement.isSelected()) {
				String attribute = webElement.getAttribute("value");
				ret.add(Enum.valueOf(clazz, attribute));
			}
		}
		return ret;
	}
}
