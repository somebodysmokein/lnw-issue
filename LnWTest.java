package org.demo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.URL;
import java.time.Duration;

public class LnWTest {

    public AndroidDriver driver;

    @BeforeMethod(alwaysRun=true)
    public void setUp() throws Exception {
        MutableCapabilities capabilities = new UiAutomator2Options();
        //capabilities.setCapability("app", "bs://d2bc3555929d173bf911943494222d43ac047b54");
        driver = new AndroidDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
    }

    @Test
    public void homePage() throws Exception {


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        int count=1;

        while(iselementExist(AppiumBy.xpath("//*[contains(@text, 'NEXT')]")))
        {
            System.out.println("Found count :"+count++);
            driver.findElement(AppiumBy.xpath("//*[contains(@text, 'NEXT')]")).click();
        }

        WebElement getStartedBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(
                AppiumBy.xpath("//*[contains(@text, 'GET STARTED')]"))));

        getStartedBtn.click();

        try {
            WebElement locationPopup = wait.until(ExpectedConditions.visibilityOf(driver.findElement(
                    AppiumBy.xpath("//*[contains(@text, 'Share Your Location')]"))));

            WebElement okBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(
                    AppiumBy.xpath("//*[contains(@text, 'OK')]"))));
            okBtn.click();
            WebElement locationAccess=wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
                    By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"))));

            locationAccess.click();

        }catch(NoSuchElementException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }catch (ElementNotInteractableException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }catch(StaleElementReferenceException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        WebElement loginBtn= wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
                AppiumBy.xpath("//*[contains(@text, 'Login')]"))));

        loginBtn.click();

        Thread.sleep(10000);

        String source = driver.getPageSource();
        System.out.println(source);

        //WebElement joinNowBtn= wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(
          //      driver.findElement(AppiumBy.xpath("//*[contains(@text, 'JOIN NOW')]")))));

        WebElement joinNowBtn = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc='JOIN NOW']")))
        ));

        joinNowBtn.click();

        Thread.sleep(5000);

    }

    public boolean iselementExist(By locator)
    {
        boolean present= false;
        WebElement elt;
        try {
            elt= driver.findElement(locator);
            if(elt.isDisplayed())
            {
                present = true;
            }

        }catch(Exception e)
        {
            present = false;
        }
        return present;
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        System.out.println("TearDown method");
        driver.quit();
    }


}
