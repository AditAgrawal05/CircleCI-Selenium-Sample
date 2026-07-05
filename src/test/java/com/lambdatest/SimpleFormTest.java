package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SimpleFormTest extends BaseTest {

    @Test
    public void testSimpleFormDemo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-message")))
                .sendKeys("LambdaTest CircleCI");
        driver.findElement(By.id("showInput")).click();

        String message = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("message"), "LambdaTest CircleCI"))
                ? driver.findElement(By.id("message")).getText()
                : "";
        Assert.assertTrue(message.contains("LambdaTest CircleCI"), "Submitted message did not match input");
    }
}
