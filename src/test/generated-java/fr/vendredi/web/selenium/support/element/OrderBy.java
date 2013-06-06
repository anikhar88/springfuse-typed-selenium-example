package fr.vendredi.web.selenium.support.element;

import static com.palominolabs.xpath.XPathUtils.getXPathString;

import javax.annotation.Nullable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

public class OrderBy extends CustomElement {
	public OrderBy(String id) {
		super(id);
	}

	private WebElement icon() {
		String xpath = "//tr/th[@id=" + getXPathString("form:searchResults:" + id) + "]/span[2]";
		return webClient.find(By.xpath(xpath));
	}

	public boolean isUp() {
		return icon().getAttribute("class").contains("ui-icon-triangle-1-n");
	}

	public boolean isDown() {
		return icon().getAttribute("class").contains("ui-icon-triangle-1-s");
	}

	public void up() {
		if (!isUp()) {
			icon().click();
			webClient.until(new Function<WebDriver, Boolean>() {
				@Override
				@Nullable
				public Boolean apply(WebDriver input) {
					return isUp();
				}
			});
		}
	}

	public void down() {
		if (!isDown()) {
			icon().click();
			webClient.until(new Function<WebDriver, Boolean>() {
				@Override
				@Nullable
				public Boolean apply(WebDriver input) {
					return isDown();
				}
			});
		}
	}
}
