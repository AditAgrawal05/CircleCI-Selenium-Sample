package com.lambdatest;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    private static final String LT_USERNAME = System.getenv("LT_USERNAME");
    private static final String LT_ACCESS_KEY = System.getenv("LT_ACCESS_KEY");
    private static final String GRID_HOST = "@hub.lambdatest.com/wd/hub";

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        if (LT_USERNAME == null || LT_USERNAME.isEmpty() || LT_ACCESS_KEY == null || LT_ACCESS_KEY.isEmpty()) {
            throw new IllegalStateException(
                    "LT_USERNAME and LT_ACCESS_KEY environment variables must be set. " +
                            "Grab them from https://accounts.lambdatest.com/security");
        }

        MutableCapabilities browserOptions = new MutableCapabilities();
        browserOptions.setCapability("browserName", "Chrome");
        browserOptions.setCapability("browserVersion", "latest");

        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("platform", "Windows 10");
        ltOptions.put("build", "CircleCI TestNG Build");
        ltOptions.put("name", method.getName());
        ltOptions.put("user", LT_USERNAME);
        ltOptions.put("accessKey", LT_ACCESS_KEY);
        ltOptions.put("network", true);
        ltOptions.put("video", true);
        ltOptions.put("console", true);
        ltOptions.put("tunnel", false);

        browserOptions.setCapability("LT:Options", ltOptions);

        String remoteUrl = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_HOST;
        driver = new RemoteWebDriver(new URL(remoteUrl), browserOptions);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver == null) {
            return;
        }
        try {
            String status = result.isSuccess() ? "passed" : "failed";
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
        } finally {
            driver.quit();
        }
    }
}
