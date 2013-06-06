package fr.vendredi.web.selenium.support.element;

import static com.palominolabs.xpath.XPathUtils.getXPathString;

import org.openqa.selenium.By;

public class ChooseEnum<T extends Enum<? extends Enum<?>>> extends CustomElement {
	public ChooseEnum(String id) {
		super(id);
	}

	public void select(T value) {
		String xpathExpression = "//label[@for=contains(@for, " + getXPathString(id + ":" + value.ordinal()) + ")]";
		webClient.click(By.xpath(xpathExpression));
	}

	public boolean isSelected(T value) {
		String xpathExpression = "//input[@type='radio' and @for=contains(@for, " + getXPathString(id + ":" + value.ordinal()) + ")]";
		return webClient.find(By.xpath(xpathExpression)).isSelected();
	}
}
