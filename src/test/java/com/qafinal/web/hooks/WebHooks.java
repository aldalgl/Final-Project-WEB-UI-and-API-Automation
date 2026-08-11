package com.qafinal.web.hooks;

import com.qafinal.web.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class WebHooks {

    @Before("@web")
    public void setUp() {
        DriverFactory.initDriver();
    }

    @After("@web")
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(scenario.getName() + " - failure screenshot", new ByteArrayInputStream(screenshot));
        }
        DriverFactory.quitDriver();
    }
}
