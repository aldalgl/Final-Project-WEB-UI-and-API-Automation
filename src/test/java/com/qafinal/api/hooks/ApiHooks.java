package com.qafinal.api.hooks;

import com.qafinal.api.context.ApiTestContext;
import io.cucumber.java.After;

public class ApiHooks {

    @After("@api")
    public void tearDown() {
        ApiTestContext.clear();
    }
}
