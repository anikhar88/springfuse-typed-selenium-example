package fr.vendredi.web.selenium.support.element;

import org.openqa.selenium.By;

public abstract class Input<T> extends CustomElement {
	public Input(String id) {
		super(id);
	}

	public abstract void type(T value);

	public abstract T value();

	protected void typeString(String value) {
		webClient.fill(By.id(id), value);
	}

	protected String valueAttribute() {
		return webClient.find(By.id(id)).getAttribute("value");
	}
}
