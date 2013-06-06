package fr.vendredi.web.selenium.support.element;

import org.apache.commons.lang.math.NumberUtils;

public class IntegerInput extends Input<Integer> {
	public IntegerInput(String id) {
		super(id);
	}

	public void type(Integer value) {
		typeString(value.toString());
	}

	public Integer value() {
		return Integer.valueOf(valueAttribute());
	}

	public boolean hasValue() {
		return NumberUtils.isNumber(valueAttribute());
	}

}
