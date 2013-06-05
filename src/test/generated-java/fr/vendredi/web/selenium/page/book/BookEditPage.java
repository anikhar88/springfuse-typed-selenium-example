/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/EditPage.e.vm.java
 */
package fr.vendredi.web.selenium.page.book;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import fr.vendredi.web.selenium.page.AbstractEditPage;
import fr.vendredi.web.selenium.support.Page;

@Page
public class BookEditPage extends AbstractEditPage {
    // edit box
    @FindBy(id = "form:bookTitle")
    public WebElement bookTitle;
    @FindBy(id = "form:numberOfPages")
    public WebElement numberOfPages;

    // many-to-one owner
    @FindBy(id = "form:owner")
    public WebElement owner;
    @FindBy(id = "form:viewOwner")
    public WebElement ownerViewButton;

}