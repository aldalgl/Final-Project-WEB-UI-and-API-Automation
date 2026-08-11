package com.qafinal.api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features/api",
        glue = {"com.qafinal.api.stepdefinitions", "com.qafinal.api.hooks"},
        tags = "@api",
        plugin = {
                "pretty",
                "html:build/cucumber-report/api/cucumber.html",
                "json:build/cucumber-report/api/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ApiTestRunner extends AbstractTestNGCucumberTests {
}
