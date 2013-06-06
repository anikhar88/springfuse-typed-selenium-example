package fr.vendredi.web.selenium.support.element;

import static com.palominolabs.xpath.XPathUtils.getXPathString;
import static org.openqa.selenium.By.name;

import org.openqa.selenium.By;

public class TableAction extends CustomElement {
	public void edit(String value) {
		clickTitle("Edit " + value);
	}

	public void view(String value) {
		clickTitle("View " + value);
	}

	public void delete(String value) {
		clickTitle("Delete " + value);
		By confirmation = name("form:askForDeleteItemDialogYes");
		webClient.click(confirmation);
	}

	public void select(String account) {

	}

	private void clickTitle(String title) {
		webClient.find(By.xpath("//a[@title=" + getXPathString(title) + "]")).click();
	}

}
