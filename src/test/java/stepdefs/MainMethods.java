package stepdefs;

import driver.Core;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainMethods {

    public RemoteWebDriver driver;

    public MainMethods(RemoteWebDriver driver) {
        this.driver = driver;
    }

    //Методы для проверки представления элементов на экране

    public WebElement waitForElementPresent(String locator, String error_message, long timeOutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        switch (by_type) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return By.id((locator));
            case "css":
                return By.cssSelector((locator));
            default:
                throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }

    public WebElement waitForElementPresent(String locator, String error_message)
    {
        return waitForElementPresent(locator, error_message,5);
    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    //Конец методов для проверки представления элементов на экране

    //Методы для осуществления действий с элементами на экране
    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);
            action
                    .press(x, start_y)
                    .waitAction(Duration.ofMillis(timeOfSwipe))
                    .moveTo(x, end_y)
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for chosen platform");
        }
    }

    public void swipeUpQuick() {
        swipeUp(300);
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator);
        int already_swipes = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swipes > max_swipes) {
                waitForElementPresent((locator), "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            already_swipes++;
        }
    }

    private int counter = 0;
    private int left_x, right_x, upper_y, lower_y, middle_x, middle_y;

    public void swipeElementToLeft(String locator, String error_message) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(
                    locator,
                    error_message,
                    10);
            if (counter == 0) {
                left_x = element.getLocation().getX();
                right_x = left_x + element.getSize().getWidth();
                upper_y = element.getLocation().getY();
                lower_y = upper_y + element.getSize().getHeight();
                middle_x = (right_x + left_x) / 2;
                middle_y = (upper_y + lower_y) / 2;
            }
            counter++;
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Core core = new Core();
            if (core.isAndroid()) {
                action.press(right_x, middle_y);
                action.waitAction(Duration.ofMillis(300));
                action.moveTo(left_x, middle_y);
            } else {
                action.press(middle_x, middle_y);
                action.waitAction(Duration.ofMillis(300));
                int offset_x = (-1 * element.getSize().getWidth());
                action.moveTo(offset_x, 0);
            }
            action.release();
            action.perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for chosen element ");
        }
    }
    //Конец методов для осуществления действий с элементами на экране
}