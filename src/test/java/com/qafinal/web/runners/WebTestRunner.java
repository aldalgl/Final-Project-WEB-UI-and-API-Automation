package com.qafinal.web.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features/web",
        glue = {"com.qafinal.web.stepdefinitions", "com.qafinal.web.hooks"},
        tags = "@web",
        plugin = {
                "pretty",
                "html:build/cucumber-report/web/cucumber.html",
                "json:build/cucumber-report/web/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class WebTestRunner extends AbstractTestNGCucumberTests {
}
