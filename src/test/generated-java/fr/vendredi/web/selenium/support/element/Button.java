package fr.vendredi.web.selenium.support.element;

import org.openqa.selenium.By;

public class Button extends CustomElement {
	public Button(String id) {
		super(id);
	}

	public void click() {
		webClient.click(By.id(id));
	}
}
