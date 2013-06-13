package fr.vendredi.web.selenium.support;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.util.JavaScriptUtils.javaScriptEscape;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ByXPath;

public class Highlighter {
	public void highlight(JavascriptExecutor executor, WebElement webElement, By by) {
		String selector = highlightSelector(webElement, by);
		if (selector == null) {
			return;
		}
		String highlight = "jQuery(" + selector + ").effect(\"highlight\",{color:\"#ffff00\"}, 700);";
		executor.executeScript(highlight);
		sleep();
	}

	private void sleep() {
		try {
			TimeUnit.MILLISECONDS.sleep(800);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private String highlightSelector(WebElement webElement, By by) {
		String selector = highlightById(webElement, by);
		if (isNotBlank(selector)) {
			return selector;
		}
		selector = highlightByCss(webElement, by);
		if (isNotBlank(selector)) {
			return selector;
		}
		selector = highlightByXpath(webElement, by);
		if (isNotBlank(selector)) {
			return selector;
		}
		return null;
	}

	private String highlightById(WebElement webElement, By by) {
		String id = webElement.getAttribute("id");
		if (isBlank(id)) {
			return null;
		}
		return "\"#" + javaScriptEscape(id).replace(":", "\\\\:") + "\"";
	}

	private String highlightByCss(WebElement webElement, By by) {
		if (!(by instanceof ByCssSelector)) {
			return null;
		}
		ByCssSelector cssSelector = (ByCssSelector) by;
		return "\"" + StringUtils.removeStart(cssSelector.toString(), "By.selector: ") + "\"";
	}

	private String highlightByXpath(WebElement webElement, By by) {
		if (!(by instanceof ByXPath)) {
			return null;
		}
		String xpath = "" //
		+ "  function xpath(STR_XPATH) {                                                                    \n" //
		+ "     var xresult = document.evaluate(STR_XPATH, document, null, XPathResult.ANY_TYPE, null);     \n" //
		+ "     var xnodes = [];                                                                            \n" //
		+ "     var xres;                                                                                   \n" //
		+ "     while (xres = xresult.iterateNext()) {                                                      \n" //
		+ "     	xnodes.push(xres);                                                                      \n" //
		+ "     }                                                                                           \n" //
		+ "     return xnodes;                                                                              \n" //
		+ "  }(\"" + StringUtils.removeStart(by.toString(), "By.xpath: ") + "\")                            \n"; //
		
		return xpath;
	}
}