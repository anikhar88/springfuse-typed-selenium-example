/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/support/element/Messages.p.vm.java
 */
package fr.vendredi.web.selenium.support.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Messages extends CustomElement {
    @FindBy(id = "form:messages")
    public WebElement messagesPanel;

    public void hasMessage(String... values) {
        for (String value : values) {
            webClient.hasText(messagesPanel, value);
        }
    }

}
