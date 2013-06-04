/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/EditPage.e.vm.java
 */
package fr.vendredi.web.selenium.page.savedsearch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import fr.vendredi.web.selenium.page.AbstractEditPage;
import fr.vendredi.web.selenium.support.Page;
import fr.vendredi.web.selenium.support.element.Upload;

@Page
public class SavedSearchEditPage extends AbstractEditPage {
    // edit box
    @FindBy(id = "form:name")
    public WebElement name;
    @FindBy(id = "form:formClassname")
    public WebElement formClassname;
    @FindBy(id = "form:formContent_input")
    public Upload formContent;

    // many-to-one account
    @FindBy(id = "form:account")
    public WebElement account;
    @FindBy(id = "form:selectAccount")
    public WebElement accountSelectButton;
    @FindBy(id = "form:editAccount")
    public WebElement accountEditButton;
    @FindBy(id = "form:viewAccount")
    public WebElement accountViewButton;

}