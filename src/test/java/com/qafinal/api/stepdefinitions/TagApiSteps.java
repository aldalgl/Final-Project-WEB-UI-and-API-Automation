package com.qafinal.api.stepdefinitions;

import com.qafinal.api.client.TagApiClient;
import com.qafinal.api.context.ApiTestContext;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class TagApiSteps {

    private final TagApiClient tagApiClient = new TagApiClient();

    @When("saya mengirim GET request untuk daftar tag")
    public void saya_mengirim_get_request_untuk_daftar_tag() {
        Response response = tagApiClient.getTags();
        ApiTestContext.setResponse(response);
    }
}
