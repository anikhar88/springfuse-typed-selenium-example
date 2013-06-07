package fr.vendredi.web.selenium.support.element;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

public class SearchAction extends CustomElement {
	public Autocomplete username = new Autocomplete("form:username");
	public SaveSearch saveSearch = new SaveSearch("form:searchFormName");

	public void search() {
		webClient.click(By.id("form:search"));
		webClient.waitUntilEnabled(By.id("form:ajaxSearchSuccess"));
	}

	public void reset() {
		webClient.click(By.id("form:resetSearch"));
		try {
			TimeUnit.MILLISECONDS.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void excel() {
		// webClient.click(By.id("form:excel"));
	}

	public void saveSearch() {
	}
}

