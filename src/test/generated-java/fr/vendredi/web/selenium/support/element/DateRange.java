package fr.vendredi.web.selenium.support.element;

import java.util.Date;

import org.openqa.selenium.By;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.datetime.DateFormatter;


public class DateRange extends CustomElement {
	private String pattern = "yyyy-MM-dd";
	private String dateFromId;
	private String dateToId;

	public DateRange(String id) {
		this.dateFromId = id + "RangeFrom_input";
		this.dateToId = id + "RangeTo_input";
	}

	public DateRange(String id, String pattern) {
		this(id);
		this.pattern = pattern;
	}

	public void from(Date date) {
		webClient.fill(webClient.find(By.id(dateFromId)), format(date));
	}

	public void to(Date date) {
		webClient.fill(webClient.find(By.id(dateToId)), format(date));
	}

	protected String format(Date date) {
		return new DateFormatter(pattern).print(date, LocaleContextHolder.getLocale());
	}
}
