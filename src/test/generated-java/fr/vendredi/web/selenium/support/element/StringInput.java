package fr.vendredi.web.selenium.support.element;

public class StringInput extends Input<String> {
	public StringInput(String id) {
		super(id);
	}

	public void type(String value) {
		typeString(value);
	}

	public String value() {
		return valueAttribute();
	}

}
