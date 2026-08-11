package com.qafinal.api.stepdefinitions;

import com.qafinal.api.context.ApiTestContext;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import java.util.List;

/**
 * Step definitions generik untuk VALIDASI RESPONSE API.
 * Dipisah dari UserApiSteps/TagApiSteps agar tidak terjadi duplikasi step definition
 * (kedua feature file sama-sama butuh assert status code & isi body).
 */
public class CommonApiSteps {

    @Then("response status code adalah {int}")
    public void response_status_code_adalah(int expectedStatus) {
        int actualStatus = ApiTestContext.getResponse().getStatusCode();
        Assert.assertEquals(actualStatus, expectedStatus,
                "Status code tidak sesuai. Response body: " + ApiTestContext.getResponse().getBody().asString());
    }

    @Then("response status code bukan {int}")
    public void response_status_code_bukan(int unexpectedStatus) {
        int actualStatus = ApiTestContext.getResponse().getStatusCode();
        Assert.assertNotEquals(actualStatus, unexpectedStatus,
                "Status code seharusnya bukan " + unexpectedStatus);
    }

    @Then("response body memuat field {string}")
    public void response_body_memuat_field(String fieldName) {
        Object value = ApiTestContext.getResponse().jsonPath().get(fieldName);
        Assert.assertNotNull(value, "Field '" + fieldName + "' tidak ditemukan di response body: "
                + ApiTestContext.getResponse().getBody().asString());
    }

    @Then("field {string} pada response bernilai {string}")
    public void field_pada_response_bernilai(String fieldName, String expectedValue) {
        String actualValue = ApiTestContext.getResponse().jsonPath().getString(fieldName);
        Assert.assertEquals(actualValue, expectedValue,
                "Nilai field '" + fieldName + "' tidak sesuai ekspektasi");
    }

    @Then("field {string} pada response berupa list yang tidak kosong")
    public void field_pada_response_berupa_list_yang_tidak_kosong(String fieldName) {
        List<?> list = ApiTestContext.getResponse().jsonPath().getList(fieldName);
        Assert.assertNotNull(list, "Field '" + fieldName + "' tidak ditemukan atau bukan berupa list. Response body: "
                + ApiTestContext.getResponse().getBody().asString());
        Assert.assertFalse(list.isEmpty(), "Field '" + fieldName + "' seharusnya tidak kosong");
    }
}
