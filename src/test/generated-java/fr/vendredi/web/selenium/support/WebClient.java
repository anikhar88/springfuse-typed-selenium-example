/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/support/WebClient.p.vm.java
 */
package fr.vendredi.web.selenium.support;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.web.util.JavaScriptUtils.javaScriptEscape;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.palominolabs.xpath.XPathUtils;

import fr.vendredi.web.selenium.support.element.WebElementConfiguration;

public class WebClient {
    public final WebDriver webDriver;
    public final String baseUrl;

    private long driverWaitBeforeStopInSeconds = 5;
    private long waitAfterClickInMs = 200;
    private long waitAfterClearInMs = 200;
    private long waitAfterStepInMs = 200;
    private long waitAfterFillMs = 200;
    private long waitAfterNotificationMs = 200;
    private boolean followVisually;

    public WebClient(WebClientBuilder builder) {
        this.webDriver = builder.webDriver;
        this.baseUrl = builder.baseUrl;
        this.followVisually = builder.followVisually;
        this.driverWaitBeforeStopInSeconds = builder.waitTimeInSeconds;
        if (followVisually) {
            waitAfterClickInMs = 250;
            waitAfterClearInMs = 250;
            waitAfterStepInMs = 4000;
            waitAfterFillMs = 250;
            waitAfterNotificationMs = 2000;
        }
        new WebElementConfiguration().configure(builder.testInstance, this);
    }

    public void hasTitle(String title) {
        hasText("<title>" + title + "</title>");
    }

    public void hasText(String text) {
        try {
            until(contains(text));
            success("Found [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find [" + text + "]");
        }
    }

    public void hasText(WebElement webElement, String text) {
        try {
            until(new TextContains(webElement, text));
            success("Found [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find [" + text + "]");
        }
    }

    public void hasNotText(WebElement webElement, String text) {
        try {
            until(new TextNotEquals(webElement, text));
            success("Found different text than [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find a text different to [" + text + "]");
        }
    }

    public void isDisplayed(WebElement webElement) {
        try {
            until(displayed(webElement));
        } catch (RuntimeException e) {
            error("element is not displayed");
        }
    }

    public void isEnabled(WebElement webElement) {
        try {
            until(enabled(webElement));
        } catch (RuntimeException e) {
            error("element is not displayed");
        }
    }

    public void until(Function<WebDriver, Boolean> function) {
        try {
            browserWait().until(function);
        } catch (StaleElementReferenceException e) {
            browserWait().until(function);
        }
    }

    public WebDriverWait browserWait() {
        return new WebDriverWait(webDriver, driverWaitBeforeStopInSeconds);
    }

    public static ExpectedCondition<Boolean> contains(final String text) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return from.getPageSource().contains(text);
            }
        };
    }

    public static ExpectedCondition<Boolean> displayed(final WebElement webElement) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return webElement.isDisplayed();
            }
        };
    }

    public static ExpectedCondition<Boolean> enabled(final WebElement webElement) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return webElement.isEnabled();
            }
        };
    }

    public void step(String text) {
        if (followVisually) {
            message(text);
            sleep(waitAfterStepInMs);
        }
    }

    public void message(String text) {
        notification(text, "info");
    }

    public void warning(String text) {
        notification(text, "warn");
    }

    public void error(String text) {
        if (followVisually) {
            notification(text, "error");
            sleep(60);
        }
        throw new RuntimeException(text);
    }

    public void success(String text) {
        notification(text, "succ_bg");
    }

    public void notification(String text, String type) {
        if (!followVisually) {
            return;
        }
        String notification = "growlNotificationBar.renderMessage({detail: '" + javaScriptEscape(text) + "', severity: '" + type + "'});";
        ((JavascriptExecutor) webDriver).executeScript(notification);
        sleep(waitAfterNotificationMs);
    }

    public void sleep(long sleepInMs) {
        try {
            MILLISECONDS.sleep(sleepInMs);
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickLinkTitle(String title) {
        clickCss("a[title=\"" + javaScriptEscape(title) + "\"]");
    }

    public void clickCss(String css) {
        click(By.cssSelector(css));
    }

    public void click(By by) {
        try {
            click(find(by));
        } catch (StaleElementReferenceException e) {
            // retry
            click(find(by));
        }
    }

	public List<WebElement> findAll(By by) {
		return webDriver.findElements(by);
	}

	public WebElement find(By by) {
		return webDriver.findElement(by);
	}

    public void click(WebElement webElement) {
        message("Clicking");
        isDisplayed(webElement);
        webElement.click();
        sleep(waitAfterClickInMs);
    }

    public void page(String relative) {
        webDriver.get(baseUrl + relative);
    }

    public void clear(WebElement... webElements) {
        for (WebElement webElement : webElements) {
            webElement.clear();
            sleep(waitAfterClearInMs);
        }
    }

    public void fill(By by, String text) {
    	fill(find(by), text);
    }

    public void fill(WebElement webElement, String text) {
        message("Sending key " + text);
        isEnabled(webElement);
        webElement.clear();
        webElement.sendKeys(text);
        webElement.sendKeys(Keys.TAB);
        sleep(waitAfterFillMs);
    }

    public void autocomplete(WebElement webElement, String text) {
        autocomplete(webElement, text, text);
    }

    public void autocomplete(WebElement webElement, String text, String match) {
        fill(webElement, text);
        click(By.xpath("//li[@data-item-label=" + XPathUtils.getXPathString(match) + "]"));
    }

    public void selectComboValue(WebElement webElement, String value) {
        new Select(webElement).selectByValue(value);
    }

    public void close() {
        webDriver.close();
    }

    public void takeScreenshot(String description) {
        if (webDriver instanceof TakesScreenshot) {
            try {
                File source = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                String extension = FilenameUtils.getExtension(source.getAbsolutePath());
                String date = DateFormatUtils.format(new Date(), "HH-mm-ss");
                String path = "./target/screenshots/" + description + "_" + date + "." + extension;
                FileUtils.copyFile(source, new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class WebClientBuilder {
        WebDriver webDriver;
        int waitTimeInSeconds = 10;
        Object testInstance;
        String baseUrl;
        boolean followVisually = true;

        public static WebClientBuilder newWebClient() {
            return new WebClientBuilder();
        }

        public WebClientBuilder waitTimeInSeconds(int waitTimeInSeconds) {
            this.waitTimeInSeconds = waitTimeInSeconds;
            return this;
        }

        public WebClientBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public WebClientBuilder onTest(Object testInstance) {
            this.testInstance = testInstance;
            return this;
        }

        public WebClientBuilder followVisually(boolean followVisually) {
            this.followVisually = followVisually;
            return this;
        }

        public WebClientBuilder webDriver(String driver) {
            if ("htmlunit".equalsIgnoreCase(driver)) {
                this.webDriver = new HtmlUnitDriver(true);
            } else if ("firefox".equalsIgnoreCase(driver)) {
                this.webDriver = new FirefoxDriver();
            } else if ("ie".equalsIgnoreCase(driver)) {
                this.webDriver = new InternetExplorerDriver();
            } else if ("chrome".equalsIgnoreCase(driver)) {
                this.webDriver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException(driver + " is not a valid web driver");
            }
            webDriver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
            webDriver.manage().window().setSize(new Dimension(1280, 1024));
            return this;
        }

        public WebClient build() {
            checkNotNull(baseUrl);
            checkNotNull(testInstance);
            checkNotNull(webDriver);
            return new WebClient(this);
        }
    }
}