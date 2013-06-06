package fr.vendredi.web.selenium.support.element;

import org.openqa.selenium.By;

public class Checkbox extends CustomElement {
	public Checkbox(String id) {
		super(id);
	}

	public void enable() {
		if (isDisabled()) {
			webClient.click(By.id(id));
		}
	}

	public void disable() {
		if (isEnabled()) {
			webClient.click(By.id(id));
		}
	}

	public boolean isDisabled() {
		return !isEnabled();
	}

	public boolean isEnabled() {
		return Boolean.valueOf(webClient.find(By.id(id)).getAttribute("checked"));
	}
}
